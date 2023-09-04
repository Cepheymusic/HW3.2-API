package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final Map<Long, Student> students = new HashMap<>();
    private long counter;

    @Override
    public Student create(Student student) {
        if(students.containsValue(student)){
            throw new StudentException("Студент существует");
        }
        student.setId(++counter);
        students.put(student.getId(), student);
        return student;
    }

    @Override
    public Student read(long id) {
        if(!students.containsKey(id)){
            throw new StudentException("Студент не найден");
        }
        return students.get(id);
    }

    @Override
    public Student update(Student student) {
        if (!students.containsKey(student.getId())) {
            throw new StudentException("Студент не найден");
        }
        return students.put(student.getId(), student);
    }

    @Override
    public Student delete(long id) {
        if (!students.containsKey(id)) {
            throw new StudentException("Студент не найден");
        }
        return students.remove(id);
    }

    @Override
    public List<Student> readAllStudentByAge(int age) {
        return students.values().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toUnmodifiableList());
    }
}
