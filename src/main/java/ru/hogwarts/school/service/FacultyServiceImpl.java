package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyServiceImpl(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {

        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty read(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyNotFoundException("Факультет не найден");
        }
        return faculty.get();
    }

    @Override
    public Faculty update(long id, Faculty faculty) {
        if (facultyRepository.findById(id).isEmpty()) {
            throw new FacultyNotFoundException("Факультет не найден");
        }
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty delete(long id) {
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyNotFoundException("Факультет не найден");
        }
        facultyRepository.deleteById(id);
        return faculty.get();
    }
    @Override
    public List<Faculty> readAllFacultiesByColor(String color) {
        return facultyRepository.findByColor(color);
    }
}
