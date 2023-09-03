package ru.hogwarts.school.exceptions;

public class StudentException extends RuntimeException{
    public StudentException(String massage) {
        super(massage);
    }
}
