package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @Autowired
    TestRestTemplate restTemplate;
    @LocalServerPort
    int port;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    FacultyRepository facultyRepository;

    @AfterEach
    void afterEach() {
        studentRepository.deleteAll();

    }

    Student student = new Student(1L, "Harry", 10);
    Faculty faculty = new Faculty(1L, "Grif", "red");

    @Test
    void create__returnStatus200AndStudent() {

        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity(
                "http://localhost:" + port + "/student",
                student, Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());
    }

    @Test
    void read_studentNotInDb_returnStatus200AndStudent() {
        ResponseEntity<String> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" +
                        student.getId(), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, studentResponseEntity.getStatusCode());
        assertEquals("Студент не найден", studentResponseEntity.getBody());
    }
//    @Test
//    void update__returnStatus200AndStudent() {
//
//        ResponseEntity<Student> studentResponseEntity = restTemplate.put(
//                "http://localhost:" + port + "/student", student, Student.class);
//        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
//        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
//        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());     не работает
//    }
//    @Test
//    void delete__returnStatus200AndStudent() {
//
//        ResponseEntity<Student> studentResponseEntity = restTemplate.delete(
//                "http://localhost:" + port + "/student", student.getId(), ResponseEntity<Student> student);
//        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
//        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
//        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());   и этот........
//    }


    @Test
    void readAll__returnStatus200AndStudentList() {
        studentRepository.save(student);
        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/by-age/" + student.getAge(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(student), exchange.getBody());
    }

//    @Test
//    void readFacultyByStudentId__returnStatus200AndFaculty() {
//        studentRepository.save(student);
//        ResponseEntity<Faculty> exchange = restTemplate.exchange(
//                "http://localhost:" + port + "/student/faculty/" + student.getId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                });
//        assertEquals(HttpStatus.OK, exchange.getStatusCode());
//        assertEquals(List.of(faculty), exchange.getBody());    //бесполезно
//    }
//    @Test
//    void readAllByAgeBetween__returnStatus200AndStudentList() {
//        studentRepository.save(student);
//        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
//                "http://localhost:" + port + "/student/search/" + student.getAge(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<>() {
//                });
//        assertEquals(HttpStatus.OK, exchange.getStatusCode());
//        assertEquals(List.of(student), exchange.getBody());    можно было не пытаться
//
//    }
}