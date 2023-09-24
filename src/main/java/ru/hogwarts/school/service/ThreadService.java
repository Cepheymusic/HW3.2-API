package ru.hogwarts.school.service;

import liquibase.pro.packaged.L;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.List;

@Service
public class ThreadService {
    private Logger logger = LoggerFactory.getLogger(ThreadService.class);

    private StudentRepository studentRepository;

    public ThreadService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    public void threadSync() {
        List<Student> all = studentRepository.findAll();

        logStudentSync(all.get(0));
        logStudentSync(all.get(1));
        new Thread(() -> {
            logStudentSync(all.get(2));
            logStudentSync(all.get(3));
        }).start();
        new Thread(() -> {
            logStudentSync(all.get(4));
            logStudentSync(all.get(5));
        }).start();
    }
    public void thread() {
        List<Student> all = studentRepository.findAll();

        logStudent(all.get(0));
        logStudent(all.get(1));
        new Thread(() -> {
            logStudent(all.get(2));
            logStudent(all.get(3));
        }).start();
        new Thread(() -> {
            logStudent(all.get(4));
            logStudent(all.get(5));
        }).start();
    }

    public synchronized void logStudentSync(Student student) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException a) {
            a.printStackTrace();
        }

        logger.info(student.toString());

    }
    public void logStudent(Student student) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException a) {
            a.printStackTrace();
        }

        logger.info(student.toString());

    }
}
