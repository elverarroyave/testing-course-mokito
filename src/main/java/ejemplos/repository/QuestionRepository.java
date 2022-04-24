package ejemplos.repository;

import java.util.List;

public interface QuestionRepository {
    List<String> findQuestionsForExamId(Long id);

    void saveQustions(List<String> questions, Long examId);
}
