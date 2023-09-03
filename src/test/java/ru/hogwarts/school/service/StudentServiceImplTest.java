package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exceptions.StudentException;
import ru.hogwarts.school.model.Student;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {
    StudentService underTest = new StudentServiceImpl();
    Student student1 = new Student(1L, "Harry", 35);
    Student student2 = new Student(1L, "Harry", 35);

    @Test
    void create__studentCreateAndReturn() {
        Student result = underTest.create(student1);
        assertEquals(student1, result);
    }

    @Test
    void create_studentIsInMap_returnStudentException() {
        underTest.create(student1);
        StudentException ex =
                assertThrows(StudentException.class,
                        () -> underTest.create(student2));
        assertEquals("Студент существует", ex.getMessage());

    }

}