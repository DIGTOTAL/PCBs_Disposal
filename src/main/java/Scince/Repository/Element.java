package Scince.Repository;

import jakarta.persistence.*;

@Entity
@Table(name = "elements")
public class Element {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @Column(name = "atomic_mass")
    private Double atomicMass;

    public Element(int id, Double atomicMass, String name) {
        this.id = id;
        this.atomicMass = atomicMass;
        this.name = name;
    }

    public Element() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAtomicMass() {
        return atomicMass;
    }

    public void setAtomicMass(Double atomicMass) {
        this.atomicMass = atomicMass;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", atomicMass='" + atomicMass + '\'' +
                '}';
    }
}
