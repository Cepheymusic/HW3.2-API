package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyException;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final Map<Long, Faculty> faculties = new HashMap<>();
    private long counter;
    @Override
    public Faculty create(Faculty faculty) {
        if (faculties.containsValue(faculty)) {
            throw new FacultyException("Факультет существует");
        }
        faculty.setId(++counter);
        return faculties.put(faculty.getId(), faculty);
    }

    @Override
    public Faculty read(long id) {
        if (!faculties.containsKey(id)) {
            throw new FacultyException("Факультет не найден");
        }
        return faculties.get(id);
    }

    @Override
    public Faculty update(Faculty faculty) {
        if (!faculties.containsValue(faculty)) {
            throw new FacultyException("Факультет не найден");
        }
        return faculties.put(faculty.getId(), faculty);
    }

    @Override
    public Faculty delete(long id) {
        if (!faculties.containsKey(id)) {
            throw new FacultyException("Факультет не найден");
        }
        return faculties.remove(id);
    }
    @Override
    public List<Faculty> readAllFacultiesByColor(String color) {
        return faculties.values().stream()
                .filter(faculty -> faculty.getColor().equals(color))
                .collect(Collectors.toUnmodifiableList());
    }
}