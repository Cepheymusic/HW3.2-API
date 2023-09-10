package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface FacultyService {

    Faculty create(Faculty faculty);

    Faculty read(long id);

    Faculty update(long id, Faculty faculty);

    Faculty delete(long id);

    List<Faculty> readAllFacultiesByColor(String color);

    List<Student> findById(long id);

    Optional<Faculty> findByNameAndColor(String name, String color);
}
