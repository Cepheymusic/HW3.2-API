package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty create(@RequestBody Faculty faculty) {
        return facultyService.create(faculty);
    }

    @GetMapping("/{id}")
    public Faculty read(@PathVariable long id) {
        return facultyService.read(id);
    }

    @PutMapping("/{id}")
    public Faculty update(@PathVariable long id, @RequestBody Faculty faculty) {
        return facultyService.update(id, faculty);
    }

    @DeleteMapping("/{id}")
    public Faculty delete(@PathVariable long id) {
        return facultyService.delete(id);
    }
    @GetMapping("/color/{color}")
    public List<Faculty> readFacultiesByColor(@PathVariable String color) {
        return facultyService.readAllFacultiesByColor(color);
    }
    @GetMapping("/student/{id}")
    public List<Student> findByFacultyId(@PathVariable long id) {
        return facultyService.findById(id);
    }

    @GetMapping("/search")
    public Faculty findByNameAndColor(@RequestParam String name, @RequestParam String color) {
        return facultyService.findByNameOrColor(name, color);
    }
}
