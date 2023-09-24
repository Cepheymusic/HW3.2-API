package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;
    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

    public FacultyServiceImpl(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Faculty create(Faculty faculty) {
        logger.info("Был вызван метод create с данными" + faculty);
        Faculty createFaculty = facultyRepository.save(faculty);
        logger.info("Из метода create вернули" + faculty);
        return createFaculty;
    }

    @Override
    public Faculty read(long id) {
        logger.info("Был вызван метод read с данными" + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyNotFoundException("Факультет не найден");
        }
        Faculty readfaculty = faculty.get();
        logger.info("Из метода read вернули" + readfaculty);
        return readfaculty;
    }

    @Override
    public Faculty update(long id, Faculty faculty) {
        logger.info("Был вызван метод update с данными: {}, {}", id, faculty);
        if (facultyRepository.findById(id).isEmpty()) {
            throw new FacultyNotFoundException("Факультет не найден");
        }
        Faculty updateFaculty = facultyRepository.save(faculty);
        logger.info("Из метода update вернули" + faculty);
        return updateFaculty;
    }

    @Override
    public Faculty delete(long id) {
        logger.info("Был вызван метод delete с данными" + id);
        Optional<Faculty> faculty = facultyRepository.findById(id);
        if (faculty.isEmpty()) {
            throw new FacultyNotFoundException("Факультет не найден");
        }
        facultyRepository.deleteById(id);
        Faculty deleteFaculty = faculty.get();
        logger.info("Из метода delete вернули" + deleteFaculty);
        return deleteFaculty;
    }

    @Override
    public List<Faculty> readAllFacultiesByColor(String color) {
        logger.info("Был вызван метод readAllFacultiesByColor с данными" + color);
        List<Faculty> readAllByColor = facultyRepository.findByColor(color);
        logger.info("Из метода readAllFacultiesByColor вернули" + readAllByColor);
        return readAllByColor;
    }

    @Override
    public List<Student> findStudentsByFacultyId(long id) {
        logger.info("Был вызван метод findStudentsByFacultyId с данными" + id);
        List<Student> findStudent = studentRepository.findByFaculty_id(id);
        logger.info("Из метода findStudentsByFacultyId вернули" + findStudent);
        return findStudent;
    }
    @Override
    public Faculty findFacultyByNameOrColor(String name, String color) {
        logger.info("Был вызван метод findStudentsByFacultyId с данными: {}, {}", name, color);
        Faculty findFaculty = facultyRepository.findByNameOrColorIgnoreCase(name, color);
        logger.info("Из метода findFacultyByNameOrColor вернули" + findFaculty);
        return findFaculty;
    }

    @Override
    public String findLongestNameFaculty() {
        return facultyRepository.findAll().stream()
                .map(name -> name.getName())
                .max(Comparator.comparingInt(name -> name.length()))
                .orElseThrow(() -> new FacultyNotFoundException("Нет факультетов в БД"));
    }
}
