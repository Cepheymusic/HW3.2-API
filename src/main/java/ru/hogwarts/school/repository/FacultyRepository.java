package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.model.Faculty;

import java.util.List;
import java.util.Optional;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Faculty findByNameOrColorIgnoreCase(String name, String color);

    List<Faculty> findByColor(String color);
    //Faculty findByStudent_id(long student_id);
}
