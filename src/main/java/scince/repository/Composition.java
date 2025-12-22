package scince.repository;

import jakarta.persistence.*;

@Entity
@Table(name = "compositions", indexes = {
        @Index(name = "idx_lower_heating_value", columnList = "lower_heating_value_of_composition")
})
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "concentration_of_sovtol_and_wwt")
    private double concentrationOfSovtolAndWWT;
    @Column(name = "concentration_of_sovtol")
    private double concentrationOfSovtol;
    @Column(name = "concentration_of_wwt")
    private double concentrationOfWWT;
    @Column(name = "concentration_of_acetone")
    private double concentrationOfAcetone;
    @Column(name = "concentration_of_fe")
    private double concentrationOfFe;
    @Column(name = "concentration_of_mn")
    private double concentrationOfMn;
    @Column(name = "concentration_of_si")
    private double concentrationOfSi;
    @Column(name = "concentration_of_ca")
    private double concentrationOfCa;
    @Column(name = "concentration_of_mg")
    private double concentrationOfMg;
    @Column(name = "concentration_of_al")
    private double concentrationOfAl;
    @Column(name = "concentration_of_cu")
    private double concentrationOfCu;
    @Column(name = "concentration_of_water")
    private double concentrationOfWater;
    @Column(name = "concentration_of_non_burning_elements")
    private double concentrationOfNonBurningElements;
    @Column(name = "lower_heating_value_of_composition")
    private double lowerHeatingValueOfComposition;

    public Composition(Integer id, double concentrationOfSovtolAndWWT, double concentrationOfSovtol, double concentrationOfWWT, double concentrationOfAcetone, double concentrationOfFe, double concentrationOfMn, double concentrationOfSi, double concentrationOfCa, double concentrationOfMg, double concentrationOfAl, double concentrationOfCu, double concentrationOfWater, double concentrationOfNonBurningElements, double lowerHeatingValueOfComposition) {
        this.id = id;
        this.concentrationOfSovtolAndWWT = concentrationOfSovtolAndWWT;
        this.concentrationOfSovtol = concentrationOfSovtol;
        this.concentrationOfWWT = concentrationOfWWT;
        this.concentrationOfAcetone = concentrationOfAcetone;
        this.concentrationOfFe = concentrationOfFe;
        this.concentrationOfMn = concentrationOfMn;
        this.concentrationOfSi = concentrationOfSi;
        this.concentrationOfCa = concentrationOfCa;
        this.concentrationOfMg = concentrationOfMg;
        this.concentrationOfAl = concentrationOfAl;
        this.concentrationOfCu = concentrationOfCu;
        this.concentrationOfWater = concentrationOfWater;
        this.concentrationOfNonBurningElements = concentrationOfNonBurningElements;
        this.lowerHeatingValueOfComposition = lowerHeatingValueOfComposition;
    }

    public Composition() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getConcentrationOfSovtolAndWWT() {
        return concentrationOfSovtolAndWWT;
    }

    public void setConcentrationOfSovtolAndWWT(double concentrationOfSovtolAndWWT) {
        this.concentrationOfSovtolAndWWT = concentrationOfSovtolAndWWT;
    }

    public double getConcentrationOfSovtol() {
        return concentrationOfSovtol;
    }

    public void setConcentrationOfSovtol(double concentrationOfSovtol) {
        this.concentrationOfSovtol = concentrationOfSovtol;
    }

    public double getConcentrationOfWWT() {
        return concentrationOfWWT;
    }

    public void setConcentrationOfWWT(double concentrationOfWWT) {
        this.concentrationOfWWT = concentrationOfWWT;
    }

    public double getConcentrationOfAcetone() {
        return concentrationOfAcetone;
    }

    public void setConcentrationOfAcetone(double concentrationOfAcetone) {
        this.concentrationOfAcetone = concentrationOfAcetone;
    }

    public double getConcentrationOfFe() {
        return concentrationOfFe;
    }

    public void setConcentrationOfFe(double concentrationOfFe) {
        this.concentrationOfFe = concentrationOfFe;
    }

    public double getConcentrationOfMn() {
        return concentrationOfMn;
    }

    public void setConcentrationOfMn(double concentrationOfMn) {
        this.concentrationOfMn = concentrationOfMn;
    }

    public double getConcentrationOfSi() {
        return concentrationOfSi;
    }

    public void setConcentrationOfSi(double concentrationOfSi) {
        this.concentrationOfSi = concentrationOfSi;
    }

    public double getConcentrationOfCa() {
        return concentrationOfCa;
    }

    public void setConcentrationOfCa(double concentrationOfCa) {
        this.concentrationOfCa = concentrationOfCa;
    }

    public double getConcentrationOfMg() {
        return concentrationOfMg;
    }

    public void setConcentrationOfMg(double concentrationOfMg) {
        this.concentrationOfMg = concentrationOfMg;
    }

    public double getConcentrationOfAl() {
        return concentrationOfAl;
    }

    public void setConcentrationOfAl(double concentrationOfAl) {
        this.concentrationOfAl = concentrationOfAl;
    }

    public double getConcentrationOfCu() {
        return concentrationOfCu;
    }

    public void setConcentrationOfCu(double concentrationOfCu) {
        this.concentrationOfCu = concentrationOfCu;
    }

    public double getConcentrationOfWater() {
        return concentrationOfWater;
    }

    public void setConcentrationOfWater(double concentrationOfWater) {
        this.concentrationOfWater = concentrationOfWater;
    }

    public double getConcentrationOfNonBurningElements() {
        return concentrationOfNonBurningElements;
    }

    public void setConcentrationOfNonBurningElements(double concentrationOfNonBurningElements) {
        this.concentrationOfNonBurningElements = concentrationOfNonBurningElements;
    }

    public double getLowerHeatingValueOfComposition() {
        return lowerHeatingValueOfComposition;
    }

    public void setLowerHeatingValueOfComposition(double lowerHeatingValueOfComposition) {
        this.lowerHeatingValueOfComposition = lowerHeatingValueOfComposition;
    }
}