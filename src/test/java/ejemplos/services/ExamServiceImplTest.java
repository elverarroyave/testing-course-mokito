package ejemplos.services;

import ejemplos.models.Exam;
import ejemplos.repository.ExamRepository;
import ejemplos.repository.ExamRepositoryImpl;
import ejemplos.repository.ExamRepositoryOtroImpl;
import ejemplos.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExamServiceImplTest {

    ExamRepository examRepository;
    ExamService examService;
    QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
        examRepository = mock(ExamRepository.class);
        questionRepository = mock(QuestionRepository.class);
        examService = new ExamServiceImpl(examRepository, questionRepository);
    }

    @Test
    void findExamForName() {
        //ExamRepository examRepository = new ExamRepositoryImpl();

        //no se pueden realizar mocks de métodos que no sean privados o estaticos
        //Le decimos a mokito que cuando se invoque el metodo findAll() de examRepository, devuelva la lista de examenes
        when(examRepository.findAll()).thenReturn(DataForTest.exams);

        Optional<Exam> exam = examService.findExamForName("Matemáticas");

        assertTrue(exam.isPresent());
        assertThat(exam.orElseThrow().getId()).isEqualTo(6L);
        assertEquals("Matemáticas", exam.orElseThrow().getName());

        System.out.println(exam.toString());

    }

    @Test
    void findExamForNameInVoidList() {
        List<Exam> exams = new ArrayList<>();
        when(examRepository.findAll()).thenReturn(exams);
        Optional<Exam> exam = examService.findExamForName("Matemáticas");
        assertFalse(exam.isPresent());
        System.out.println(exam.toString());
    }

    @Test
    void testQuestionExam() {
        when(examRepository.findAll()).thenReturn(DataForTest.exams);
        //when(questionRepository.findQuestionsForExamId(6L)).thenReturn(DataForTest.QUESTIONS);
        when(questionRepository.findQuestionsForExamId(anyLong())).thenReturn(DataForTest.QUESTIONS);
        Exam exam = examService.findExamForNameWithQuestion("Matemáticas");
        assertThat(exam.getQuestions().size()).isEqualTo(4);
        assertThat(exam.getQuestions().get(0)).isEqualTo("¿Cuál es la suma de los números del 1 al 10?");
    }

    @Test
    void testQuestionExamVerify() {
        when(examRepository.findAll()).thenReturn(DataForTest.exams);
        //when(questionRepository.findQuestionsForExamId(6L)).thenReturn(DataForTest.QUESTIONS);
        when(questionRepository.findQuestionsForExamId(anyLong())).thenReturn(DataForTest.QUESTIONS);
        Exam exam = examService.findExamForNameWithQuestion("Matemáticas");
        assertThat(exam.getQuestions().size()).isEqualTo(4);
        assertThat(exam.getQuestions().get(0)).isEqualTo("¿Cuál es la suma de los números del 1 al 10?");

        //Metodo para verificar que el metodo nombrado en el test se ha invocado
        verify(examRepository, times(1)).findAll();
        verify(questionRepository, times(1)).findQuestionsForExamId(6L);
    }


}