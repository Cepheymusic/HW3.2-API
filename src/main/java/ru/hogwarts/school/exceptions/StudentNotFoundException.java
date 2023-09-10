package ru.hogwarts.school.exceptions;

public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String massage) {
        super(massage);
    }
}
