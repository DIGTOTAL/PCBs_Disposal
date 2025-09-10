package Scince.Service;

import Scince.Repository.Element;
import Scince.Repository.ElementRepository;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElementService {
    private final ElementRepository elementRepository;

    public ElementService(ElementRepository elementRepository) {
        this.elementRepository = elementRepository;
    }

    public List<Element> findAll() {
        return elementRepository.findAll();
    }

    public Element create(@NotNull Element element) {
        Optional<Element> existingElement = elementRepository.findByName(element.getName());
        if (existingElement.isPresent()) {
            throw new IllegalArgumentException("Element with name: " + element.getName() + " already exists.");
        }
        return elementRepository.save(element);
    }

    public void delete(int id) {
        boolean exists = elementRepository.existsById(id);
        if (!exists) {
            throw new IllegalArgumentException("Element with id: " + id + " does not exist.");
        }
        elementRepository.deleteById(id);
    }

    @Transactional
    public void update(int id, Double atomicMass) {
        Optional<Element> existingElement = elementRepository.findById(id);
        if (existingElement.isEmpty()) {
            throw new IllegalArgumentException("Element with id: " + id + " already exists.");
        }

        Element element = existingElement.get();

        if (atomicMass != null && !atomicMass.equals(element.getAtomicMass())) {
            element.setAtomicMass(atomicMass);
        }
        element.setAtomicMass(atomicMass);
        elementRepository.save(element);
    }

    public double getAtomicMassById(int id){
       return elementRepository.getAtomicMassById(id);
    }
}