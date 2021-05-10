package tech.itpark.roben.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tech.itpark.roben.domain.Project;
import tech.itpark.roben.manager.ProjectManager;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectsController {
    private final ProjectManager manager;

    @GetMapping()
    List<Project> getAll(){
        return manager.getAll();
    }
//
    @GetMapping("/search")
    List<Project> search(@RequestParam String text){
        return manager.search(text);
    }

    @PostMapping("/add")
    public List<Project> addNewProduct(@RequestBody Project project){
        manager.add(project);
        return manager.lastItem();
    }

    @PostMapping("/delete/{id}")
    public Project deleteById(@PathVariable long id){
        return manager.deleteById(id);
    }

    @GetMapping("/{id}")
    public Project getById(@PathVariable long id){
       return manager.getByID(id);
  }
}
