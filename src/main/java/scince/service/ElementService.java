package scince.service;

import scince.repository.Element;
import scince.repository.ElementRepository;
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
    public Element update(Integer id, Double atomicMass) {
        Element element = elementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Element with id: " + id + " does not exist."));

        if (atomicMass != null && !atomicMass.equals(element.getAtomicMass())) {
            element.setAtomicMass(atomicMass);
            element = elementRepository.save(element);
        }

        return element;
    }
}