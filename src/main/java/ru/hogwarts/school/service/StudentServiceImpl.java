package ru.hogwarts.school.service;

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


    @Override
    public Student create(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student read(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()){
            throw new StudentNotFoundException("Студент не найден");
        }
        return student.get();
    }

    @Override
    public Student update(long id, Student student) {
        if(studentRepository.findById(id).isEmpty()) {
            throw new StudentNotFoundException("Студент не найден");
        }
        return studentRepository.save(student);
    }

    @Override
    public Student delete(long id) {
        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()) {
            throw new StudentNotFoundException("Студент не найден");
        }
        studentRepository.deleteById(id);
        return student.get();
    }

    @Override
    public List<Student> readAllStudentByAge(int age) {
        return studentRepository.findByAge(age);
    }
    @Override
    public Faculty findById(long id) {
        Faculty faculty = facultyRepository.findByStudent_id(id);
        if(faculty == null){
            throw new FacultyNotFoundException("Факультет не найден");
        }
        return faculty;
    }
    @Override
    public List<Student> findStudentByAgeBetween(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }
    }