package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;

import java.util.List;

public interface FacultyService {

    Faculty create(Faculty faculty);

    Faculty read(long id);

    Faculty update(long id, Faculty faculty);

    Faculty delete(long id);

    List<Faculty> readAllFacultiesByColor(String color);
}
