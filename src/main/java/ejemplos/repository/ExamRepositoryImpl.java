package ejemplos.repository;

import ejemplos.models.Exam;

import java.util.Arrays;
import java.util.List;

public class ExamRepositoryImpl implements ExamRepository {

    @Override
    public List<Exam> findAll() {
        return Arrays.asList(
                new Exam(9L, "Matemáticas"),
                new Exam(10L, "Español"),
                new Exam(11L, "Sociales"),
                new Exam(12L, "Biología"),
                new Exam(13L, "Artes"),
                new Exam(14L, "Matemáticas"));

    }
}
