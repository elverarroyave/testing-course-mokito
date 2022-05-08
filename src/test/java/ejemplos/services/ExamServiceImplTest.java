package ejemplos.services;

import ejemplos.models.Exam;
import ejemplos.repository.ExamRepository;
import ejemplos.repository.ExamRepositoryImpl;
import ejemplos.repository.ExamRepositoryOtroImpl;
import ejemplos.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.security.PrivilegedActionException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamServiceImplTest {

    //Anotaciones para crear los mock
    @Mock
    private ExamRepository examRepository;
    @Mock
    private QuestionRepository questionRepository;


    @Captor
    private ArgumentCaptor<Long> captor;

    //Creamos servicios
    @InjectMocks
    private ExamServiceImpl examService;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this); //Habilitamos el uso de anotaciones para los mocks
////        examRepository = mock(ExamRepository.class);
////        questionRepository = mock(QuestionRepository.class);
////        examService = new ExamServiceImpl(examRepository, questionRepository);
//    }

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
        when(questionRepository.findQuestionsForExamId(anyLong())).thenReturn(DataForTest.QUESTIONS_MATH);
        Exam exam = examService.findExamForNameWithQuestion("Matemáticas");
        assertThat(exam.getQuestions().size()).isEqualTo(4);
        assertThat(exam.getQuestions().get(0)).isEqualTo("¿Cuál es la suma de los números del 1 al 10?");
    }

    @Test
    void testQuestionExamVerify() {
        //Given
        when(examRepository.findAll()).thenReturn(DataForTest.exams);
        //when(questionRepository.findQuestionsForExamId(6L)).thenReturn(DataForTest.QUESTIONS);
        when(questionRepository.findQuestionsForExamId(anyLong())).thenReturn(DataForTest.QUESTIONS_MATH);

        //When
        Exam exam = examService.findExamForNameWithQuestion("Matemáticas");

        //Then
        assertThat(exam.getQuestions().size()).isEqualTo(4);
        assertThat(exam.getQuestions().get(0)).isEqualTo("¿Cuál es la suma de los números del 1 al 10?");

        //Metodo para verificar que el metodo nombrado en el test se ha invocado
        verify(examRepository, times(1)).findAll();
        verify(questionRepository, times(1)).findQuestionsForExamId(6L);
    }

    @Test
    void testSaveExam() {

        // Given
        Exam newExam = DataForTest.EXAMEN;
        newExam.setQuestions(DataForTest.QUESTIONS_MATH);

        when(examRepository.save(any(Exam.class))).then(new Answer<Exam>() {

            Long secuencia = 8L;
            @Override
            public Exam answer(InvocationOnMock invocation) throws Throwable {
                Exam exam = invocation.getArgument(0);
                exam.setId(secuencia++);
                return exam;
            }
        });

        // When
        Exam exam = examService.save(newExam);

        // Then
        assertNotNull(exam.getId());
        assertThat(exam.getId()).isEqualTo(8L);
        assertThat(exam.getName()).isEqualTo("Física");

        System.out.println(exam.toString());

        verify(examRepository).save(any(Exam.class));
        verify(questionRepository).saveQustions(anyList(), anyLong());

    }


    @Test
    void TestManejoException() {
        when(examRepository.findAll()).thenReturn(DataForTest.exams);
        when(questionRepository.findQuestionsForExamId(anyLong())).thenThrow(IllegalArgumentException.class);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            examService.findExamForNameWithQuestion("Matemáticas");
        });

        assertThat(IllegalArgumentException.class).isEqualTo(exception.getClass());

        verify(examRepository, times(1)).findAll();
        verify(questionRepository, times(1)).findQuestionsForExamId(anyLong());

    }

    @Test
    void testArgumentMatchers() {
        when(examRepository.findAll()).thenReturn(DataForTest.exams);
        when(questionRepository.findQuestionsForExamId(anyLong())).thenReturn(DataForTest.QUESTIONS_MATH);

        examService.findExamForNameWithQuestion("Matemáticas");

        verify(questionRepository).findQuestionsForExamId(argThat(arg -> arg.equals(6L)));
        verify(questionRepository).findQuestionsForExamId(argThat(arg -> arg != null && arg.equals(6L)));
    }

    @Test
    void testArgumentMatchers2() {
        when(examRepository.findAll()).thenReturn(DataForTest.exams);
        when(questionRepository.findQuestionsForExamId(anyLong())).thenReturn(DataForTest.QUESTIONS_MATH);

        examService.findExamForNameWithQuestion("Matemáticas");

        verify(examRepository).findAll();
        verify(questionRepository).findQuestionsForExamId(argThat(new MiArgsMatchers()));
    }

    public static class MiArgsMatchers implements ArgumentMatcher<Long> {

        private Long argument;

        @Override
        public boolean matches(Long arguments) {
            this.argument = arguments;
            return arguments != null && arguments > 0;
        }

        @Override
        public String toString() {
            return "Mensaje personalizado de error, cuando falla el test de Mockito." +
                    "El argumento es: " + argument + "," +
                    "  Debe ser un entero positivo";
        }
    }


    //Capturar el argumento de un test

    @Test
    void testArgumentCaptor() {
        when(examRepository.findAll()).thenReturn(DataForTest.exams);
        when(questionRepository.findQuestionsForExamId(anyLong())).thenReturn(DataForTest.QUESTIONS_MATH);

        examService.findExamForNameWithQuestion("Matemáticas");

        //ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);
        verify(questionRepository).findQuestionsForExamId(captor.capture());

        assertThat(captor.getValue()).isEqualTo(6L);
    }


}
