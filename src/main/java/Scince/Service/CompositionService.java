package Scince.Service;

import Scince.Repository.Composition;
import Scince.Repository.CompositionRepository;
import Scince.Repository.Element;
import Scince.Repository.ElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public static double getLowerHeatingValueOfAcetone() {
        return lowerHeatingValueOfAcetone;
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

    public void calculateComposition() {
        double initialConcentrationOfFe = 0.303;
        double initialConcentrationOfMn = 0.045;
        double initialConcentrationOfSi = 0.04;
        double initialConcentrationOfCa = 0.01;
        double initialConcentrationOfMg = 0.0026;
        double initialConcentrationOfAl = 0.001;
        double initialConcentrationOfCu = 0.0005;
        double initialConcentrationOfWater = 0.5979;


        for (double i = 1; i >= 0; i -= 0.05) {
            for (double j = 0; j <= 1; j += 0.05) {
                for (double k = 0; k <= 1; k += 0.05) {

                    Composition composition = new Composition();

                    composition.setConcentrationOfSovtolAndWWT(i);
                    double concentrationOfSovtolAndWWT = composition.getConcentrationOfSovtolAndWWT();

                    composition.setConcentrationOfSovtol(j);
                    double concentrationOfSovtol = composition.getConcentrationOfSovtol();
                    composition.setConcentrationOfWWT(1 - j);
                    double concentrationOfWWT = composition.getConcentrationOfWWT();
                    
                    composition.setConcentrationOfAcetone(k);
                    double concentrationOfAcetone = composition.getConcentrationOfAcetone();

                    composition.setConcentrationOfFe(initialConcentrationOfFe*concentrationOfWWT*concentrationOfSovtolAndWWT);
                    double concentrationOfFe = composition.getConcentrationOfFe();

                    composition.setConcentrationOfMn(initialConcentrationOfMn*concentrationOfWWT*concentrationOfSovtolAndWWT);
                    double concentrationOfMn = composition.getConcentrationOfMn();

                    composition.setConcentrationOfSi(initialConcentrationOfSi*concentrationOfWWT*concentrationOfSovtolAndWWT);
                    double concentrationOfSi = composition.getConcentrationOfSi();

                    composition.setConcentrationOfCa(initialConcentrationOfCa*concentrationOfWWT*concentrationOfSovtolAndWWT);
                    double concentrationOfCa = composition.getConcentrationOfCa();

                    composition.setConcentrationOfMg(initialConcentrationOfMg*concentrationOfWWT*concentrationOfSovtolAndWWT);
                    double concentrationOfMg = composition.getConcentrationOfMg();

                    composition.setConcentrationOfAl(initialConcentrationOfAl*concentrationOfWWT*concentrationOfSovtolAndWWT);
                    double concentrationOfAl = composition.getConcentrationOfAl();

                    composition.setConcentrationOfCu(initialConcentrationOfCu*concentrationOfWWT*concentrationOfSovtolAndWWT);
                    double concentrationOfCu = composition.getConcentrationOfCu();

                    composition.setConcentrationOfWater(initialConcentrationOfWater*concentrationOfWWT*concentrationOfSovtolAndWWT);
                    double concentrationOfWater = composition.getConcentrationOfWater();

                    composition.setConcentrationOfNonBurningElements(concentrationOfFe+concentrationOfMn+concentrationOfSi+
                            concentrationOfCa+concentrationOfMg+concentrationOfAl+concentrationOfCu);
                    double concentrationOfNonBurningElements = composition.getConcentrationOfNonBurningElements();

                    composition.setLowerHeatingValueOfComposition(((1-concentrationOfNonBurningElements-concentrationOfWater)*lowerHeatingValueOfSovtol10-2.5*concentrationOfWater)*
                            concentrationOfSovtolAndWWT+lowerHeatingValueOfAcetone*concentrationOfAcetone);

                    compositionRepository.save(composition);
                }
            }
        }
    }

    public List<Composition> findAll() {
            return compositionRepository.findAll();
        }
    }

