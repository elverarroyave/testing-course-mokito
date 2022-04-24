package ejemplos.services;

import ejemplos.config.BadRequestException;
import ejemplos.models.Exam;
import ejemplos.repository.ExamRepository;
import ejemplos.repository.QuestionRepository;

import java.util.List;
import java.util.Optional;

public class ExamServiceImpl implements ExamService {

    private ExamRepository  examRepository;
    private QuestionRepository questionRepository;

    @Override
    public Optional<Exam> findExamForName(String name) {
        Optional<Exam> examResponse = examRepository.findAll().stream().filter(exam -> exam.getName()
                .equalsIgnoreCase(name))
                .findFirst();

        if (examResponse == null)
            throw new BadRequestException("Exam not found");

        return examResponse;
    }

    @Override
    public Exam findExamForNameWithQuestion(String name) {
        Optional<Exam> examOptional = findExamForName(name);
        Exam exam = null;
        if (examOptional.isPresent()) {
            exam = examOptional.orElseThrow();
            List<String> questions = questionRepository.findQuestionsForExamId(exam.getId());
            exam.setQuestions(questions);
        }
        return exam;
    }

    @Override
    public Exam save(Exam exam) {
        if(!exam.getQuestions().isEmpty()){
            questionRepository.saveQustions(exam.getQuestions(), exam.getId());
        }
        return examRepository.save(exam);
    }

    ExamServiceImpl(ExamRepository examRepository, QuestionRepository questionRepository) {
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
    }


}
