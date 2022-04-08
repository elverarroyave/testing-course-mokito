package ejemplos.models;

import java.util.ArrayList;
import java.util.List;

public class Exam {

    private Long id;

    private String name;

    private List<String> questions;

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }

    // Constructor with id and name
    public Exam(Long id, String name) {
        this.id = id;
        this.name = name;
        this.questions = new ArrayList<>();
    }

    // to string method
    @Override
    public String toString() {
        return "Exam{" + "id=" + id + ", name=" + name + ", questions=" + questions + '}';
    }


}
