package scince.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import scince.repository.Composition;
import scince.service.CompositionService;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/compositions")
public class CompositionController {

    private CompositionService compositionService;

    public CompositionController(CompositionService compositionService) {
        this.compositionService = compositionService;
    }

    @GetMapping("/search")
    public List<Composition> findByLHVRange(@RequestParam(required = false) Double lowerHeatingValue,
                                            @RequestParam(defaultValue = "0.05") double tolerance) {
        if (lowerHeatingValue == null) {
            return compositionService.findAll();
        }
        return compositionService.findByLowerHeatingValueApproximately(lowerHeatingValue, tolerance);
    }

    @PostMapping("/calculate")
    public ResponseEntity<Void> calculateCompositions() {
        CompletableFuture.runAsync(() -> compositionService.calculateComposition());
        return ResponseEntity.accepted().build(); // 202 Accepted
    }
}
