package scince.service;

import scince.repository.Composition;
import scince.repository.CompositionRepository;
import scince.repository.ElementRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CompositionService {

    private static final Logger log = LoggerFactory.getLogger(CompositionService.class);

    private static final double LOWER_HEATING_VALUE_OF_ACETONE = 31.54;
    private static final double STEP = 0.05;
    private static final int STEPS = (int) Math.round(1.0 / STEP);
    private static final int BATCH_SIZE = 100;

    private final ElementRepository elementRepository;
    private final CompositionRepository compositionRepository;
    private final EntityManager entityManager;

    // атомные массы и рассчитанные LHV — инициализируются в @PostConstruct
    private double atomicMassOfCarbon;
    private double atomicMassOfHydrogen;
    private double atomicMassOfChlorine;

    private double lowerHeatingValueOfPCB;
    private double lowerHeatingValueOfTCB;
    private double lowerHeatingValueOfSovtol10;

    public CompositionService(ElementRepository elementRepository,
                              CompositionRepository compositionRepository,
                              EntityManager entityManager) {
        this.elementRepository = Objects.requireNonNull(elementRepository, "elementRepository");
        this.compositionRepository = Objects.requireNonNull(compositionRepository, "compositionRepository");
        this.entityManager = Objects.requireNonNull(entityManager, "entityManager");
    }

    @PostConstruct
    private void init() {
        this.atomicMassOfCarbon = getAtomicMassOrThrow(6);
        this.atomicMassOfHydrogen = getAtomicMassOrThrow(1);
        this.atomicMassOfChlorine = getAtomicMassOrThrow(17);

        calculateLowerHeatingValues();

        log.info("Initialized atomic masses and lower heating values: PCB={}, TCB={}, Sovtol10={}",
                lowerHeatingValueOfPCB, lowerHeatingValueOfTCB, lowerHeatingValueOfSovtol10);
    }

    private double getAtomicMassOrThrow(Integer id) {
        return elementRepository.getAtomicMassById(id)
                .orElseThrow(() -> new IllegalStateException("Element with id " + id + " not found in database"));
    }

    private void calculateLowerHeatingValues() {
        double carbonRatioInTCB = 6;
        double hydrogenRatioInTCB = 3;
        double chlorineRatioInTCB = 3;

        double atomicMassOfTCB = carbonRatioInTCB * atomicMassOfCarbon + hydrogenRatioInTCB * atomicMassOfHydrogen + chlorineRatioInTCB * atomicMassOfChlorine;
        double massConcentrationOfCarbonTCB = carbonRatioInTCB * atomicMassOfCarbon / atomicMassOfTCB;
        double massConcentrationOfHydrogenTCB = hydrogenRatioInTCB * atomicMassOfHydrogen / atomicMassOfTCB;

        double carbonRatioInSovtol10 = 33.9;
        double hydrogenRatioInSovtol10 = 103;
        this.lowerHeatingValueOfTCB = carbonRatioInSovtol10 * massConcentrationOfCarbonTCB + hydrogenRatioInSovtol10 * massConcentrationOfHydrogenTCB;

        double carbonRatioInPCB = 12;
        double chlorineRatioInPCB = 10;
        double hydrogenRatioInPCB = 0;

        double atomicMassOfPCB = carbonRatioInPCB * atomicMassOfCarbon + chlorineRatioInPCB * atomicMassOfChlorine;
        double massConcentrationOfCarbonPCB = carbonRatioInPCB * atomicMassOfCarbon / atomicMassOfPCB;
        double massConcentrationOfHydrogenPCB = hydrogenRatioInPCB * atomicMassOfHydrogen;
        this.lowerHeatingValueOfPCB = carbonRatioInSovtol10 * massConcentrationOfCarbonPCB + hydrogenRatioInSovtol10 * massConcentrationOfHydrogenPCB;

        double ratioOfPCBInSovtol10 = 0.9;
        double ratioOfTCBInSovtol10 = 0.1;
        this.lowerHeatingValueOfSovtol10 = ratioOfPCBInSovtol10 * lowerHeatingValueOfPCB + ratioOfTCBInSovtol10 * lowerHeatingValueOfTCB;
    }

    @Transactional
    public void calculateComposition() {
        // Попробуем выполнить TRUNCATE RESTART IDENTITY — это сбросит sequence в PostgreSQL
        try {
            entityManager.createNativeQuery("TRUNCATE TABLE compositions RESTART IDENTITY CASCADE").executeUpdate();
        } catch (Exception e) {
            log.warn("TRUNCATE failed, falling back to delete + reset sequence", e);
            try {
                compositionRepository.deleteAllInBatch();
            } catch (org.springframework.dao.DataIntegrityViolationException ex) {
                log.error("Failed to delete existing compositions. Ensure there are no foreign key constraints.", ex);
                compositionRepository.deleteAll();
            }
            // Попробуем сбросить sequence через pg_get_serial_sequence (Postgres)
            try {
                entityManager.createNativeQuery("SELECT setval(pg_get_serial_sequence('compositions','id'), 1, false)").getSingleResult();
            } catch (Exception ex2) {
                log.warn("Failed to reset sequence via setval", ex2);
            }
        }

        final double initialConcentrationOfFe = 0.303;
        final double initialConcentrationOfMn = 0.045;
        final double initialConcentrationOfSi = 0.04;
        final double initialConcentrationOfCa = 0.01;
        final double initialConcentrationOfMg = 0.0026;
        final double initialConcentrationOfAl = 0.001;
        final double initialConcentrationOfCu = 0.0005;
        final double initialConcentrationOfWater = 0.5979;

        List<Composition> batch = new ArrayList<>(BATCH_SIZE);
        int counter = 0;

        for (int i = 0; i <= STEPS; i++) {
            double concentrationOfSovtolAndWWT = i * STEP;
            for (int j = 0; j <= STEPS; j++) {
                double concentrationOfSovtol = j * STEP;

                Composition composition = new Composition();

                composition.setConcentrationOfSovtolAndWWT(roundTo(concentrationOfSovtolAndWWT));
                composition.setConcentrationOfSovtol(roundTo(concentrationOfSovtol));

                double concentrationOfWWT = 1 - concentrationOfSovtol;
                composition.setConcentrationOfWWT(roundTo(concentrationOfWWT));

                double concentrationOfAcetone = 1 - concentrationOfSovtolAndWWT;
                composition.setConcentrationOfAcetone(roundTo(concentrationOfAcetone));

                double factor = concentrationOfWWT * concentrationOfSovtolAndWWT;
                double concentrationOfFe = initialConcentrationOfFe * factor;
                double concentrationOfMn = initialConcentrationOfMn * factor;
                double concentrationOfSi = initialConcentrationOfSi * factor;
                double concentrationOfCa = initialConcentrationOfCa * factor;
                double concentrationOfMg = initialConcentrationOfMg * factor;
                double concentrationOfAl = initialConcentrationOfAl * factor;
                double concentrationOfCu = initialConcentrationOfCu * factor;
                double concentrationOfWater = initialConcentrationOfWater * factor;

                composition.setConcentrationOfFe(roundTo(concentrationOfFe));
                composition.setConcentrationOfMn(roundTo(concentrationOfMn));
                composition.setConcentrationOfSi(roundTo(concentrationOfSi));
                composition.setConcentrationOfCa(roundTo(concentrationOfCa));
                composition.setConcentrationOfMg(roundTo(concentrationOfMg));
                composition.setConcentrationOfAl(roundTo(concentrationOfAl));
                composition.setConcentrationOfCu(roundTo(concentrationOfCu));
                composition.setConcentrationOfWater(roundTo(concentrationOfWater));

                double concentrationOfNonBurningElements = concentrationOfFe + concentrationOfMn + concentrationOfSi +
                        concentrationOfCa + concentrationOfMg + concentrationOfAl + concentrationOfCu;
                composition.setConcentrationOfNonBurningElements(roundTo(concentrationOfNonBurningElements));

                double waterRatioInComposition = 2.5;
                double lowerHeatingValueOfComposition = concentrationOfSovtolAndWWT * ((1-concentrationOfWater-concentrationOfNonBurningElements)*lowerHeatingValueOfSovtol10
                - waterRatioInComposition * concentrationOfWater)+ concentrationOfAcetone * LOWER_HEATING_VALUE_OF_ACETONE;
                composition.setLowerHeatingValueOfComposition(roundTo(lowerHeatingValueOfComposition));

                batch.add(composition);
                counter++;

                if (counter % BATCH_SIZE == 0) {
                    compositionRepository.saveAll(batch);
                    compositionRepository.flush();
                    batch.clear();
                }
            }
        }

        if (!batch.isEmpty()) {
            compositionRepository.saveAll(batch);
            compositionRepository.flush();
            batch.clear();
        }

        log.info("calculateComposition finished, saved {} compositions", counter);
    }

    public List<Composition> findAll() {
        return compositionRepository.findAll();
    }

    public List<Composition> findByLowerHeatingValueApproximately(double value, double tolerance) {
        double min = roundTo(value - tolerance);
        double max = roundTo(value + tolerance);
        return compositionRepository.findByLowerHeatingValueOfCompositionBetween(min, max);
    }

    private static double roundTo(double value) {
        double decimal = 4;
        long factor = (long) Math.pow(10, decimal);
        return Math.round(value * factor) / (double) factor;
    }
}