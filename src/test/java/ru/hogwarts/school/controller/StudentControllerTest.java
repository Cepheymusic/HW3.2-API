package ru.hogwarts.school.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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
    Student student1 = new Student(2L, "Harrys", 10);
    Student student2 = new Student(2L, "Arrys", 10);
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
    void read_studentNotInDb_returnStatus404AndStudent() {
        ResponseEntity<String> studentResponseEntity = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/" +
                        student.getId(), String.class);
        assertEquals(HttpStatus.NOT_FOUND, studentResponseEntity.getStatusCode());
        assertEquals("Студент не найден", studentResponseEntity.getBody());
    }

    @Test
    void update__returnStatus200AndStudent() {
        studentRepository.save(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + student.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(student),
                Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());
    }

    @Test
    void delete__returnStatus200AndStudent() {
        studentRepository.save(student);
        ResponseEntity<Student> studentResponseEntity = restTemplate.exchange(
                "http://localhost:" + port + "/student/" + student.getId(),
                HttpMethod.DELETE, new HttpEntity<>(student), Student.class);
        assertEquals(HttpStatus.OK, studentResponseEntity.getStatusCode());
        assertEquals(student.getName(), studentResponseEntity.getBody().getName());
        assertEquals(student.getAge(), studentResponseEntity.getBody().getAge());
    }


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

    @Test
    void readFacultyByStudentId__returnStatus200AndFaculty() {
        student.setFaculty(faculty);
        facultyRepository.save(faculty);
        studentRepository.save(student);


        ResponseEntity<Faculty> exchange = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/faculty/" + "2",
                Faculty.class);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(student.getFaculty(), exchange.getBody());
    }

    @Test
    void readAllByAgeBetween__returnStatus200AndStudentList() {
        studentRepository.save(student);
        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/search?min=1&max=15",
                HttpMethod.GET, null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(student), exchange.getBody());

    }

    @Test
    void findQualityStudents__returnStatus200AndQualityStudents() {
        studentRepository.save(student);
        ResponseEntity<Integer> exchange = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/count-all-students/",
                Integer.class);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(1, exchange.getBody());
    }
    @Test
    void findAvgAgeStudents__returnStatus200AndAvgAgeStudents() {
        studentRepository.save(student);
        studentRepository.save(student1);
        ResponseEntity<Integer> exchange = restTemplate.getForEntity(
                "http://localhost:" + port + "/student/avg-age/",
                Integer.class);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(10, exchange.getBody());
    }
    @Test
    void findFiveLastStudent__returnStatus200AndStudentList() {
        studentRepository.save(student);
        ResponseEntity<List<Student>> exchange = restTemplate.exchange(
                "http://localhost:" + port + "/student/last-five-students/",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Student>>() {
                });
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertEquals(List.of(student), exchange.getBody());
    }

//    @Test
//    void findStudentNameWithA__returnStatus200AndListNameStudent() {
//        studentRepository.save(student2);
//        studentRepository.save(student1);
//        ResponseEntity<List<String>> exchange = restTemplate.exchange(
//                "http://localhost:" + port + "/student/name-start-with-a/",
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<String>>() {
//                });
//        assertEquals(HttpStatus.OK, exchange.getStatusCode());
//        assertEquals(List.of("Arrys"), exchange.getBody());
//    }
//    @Test
//    void findAverageAgeStudents__returnStatus200AndAverageAgeStudent() {
//        studentRepository.save(student2);
//        studentRepository.save(student1);
//        ResponseEntity<Double> exchange = restTemplate.getForEntity(
//                "http://localhost:" + port + "/student/name-start-with-a/", Double.class);
//        assertEquals(HttpStatus.OK, exchange.getStatusCode());
//        assertEquals(10, exchange.getBody());
//    }
}