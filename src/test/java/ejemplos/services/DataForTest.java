package ejemplos.services;

import ejemplos.models.Exam;

import java.util.Arrays;
import java.util.List;

public class DataForTest {
    public static List<Exam> exams = Arrays.asList(
            new Exam(5L, "Ciencias"),
            new Exam(6L, "Matemáticas"),
            new Exam(7L, "Lengua"),
            new Exam(8L, "Física"),
            new Exam(9L, "Química"),
            new Exam(10L, "Matemáticas")
    );

    public static List<Exam> EXAMS_ID_NEGATIVES = Arrays.asList(
            new Exam(-5L, "Ciencias"),
            new Exam(-6L, "Matemáticas")
    );

    public final static List<String>   QUESTIONS_MATH = Arrays.asList(
            "¿Cuál es la suma de los números del 1 al 10?",
            "¿Cuál es la suma de los números del 1 al 20?",
            "¿Cuál es la suma de los números del 1 al 30?",
            "¿Cuál es la suma de los números del 1 al 40?"

    );

    public final static List<String>   ANSWER = Arrays.asList(
            "55",
            "210",
            "555",
            "1020"
    );

    public final static Exam EXAMEN = new Exam(null, "Física");


}
