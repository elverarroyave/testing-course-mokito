package ejemplos.repository;

import ejemplos.models.Exam;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamRepositoryOtroImpl implements ExamRepository{
    @Override
    public List<Exam> findAll() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Exam save(Exam exam) {
        return null;
    }
}
