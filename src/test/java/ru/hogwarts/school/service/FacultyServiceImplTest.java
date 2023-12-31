package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {
    @InjectMocks
    FacultyServiceImpl underTest;

    Faculty faculty1 = new Faculty(0L, "Gryffindor", "brown");
    Faculty faculty2 = new Faculty(0L, "Slyseryne", "green");
    Faculty faculty3 = new Faculty(0L, "Puffendui", "gray");
    Faculty faculty4 = new Faculty(0L, "Cogtevran", "yellow");
     List<Faculty> faculties = List.of(faculty1, faculty2, faculty3, faculty4);
    List<Student> students = List.of(new Student(1L, "Harry", 13));
    @Mock
    private FacultyRepository facultyRepository;
    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void beforeEach() {
        underTest = new FacultyServiceImpl(facultyRepository, studentRepository);
    }

    @Test
    void create__facultyCreateAndReturn() {
        when(facultyRepository.save(faculty1)).thenReturn(faculty1);
        assertEquals(faculty1, underTest.create(faculty1));
    }

    @Test
    void read_facultyIsInMap_readAndReturnedFaculty() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty1));
        Faculty result = underTest.read(1L);
        assertEquals(faculty1, result);
    }

    @Test
    void read_facultyIsNotInMap_returnedFacultyException() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());
        FacultyNotFoundException ex =
                assertThrows(FacultyNotFoundException.class,
                        () -> underTest.read(1L));
        assertEquals("Факультет не найден", ex.getMessage());
    }

    @Test
    void update_facultyIsUpdate_updateAndReturnedStudent() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty1));
        when(facultyRepository.save(faculty1)).thenReturn(faculty1);
        Faculty result = underTest.update(1L, faculty1);
        assertEquals(faculty1, result);
    }

    @Test
    void update_studentIsNotInMap_returnedStudentException() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());
        FacultyNotFoundException ex =
                assertThrows(FacultyNotFoundException.class,
                        () -> underTest.update(1, faculty1));
        assertEquals("Факультет не найден", ex.getMessage());
    }

    @Test
    void delete_facultyIsNotInMap_returnedFacultyException() {
        when(facultyRepository.findById(1L)).thenReturn(Optional.empty());
        FacultyNotFoundException ex =
                assertThrows(FacultyNotFoundException.class,
                        () -> underTest.delete(1L));
        assertEquals("Факультет не найден", ex.getMessage());
    }

    @Test
    void readAllFacultyByColor_facultyIsInMap_readAndReturnedFaculty() {
        when(facultyRepository.findByColor("brown")).thenReturn(List.of(faculty1));
        List<Faculty> result = underTest.readAllFacultiesByColor("brown");
        assertEquals(List.of(faculty1), result);
    }

    @Test
    void findStudentsByFacultyId_studentFind_FindAndReturnStudents() {
        when(studentRepository.findByFaculty_id(1L)).thenReturn(students);
        List<Student> result = underTest.findStudentsByFacultyId(1L);
        assertEquals(students, result);
    }

    @Test
    void findByNameOrColor_findFaculty_findAndReturnFaculty() {
        when(facultyRepository.findByNameOrColorIgnoreCase("Gryffindor", "brown")).thenReturn(faculty1);
        Faculty result = underTest.findFacultyByNameOrColor("Gryffindor", "brown");
        assertEquals(faculty1, result);
    }

    @Test
    void findLongestNameFaculty() {
        when(facultyRepository.findAll()).thenReturn(List.of(faculty1, faculty2));
        String result = underTest.findLongestNameFaculty();
        assertEquals(faculty1.getName(), result);
    }

}