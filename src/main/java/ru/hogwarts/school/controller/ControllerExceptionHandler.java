package ru.hogwarts.school.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.exceptions.StudentNotFoundException;

import java.util.Arrays;

@RestControllerAdvice
public class ControllerExceptionHandler {
    Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
    @ExceptionHandler({StudentNotFoundException.class, FacultyNotFoundException.class})
    public ResponseEntity<String> handleStudentException(RuntimeException ex){
        logger.warn(ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception exception) {
        logger.error(String.valueOf(exception));
        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Ошибка сервера, приносим извинения");
    }
}
