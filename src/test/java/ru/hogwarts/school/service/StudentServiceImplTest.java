package ru.hogwarts.school.service;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentServiceImplTest {
    @InjectMocks
    StudentServiceImpl underTest;
    static Student student1 = new Student(0L, "Harry", 35);
    static Student student2 = new Student(0L, "Harry", 35);
    static Student student3 = new Student(0L, "ACAM", 20);
    static Student student4 = new Student(0L, "ASAM", 10);
    static Student student5 = new Student(0L, "APVAM", 15);
    private static final Faculty faculty = new Faculty(1L, "PRPR", "green");
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private FacultyRepository facultyRepository;
    @BeforeAll
    private static void init() {
        student1.setFaculty(faculty);

    }
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
    void findFacultyByStudentId_studentNotFound_returnedNotFoundException() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());
        StudentNotFoundException ex =
                assertThrows(StudentNotFoundException.class, () -> underTest.findFacultyByStudentId(1));
        assertEquals("Студент не найден", ex.getMessage());
    }

    @Test
    void findFacultyByStudentId_facultyIsFind_findAndReturnedFaculty() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student1));
        Faculty result = underTest.findFacultyByStudentId(1L);
        assertEquals(faculty, result);
    }
    @Test
    void findStudentByAgeBetween_studentIsFind_findAndReturnStudent() {
        when(studentRepository.findByAgeBetween(12, 35)).thenReturn(List.of(student1, student2));
        List<Student> result = underTest.findStudentByAgeBetween(12, 35);
        assertEquals(List.of(student1, student2), result);
    }
    @Test
    void findQuantityStudents() {
        when(studentRepository.findQuantityStudents()).thenReturn(2);
        Integer result = underTest.findQuantityStudents();
        assertEquals(2, result);
    }
    @Test
    void findAvgAgeStudents() {
        when(studentRepository.findAvgAgeStudents()).thenReturn(35);
        Integer result = underTest.findAvgAgeStudents();
        assertEquals(35, result);
    }
    @Test
    void findLastStudents() {
        when(studentRepository.findLastStudents(5)).thenReturn(List.of(student1, student2));
        List<Student> result = underTest.findLastStudents(5);
        assertEquals(List.of(student1, student2), result);
    }

    @Test
    void findStudentNameWithA() {
        when(studentRepository.findAll()).thenReturn(List.of(student3, student4, student5));
        List<String> result = underTest.findStudentNameWithA();
        assertEquals(List.of("ACAM", "APVAM", "ASAM"), result);
    }
    @Test
    void findAverageAgeStudentsWithStream() {
        when(studentRepository.findAll()).thenReturn(List.of(student3, student4, student5));
        Double result = underTest.findAverageAgeStudentsWithStream();
        assertEquals(15, result);

    }

}