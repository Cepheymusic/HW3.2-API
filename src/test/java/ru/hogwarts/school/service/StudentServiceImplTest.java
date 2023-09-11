package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @InjectMocks
    StudentServiceImpl underTest;
    Student student1 = new Student(0L, "Harry", 35);
    Student student2 = new Student(0L, "Harry", 35);
    Student student3 = new Student(0L, "Sam", 36);
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private FacultyRepository facultyRepository;
    @BeforeEach
    void beforeEach() {
    underTest = new StudentServiceImpl(studentRepository, facultyRepository);
    }

    @Test
    void create__studentCreateAndReturn() {
        when(studentRepository.save(student1)).thenReturn(student1);

        assertEquals(student1, underTest.create(student1));
    }

    @Test
    void read_studentIsInMap_readAndReturnedStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        Student result = underTest.read(1L);
        assertEquals(student1, result);
    }
    @Test
    void read_studentIsNotInMap_returnedStudentException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        StudentNotFoundException ex =
                assertThrows(StudentNotFoundException.class,
                        () -> underTest.read(1L));
        assertEquals("Студент не найден", ex.getMessage());
    }
    @Test
    void update_studentIsUpdate_updateAndReturnedStudent() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        when(studentRepository.save(student1)).thenReturn(student1);
        Student result = underTest.update(1L, student1);
        assertEquals(student1, result);
    }
    @Test
    void update_studentIsNotInMap_returnedStudentException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        StudentNotFoundException ex =
                assertThrows(StudentNotFoundException.class,
                        () -> underTest.update(1, student1));
        assertEquals("Студент не найден", ex.getMessage());
    }
//    @Test
//    void delete_studentDelete_deleteAndReturnedStudent() {
//        underTest.create(student1);
//        Student result = underTest.delete(1L);
//        assertEquals(student1, result);
//    }
    @Test
    void delete_studentIsNotInMap_returnedStudentException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        StudentNotFoundException ex =
                assertThrows(StudentNotFoundException.class,
                        () -> underTest.delete(1L));
        assertEquals("Студент не найден", ex.getMessage());
    }
    @Test
    void readAllStudentByAge_studentIsInMap_readAndReturnedStudent() {
        when(studentRepository.findByAge(35)).thenReturn(List.of(student1, student2));
        List<Student> result = underTest.readAllStudentByAge(35);
        assertEquals(List.of(student1, student2), result);
    }

    @Test
    void findFacultyByStudentId_facultyIsNot_returnedNotFoundException() {
        when(facultyRepository.findByStudent_id(1L)).thenReturn(null);
        FacultyNotFoundException ex =
                assertThrows(FacultyNotFoundException.class, () -> underTest.findById(1L));
        assertEquals("Факультет не найден", ex.getMessage());
    }

    //    @Test
//    void findFacultyByStudentId_facultyIsFind_findAndReturnedFaculty() {
//        when(facultyRepository.findByStudent_id(1L)).thenReturn(student1.getFaculty());
//        Faculty result =  underTest.findById(1L);
//        assertEquals(student1.getFaculty(), result); // не работает
//    }
    @Test
    void findStudentByAgeBetween_studentIsFind_findAndReturnStudent() {
        when(studentRepository.findByAgeBetween(12, 35)).thenReturn(List.of(student1, student2));
        List<Student> result = underTest.findStudentByAgeBetween(12, 35);
        assertEquals(List.of(student1, student2), result);
    }

}