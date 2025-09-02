package Scince.PCD.Controller;

import Scince.PCD.Repository.Element;
import Scince.PCD.Repository.ElementRepository;
import Scince.PCD.Service.ElementService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/elements")
public class ElementController {

    private final ElementService elementService;
    private final ElementRepository elementRepository;

    public ElementController(ElementService elementService, ElementRepository elementRepository) {
        this.elementService = elementService;
        this.elementRepository = elementRepository;
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
    public Element update(@PathVariable int id, @RequestParam String atomicMass) {
        elementService.update(id, Double.valueOf(atomicMass));
        return null;
    }
}