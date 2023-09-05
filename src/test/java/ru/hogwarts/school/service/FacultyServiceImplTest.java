package ru.hogwarts.school.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.model.Faculty;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FacultyServiceImplTest {
    FacultyService underTest = new FacultyServiceImpl();

    Faculty faculty1 = new Faculty(0L, "Gryffindor", "brown");
    Faculty faculty2 = new Faculty(0L, "Slyseryne", "green");
    Faculty faculty3 = new Faculty(0L, "Puffendui", "gray");
    Faculty faculty4 = new Faculty(0L, "Cogtevran", "yellow");

    @BeforeEach
    void beforeEach() {
        underTest = new FacultyServiceImpl();
    }
    @Test
    void create__facultyCreateAndReturn() {
        Faculty result = underTest.create(faculty1);
        assertEquals(faculty1, result);
    }

    @Test
    void create_facultyIsInMap_returnFacultyException() {
        underTest.create(faculty1);
        FacultyNotFoundException ex =
                assertThrows(FacultyNotFoundException.class,
                        () -> underTest.create(faculty1));
        assertEquals("Факультет существует", ex.getMessage());

    }

    @Test
    void read_facultyIsInMap_readAndReturnedFaculty() {
        underTest.create(faculty1);
        underTest.create(faculty2);
        Faculty result = underTest.read(2L);
        assertEquals(faculty2, result);
    }
    @Test
    void read_facultyIsNotInMap_returnedFacultyException() {
        underTest.create(faculty1);
        FacultyNotFoundException ex =
                assertThrows(FacultyNotFoundException.class,
                        () -> underTest.read(2L));
        assertEquals("Факультет не найден", ex.getMessage());
    }
    @Test
    void update_facultyIsUpdate_updateAndReturnedStudent() {
        underTest.create(faculty1);
        Faculty result = underTest.update(faculty1);
        assertEquals(faculty1, result);
    }
    @Test
    void update_studentIsNotInMap_returnedStudentException() {
        underTest.create(faculty1);
        FacultyNotFoundException ex =
                assertThrows(FacultyNotFoundException.class,
                        () -> underTest.update(faculty3));
        assertEquals("Факультет не найден", ex.getMessage());
    }
    @Test
    void delete_facultyDelete_deleteAndReturnedFaculty() {
        underTest.create(faculty1);
        Faculty result = underTest.delete(1L);
        assertEquals(faculty1, result);
    }
    @Test
    void delete_facultyIsNotInMap_returnedFacultyException() {
        underTest.create(faculty1);
        FacultyNotFoundException ex =
                assertThrows(FacultyNotFoundException.class,
                        () -> underTest.delete(2L));
        assertEquals("Факультет не найден", ex.getMessage());
    }
    @Test
    void readAllFacultyByColor_facultyIsInMap_readAndReturnedFaculty() {
        underTest.create(faculty3);
        underTest.create(faculty1);
        underTest.create(faculty2);
        List<Faculty> result = underTest.readAllFacultiesByColor("brown");
        assertFalse(result.isEmpty());
    }

}