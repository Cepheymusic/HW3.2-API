package ru.hogwarts.school.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.FacultyServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = {FacultyController.class})
public class FacultyControllerTest {
    @Autowired
    MockMvc mockMvc;
    @SpyBean
    FacultyServiceImpl facultyService;
    @Autowired
    FacultyController facultyController;

    @MockBean
    FacultyRepository facultyRepository;
    @MockBean
    StudentRepository studentRepository;
    @Autowired
    ObjectMapper objectMapper;
    Faculty faculty = new Faculty(1L, "Grif", "red");

    @Test
    void create__status200AndSavedToDb() throws Exception{
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        mockMvc.perform(post("/faculty")
                .content(objectMapper.writeValueAsString(faculty))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }
    @Test
    void update__status200AndUpdateToDb() throws Exception{
        when(facultyRepository.save(faculty)).thenReturn(faculty);
        mockMvc.perform(post("/faculty")
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(faculty.getName()))
                .andExpect(jsonPath("$.color").value(faculty.getColor()));
    }
    @Test
    void delete__status200AndDeleteToDb() throws Exception{
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));
        mockMvc.perform(delete("/faculty/" + faculty.getId())
                        .content(objectMapper.writeValueAsString(faculty))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(faculty.getId()));
    }
    @Test
    void readFacultyByColor__status200AndReturnList() throws Exception{
        when(facultyRepository.findByColor(faculty.getColor())).thenReturn(List.of(faculty));

        mockMvc.perform(get("/faculty/color/" + faculty.getColor()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(faculty.getName()))
                .andExpect(jsonPath("$[0].color").value(faculty.getColor()));
    }
    @Test
    void readStudentsByFacultyId__status200AndReturnList() throws Exception{
        when(facultyRepository.findById(1L)).thenReturn(Optional.of(faculty));

        mockMvc.perform(get("/faculty/student/" + faculty.getId()))
                .andExpect(status().isOk());
    }
//    @Test
//    void findByNameAndColor__status200AndNameAndColor() throws Exception{
//        when(facultyRepository.fin(1L)).thenReturn(Optional.of(faculty));
//        mockMvc.perform(delete("/faculty/search/" + faculty.getId())
//                        .content(objectMapper.writeValueAsString(faculty))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value(faculty.getId()))
//                .andExpect(jsonPath("$.color").value(faculty.getId()));
//    }
//    @Test
//    void findByNameAndColor__status200AndNameAndColor() throws Exception{
//        when(facultyRepository.fin(1L)).thenReturn("");
//        mockMvc.perform(delete("/faculty/longest-name-faculty/" + faculty.getId())
//                        .content(objectMapper.writeValueAsString(faculty))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$.name").value(faculty.getId()))
//                .andExpect(jsonPath("$.color").value(faculty.getId()));
//    }
}
