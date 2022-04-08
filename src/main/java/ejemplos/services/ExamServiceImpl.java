package ejemplos.services;

import ejemplos.config.BadRequestException;
import ejemplos.models.Exam;
import ejemplos.repository.ExamRepository;

import java.util.Optional;

public class ExamServiceImpl implements ExamService {

    private ExamRepository  examRepository;

    @Override
    public Optional<Exam> findExamForName(String name) {
        Optional<Exam> examResponse = examRepository.findAll().stream().filter(exam -> exam.getName()
                .equalsIgnoreCase(name))
                .findFirst();

        if (examResponse == null)
            throw new BadRequestException("Exam not found");

        return examResponse;
    }

    ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }
}
