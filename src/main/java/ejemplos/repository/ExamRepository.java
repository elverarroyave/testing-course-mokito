package ejemplos.repository;

import ejemplos.models.Exam;

import java.util.List;

public interface ExamRepository {
    List<Exam> findAll();
}
