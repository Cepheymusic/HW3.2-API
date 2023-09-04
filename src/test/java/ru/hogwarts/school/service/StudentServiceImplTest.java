package ru.hogwarts.school.service;

import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exceptions.StudentException;
import ru.hogwarts.school.model.Student;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceImplTest {
    StudentService underTest = new StudentServiceImpl();
    Student student1 = new Student(0L, "Harry", 35);
    Student student2 = new Student(0L, "Harry", 35);
    Student student3 = new Student(0L, "Sam", 36);

    List<Student> students = new ArrayList<>(List.of(student1, student2, student3));

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
                        () -> underTest.create(student1));
        assertEquals("Студент существует", ex.getMessage());

    }

    @Test
    void read_studentIsInMap_readAndReturnedStudent() {
        underTest.create(student1);
        underTest.create(student2);
        Student result = underTest.read(2L);
        assertEquals(student2, result);
    }
    @Test
    void read_studentIsNotInMap_returnedStudentException() {
        underTest.create(student1);
        StudentException ex =
                assertThrows(StudentException.class,
                        () -> underTest.read(2L));
        assertEquals("Студент не найден", ex.getMessage());
    }
    @Test
    void update_studentIsUpdate_updateAndReturnedStudent() {
        underTest.create(student1);
        Student result = underTest.update(student1);
        assertEquals(student1, result);
    }
    @Test
    void update_studentIsNotInMap_returnedStudentException() {
        underTest.create(student1);
        StudentException ex =
                assertThrows(StudentException.class,
                        () -> underTest.update(student3));
        assertEquals("Студент не найден", ex.getMessage());
    }
    @Test
    void delete_studentDelete_deleteAndReturnedStudent() {
        underTest.create(student1);
        Student result = underTest.delete(1L);
        assertEquals(student1, result);
    }
    @Test
    void delete_studentIsNotInMap_returnedStudentException() {
        underTest.create(student1);
        StudentException ex =
                assertThrows(StudentException.class,
                        () -> underTest.delete(2L));
        assertEquals("Студент не найден", ex.getMessage());
    }
    @Test
    void readAllStudentByAge_studentIsInMap_readAndReturnedStudent() {
        underTest.create(student1);
        underTest.create(student2);
        underTest.create(student3);
        List<Student> result = underTest.readAllStudentByAge(35);
        assertFalse(result.isEmpty());
    }
}