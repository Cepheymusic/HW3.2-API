package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @PostMapping
    public Student create(@RequestBody Student student) {
        return studentService.create(student);
    }

    @GetMapping("/{id}")
    public Student read(@PathVariable long id) {
        return studentService.read(id);
    }

    @PutMapping("/{id}")
    public Student update(@PathVariable long id,@RequestBody Student student) {
        return studentService.update(id,student);
    }

    @DeleteMapping("/{id}")
    public Student delete(@PathVariable long id) {
        return studentService.delete(id);
    }

    @GetMapping
    public List<Student> readStudentByAge(@RequestParam int age) {
        return studentService.readAllStudentByAge(age);
    }
    @GetMapping("/faculty/{id}")
    public Optional<Faculty> findByStudent_id(@PathVariable long id) {
        return studentService.findById(id);
    }

    @GetMapping("/search")
    public List<Student> findStudentByAgeBetween(@RequestParam int min, @RequestParam int max) {
        return studentService.findStudentByAgeBetween(min, max);
    }
}
