package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {


    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;
    public StudentServiceImpl(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    private final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);
    @Override
    public Student create(Student student) {
        logger.info("Был вызван метод create с данными" + student);
        Student savedStudent = studentRepository.save(student);
        logger.info("Из метода create вернули" + student);
        return savedStudent;
    }

    @Override
    public Student read(long id) {
        logger.info("Был вызван метод read с данными" + id);
        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()){
            throw new StudentNotFoundException("Студент не найден");
        }
        Student readStudent = student.get();
        logger.info("Из метода read вернули" + student.get());
        return readStudent;
    }

    @Override
    public Student update(long id, Student student) {
        logger.info("Был вызван метод update с данными: {},{}", id, student);
        if(studentRepository.findById(id).isEmpty()) {
            throw new StudentNotFoundException("Студент не найден");
        }
        Student updateStudent = studentRepository.save(student);
        logger.info("Из метода update вернули" + student);
        return updateStudent;
    }

    @Override
    public Student delete(long id) {
        logger.info("Был вызван метод delete с данными" + id);
        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()) {
            throw new StudentNotFoundException("Студент не найден");
        }
        studentRepository.deleteById(id);
        Student deleteStudent = student.get();
        logger.info("Из метода delete вернули" + deleteStudent);
        return deleteStudent;
    }

    @Override
    public List<Student> readAllStudentByAge(int age) {
        logger.info("Был вызван метод readAllStudentByAge с данными" + age);
        List<Student> readStudent = studentRepository.findByAge(age);
        logger.info("Из метода readAllStudentByAge вернули" + readStudent);
        return readStudent;
    }
    @Override
    public Faculty findFacultyByStudentId(long id) {
        logger.info("Был вызван метод findFacultyByStudentId с данными" + id);
        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()){
            throw new StudentNotFoundException("Студент не найден");
        }
        Faculty findFaculty = student.get().getFaculty();
        logger.info("Из метода findFacultyByStudentId вернули" + findFaculty);
        return findFaculty;
    }
    @Override
    public List<Student> findStudentByAgeBetween(int min, int max) {
        logger.info("Был вызван метод findStudentByAgeBetween с данными: {},{}", min, max);
        List<Student> findStudent = studentRepository.findByAgeBetween(min, max);
        logger.info("Из метода findStudentByAgeBetween вернули" + findStudent);
        return findStudent;
    }

    @Override
    public Integer findQuantityStudents() {
        Integer findQuantity = studentRepository.findQuantityStudents();
        logger.info("Из метода findQuantityStudents вернули" + findQuantity);
        return findQuantity;

    }

    @Override
    public Integer findAvgAgeStudents() {
        Integer findAvgAge = studentRepository.findAvgAgeStudents();
        logger.info("Из метода findAvgAgeStudents вернули" + findAvgAge);
        return findAvgAge;
    }

    @Override
    public List<Student> findLastStudents(int limit) {
        logger.info("Был вызван метод findLastStudents с данными" + limit);
        List<Student> findStudent = studentRepository.findLastStudents(limit);
        logger.info("Из метода findLastStudents вернули" + findStudent);
        return findStudent;
    }
}