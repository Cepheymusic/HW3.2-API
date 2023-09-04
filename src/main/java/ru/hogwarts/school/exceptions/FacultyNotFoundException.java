package ru.hogwarts.school.exceptions;

public class FacultyNotFoundException extends RuntimeException {
    public FacultyNotFoundException(String massage) {
        super(massage);
    }
}
