package tech.itpark.roben.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.itpark.roben.domain.Suite;
import tech.itpark.roben.manager.SuiteManager;

import java.util.List;

@RestController
@RequestMapping("/suites")
@RequiredArgsConstructor
public class SuiteController {
    private final SuiteManager manager;

    @GetMapping()
    List<Suite> getAll() {
        return manager.getAll();
    }

    @GetMapping("/search")
    List<Suite> search(@RequestParam String text) {
        return manager.search(text);
    }

    @PostMapping("/add")
    public Suite addNewProduct(@RequestBody Suite suite) {
        return manager.add(suite);
    }

    @PostMapping("/delete/{id}")
    public Suite deleteById(@PathVariable long id) {
        return manager.deleteById(id);
    }

    @GetMapping("/{id}")
    public Suite getById(@PathVariable long id) {
        return manager.getByID(id);
    }
}
