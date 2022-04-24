package ejemplos.services;

import ejemplos.models.Exam;

import java.util.Optional;

public interface ExamService {
    Optional<Exam> findExamForName(String name);
    Exam findExamForNameWithQuestion(String name);

    Exam save(Exam exam);
}
