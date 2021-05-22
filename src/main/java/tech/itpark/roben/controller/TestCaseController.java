package tech.itpark.roben.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.itpark.roben.domain.Suite;
import tech.itpark.roben.domain.TestCase;
import tech.itpark.roben.manager.TestCaseManager;

import java.util.List;

@RestController
@RequestMapping("/cases")
@RequiredArgsConstructor
public class TestCaseController {
    private final TestCaseManager manager;

    @GetMapping()
    List<TestCase> getAll() {
        return manager.getAll();
    }

    @GetMapping("/last")
    List<TestCase> giveLast() {
        return manager.lastItem();
    }

    @GetMapping("/search")
    List<TestCase> search(@RequestParam String text) {
        return manager.search(text);
    }

    @PostMapping("/add")
    public TestCase addNewProduct(@RequestBody TestCase testCase) {
        return manager.add(testCase);
    }

    @PostMapping("/delete/{id}")
    public TestCase deleteById(@PathVariable long id) {
        return manager.deleteById(id);
    }

    @GetMapping("/{id}")
    public TestCase getById(@PathVariable long id) {
        return manager.getByID(id);
    }

}
