package scince.controller;

import scince.repository.Element;
import scince.repository.ElementRepository;
import scince.service.ElementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/elements")
public class ElementController {

    private final ElementService elementService;

    public ElementController(ElementService elementService) {
        this.elementService = elementService;
    }

    @GetMapping
    public List<Element> findAll() {
        return elementService.findAll();
    }

    @PostMapping
    public Element createElement(@RequestBody Element element) {
        return elementService.create(element);
    }

    @DeleteMapping(path = "{id}")
    public void deleteElement(@PathVariable int id) {
        elementService.delete(id);
    }

    @PutMapping(path = "{id}")
    public Element update(@PathVariable int id, @RequestParam Double atomicMass) {
       return elementService.update(id, atomicMass);
    }
}