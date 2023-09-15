package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByNameAndAge(String name, int age);

    List<Student> findByAge(int age);

    List<Student> findByAgeBetween(int min, int max);

    List<Student> findByFaculty_id(long facultyId);

    @Query("select count(s) from Student s")
    Integer findQuantityStudents();

    @Query("select avg(s) from Student s")
    Integer findAvgAgeStudents();

    @Query(value = "select s from Student s order by desc s.id limit :limit", nativeQuery = true)
    List<Student> findLastStudents(int limit);
}
