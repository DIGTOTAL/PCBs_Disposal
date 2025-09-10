package Scince.Repository;

import jakarta.persistence.*;

@Entity
@Table(name = "compositions")
public class Composition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double concentrationOfSovtolAndWWT;
    private double concentrationOfSovtol;
    private double concentrationOfWWT;
    private double concentrationOfAcetone;
    private double concentrationOfFe;
    private double concentrationOfMn;
    private double concentrationOfSi;
    private double concentrationOfCa;
    private double concentrationOfMg;
    private double concentrationOfAl;
    private double concentrationOfCu;
    private double concentrationOfWater;
    private double concentrationOfNonBurningElements;
    private double loverHeatingValueOfComposition;

    public Composition(int id, double concentrationOfSovtolAndWWT, double concentrationOfSovtol, double concentrationOfWWT, double concentrationOfAcetone, double concentrationOfFe, double concentrationOfMn, double concentrationOfSi, double concentrationOfCa, double concentrationOfMg, double concentrationOfAl, double concentrationOfCu, double concentrationOfWater, double concentrationOfNonBurningElements, double loverHeatingValueOfComposition) {
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
        this.loverHeatingValueOfComposition = loverHeatingValueOfComposition;
    }

    public Composition() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
        return loverHeatingValueOfComposition;
    }

    public void setLowerHeatingValueOfComposition(double loverHeatingValueOfComposition) {
        this.loverHeatingValueOfComposition = loverHeatingValueOfComposition;
    }
}