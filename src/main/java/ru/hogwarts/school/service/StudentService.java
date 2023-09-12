package ru.hogwarts.school.service;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Student create(Student student);

    Student read(long id);

    Student update(long id, Student student);

    Student delete(long id);

    List<Student> readAllStudentByAge(int age);

    Faculty findById(long id);

    List<Student> findStudentByAgeBetween(int min, int max);
}
