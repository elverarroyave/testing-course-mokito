package ejemplos.services;

import ejemplos.models.Exam;
import ejemplos.repository.ExamRepository;
import ejemplos.repository.ExamRepositoryImpl;
import ejemplos.repository.ExamRepositoryOtroImpl;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExamServiceImplTest {

    @Test
    void findExamForName() {
        //ExamRepository examRepository = new ExamRepositoryImpl();
        ExamRepository examRepository = mock(ExamRepositoryOtroImpl.class);
        ExamService examService = new ExamServiceImpl(examRepository);

        List<Exam> exams = Arrays.asList(
                new Exam(5L, "Ciencias"),
                new Exam(6L, "Matemáticas"),
                new Exam(7L, "Lengua"),
                new Exam(8L, "Física"),
                new Exam(9L, "Química"),
                new Exam(10L, "Matemáticas")
        );


        //no se pueden realizar mocks de métodos que no sean privados o estaticos
        //Le decimos a mokito que cuando se invoque el metodo findAll() de examRepository, devuelva la lista de examenes
        when(examRepository.findAll()).thenReturn(exams);

        Optional<Exam> exam = examService.findExamForName("Matemáticas");

        assertTrue(exam.isPresent());
        assertThat(exam.orElseThrow().getId()).isEqualTo(6L);
        assertEquals("Matemáticas", exam.orElseThrow().getName());

        System.out.println(exam.toString());

    }
}