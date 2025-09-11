package Scince.Service;

import Scince.Repository.Composition;
import Scince.Repository.CompositionRepository;
import Scince.Repository.ElementRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompositionService {
    private static double lowerHeatingValueOfPCB;
    private static double lowerHeatingValueOfTCB;
    private static double lowerHeatingValueOfSovtol10;
    private static final double lowerHeatingValueOfAcetone = 31.54;

    private final ElementRepository elementRepository;
    @Autowired
    private CompositionRepository compositionRepository;

    public CompositionService(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }


    private double lowerHeatingValueOfTCB() {
        double massConcetrationOfCarbon = 6 * elementRepository.getAtomicMassById(6) /
                (6 * elementRepository.getAtomicMassById(6) + 3 * elementRepository.getAtomicMassById(1) +
                        3 * elementRepository.getAtomicMassById(17));
        double massConcetrationOfHydrogen = 3 * elementRepository.getAtomicMassById(1) /
                (6 * elementRepository.getAtomicMassById(6) + 3 * elementRepository.getAtomicMassById(1) +
                        3 * elementRepository.getAtomicMassById(17));
        lowerHeatingValueOfTCB = 33.9 * massConcetrationOfCarbon + 103 * massConcetrationOfHydrogen;
        return lowerHeatingValueOfTCB;
    }

    private double lowerHeatingValueOfPCB() {
        double massConcetrationOfCarbon = 12 * elementRepository.getAtomicMassById(6) /
                (12 * elementRepository.getAtomicMassById(6) + 0 * elementRepository.getAtomicMassById(1) +
                        10 * elementRepository.getAtomicMassById(17));
        double massConcetrationOfHydrogen = 0;
        lowerHeatingValueOfPCB = 33.9 * massConcetrationOfCarbon + 103 * massConcetrationOfHydrogen;
        return lowerHeatingValueOfPCB;
    }

    private double lowerHeatingValueOfSovtol10() {
        if (lowerHeatingValueOfPCB == 0) {
            lowerHeatingValueOfPCB();
        }
        if (lowerHeatingValueOfTCB == 0) {
            lowerHeatingValueOfTCB();
        }
        lowerHeatingValueOfSovtol10 = 0.9 * lowerHeatingValueOfPCB + 0.1 * lowerHeatingValueOfTCB;
        return lowerHeatingValueOfSovtol10;
    }

    @Transactional
    public void calculateComposition() {
        compositionRepository.deleteAllInBatch();
        lowerHeatingValueOfSovtol10();
        final double initialConcentrationOfFe = 0.303;
        final double initialConcentrationOfMn = 0.045;
        final double initialConcentrationOfSi = 0.04;
        final double initialConcentrationOfCa = 0.01;
        final double initialConcentrationOfMg = 0.0026;
        final double initialConcentrationOfAl = 0.001;
        final double initialConcentrationOfCu = 0.0005;
        final double initialConcentrationOfWater = 0.5979;
        final int BATCH_SIZE = 100;

        List<Composition> batch = new ArrayList<>(BATCH_SIZE);
        int counter = 0;
        for (double i = 0.0; i <= 1; i += 0.05) {
            for (double j = 1.0; j >= 0; j -= 0.05) {
                for (double k = 0.0; k <= 1; k += 0.05) {

                    Composition composition = new Composition();

                    composition.setConcentrationOfSovtolAndWWT(j);
                    composition.setConcentrationOfSovtol(k);

                    double concentrationOfWWT = 1 - k;
                    composition.setConcentrationOfWWT(concentrationOfWWT);

                    composition.setConcentrationOfSovtol(k);

                    double concentrationOfAcetone = 1 - j;
                    composition.setConcentrationOfAcetone(concentrationOfAcetone);

                    double concentrationOfFe = initialConcentrationOfFe * concentrationOfWWT * j;
                    composition.setConcentrationOfFe(concentrationOfFe);

                    double concentrationOfMn = initialConcentrationOfMn * concentrationOfWWT * j;
                    composition.setConcentrationOfMn(concentrationOfMn);

                    double concentrationOfSi = initialConcentrationOfSi * concentrationOfWWT * j;
                    composition.setConcentrationOfSi(concentrationOfSi);

                    double concentrationOfCa = initialConcentrationOfCa * concentrationOfWWT * j;
                    composition.setConcentrationOfCa(concentrationOfCa);


                    double concentrationOfMg = initialConcentrationOfMg * concentrationOfWWT * j;
                    composition.setConcentrationOfMg(concentrationOfMg);

                    double concentrationOfAl = initialConcentrationOfAl * concentrationOfWWT * j;
                    composition.setConcentrationOfAl(concentrationOfAl);

                    double concentrationOfCu = initialConcentrationOfCu * concentrationOfWWT * j;
                    composition.setConcentrationOfCu(concentrationOfCu);

                    double concentrationOfWater = initialConcentrationOfWater * concentrationOfWWT * j;
                    composition.setConcentrationOfWater(concentrationOfWater);

                    double concentrationOfNonBurningElements = concentrationOfFe + concentrationOfMn + concentrationOfSi +
                            concentrationOfCa + concentrationOfMg + concentrationOfAl + concentrationOfCu;
                    composition.setConcentrationOfNonBurningElements(concentrationOfNonBurningElements);

                    double lowerHeatingValueOfComposition = k * lowerHeatingValueOfSovtol10 - 2.5 * concentrationOfWater +
                            concentrationOfAcetone * lowerHeatingValueOfAcetone;

                    composition.setLowerHeatingValueOfComposition(lowerHeatingValueOfComposition);

                    batch.add(composition);
                    counter++;
                    if (counter % BATCH_SIZE == 0) {
                        compositionRepository.saveAll(batch);
                        compositionRepository.flush();
                        batch.clear();
                    }
                }
            }
        }
        if (!batch.isEmpty()) {
            compositionRepository.saveAll(batch);
            compositionRepository.flush();
            batch.clear();
        }
    }

    public List<Composition> findAll() {
        return compositionRepository.findAll();
    }
}

