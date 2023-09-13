package ru.hogwarts.school.service;

import nonapi.io.github.classgraph.utils.FileUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptions.AvatarNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AvatarServiceImplTest {
    StudentService studentService = mock(StudentService.class);
    AvatarRepository avatarRepository = mock(AvatarRepository.class);
    String avatarDir = "./src/test/resources/avatar";
    AvatarServiceImpl underTest = new AvatarServiceImpl(studentService, avatarRepository, avatarDir);

    Student student1 = new Student(0L, "Harry", 35);
    Student student2 = new Student(0L, "Harry", 35);
    Student student3 = new Student(0L, "Sam", 36);
    @Test
    void uploadAvatar__avatarSavedToDbAndDirectory() throws IOException {
        MultipartFile file = new MockMultipartFile("11.pdf", "11.pdf", "pdf", new byte[]{});
        when(studentService.read(student1.getId())).thenReturn(student1);
        when(avatarRepository.findByStudentId(student1.getId())).thenReturn(Optional.empty());
        underTest.uploadAvatar(student1.getId(), file);
        verify(avatarRepository, times(1)).save(any());
        assertTrue(FileUtils.canRead(new File(avatarDir + "/" + student1.getId() + "." + file.getContentType())));
    }

    @Test
    void readFromDb_avatarIsNotFound_returnNotFoundException() {
        when(avatarRepository.findById(1L)).thenReturn(Optional.empty());
        AvatarNotFoundException ex =
                assertThrows(AvatarNotFoundException.class, () -> underTest.readFromDb(1L));
        assertEquals("Аватар не найден", ex.getMessage());

    }
    @Test
    void readFromDb_avatarIsFind_findAndReturnAvatar() {
        Avatar avatar = new Avatar();
        avatar.setId(1L);
        avatar.setFileSize(20);
        avatar.setData(new byte[]{});
        avatar.setFilePath("./src/test/resources/avatar");
        avatar.setMediaType("image/jpg");
        when(avatarRepository.findById(1L)).thenReturn(Optional.of(avatar));
        Avatar result = underTest.readFromDb(1L);
        assertEquals(avatar, result);

    }

}