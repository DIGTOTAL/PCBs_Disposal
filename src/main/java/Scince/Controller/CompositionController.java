package Scince.Controller;

import Scince.Repository.Composition;
import Scince.Service.CompositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/compositions")
public class CompositionController {
    @Autowired
    private CompositionService compositionService;

    @GetMapping
    public List<Composition> findAll() {
        return compositionService.findAll();
    }

    @PostMapping("/calculate")
    public void calculateCompositions() {
        compositionService.calculateComposition();
    }
}
