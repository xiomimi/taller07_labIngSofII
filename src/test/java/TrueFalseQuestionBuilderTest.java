import builder.TrueFalseQuestionBuilder;
import director.QuestionDirector;
import domain.DifficultyLevel;
import domain.Question;
import domain.QuestionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para TrueFalseQuestionBuilder.
 * Verifica la construccion de preguntas de tipo Verdadero / Falso,
 * validacion de campos obligatorios, consistencia de opciones fijas y respuestas validas.
 */
public class TrueFalseQuestionBuilderTest {

    private TrueFalseQuestionBuilder builder;

    @BeforeEach
    public void setUp() {
        builder = new TrueFalseQuestionBuilder();
    }

    @Test
    @DisplayName("Construcción correcta: Debe ser posible construir una pregunta V/F con respuesta Verdadero")
    public void testConstruccionCorrectaVerdadero() {
        builder.buildStatement("El patron Builder es un patron de diseno creacional de GoF.");
        builder.buildDifficulty(DifficultyLevel.BAJA);
        builder.buildCategory("Diseno de Software");
        builder.buildAuthor("Profesor");
        builder.buildCorrectAnswer("Verdadero");

        Question question = builder.build();

        assertNotNull(question);
        assertEquals(QuestionType.TRUE_FALSE, question.getType());
        assertEquals("Verdadero", question.getCorrectAnswer());
        assertEquals(2, question.getOptions().size());
        assertTrue(question.getOptions().stream().anyMatch(o -> o.getText().equals("Verdadero") && o.isCorrect()));
        assertTrue(question.getOptions().stream().anyMatch(o -> o.getText().equals("Falso") && !o.isCorrect()));
    }

    @Test
    @DisplayName("Construcción correcta: Debe ser posible construir una pregunta V/F con respuesta Falso")
    public void testConstruccionCorrectaFalso() {
        builder.buildStatement("El patron Builder requiere que todos los productos hereden de la misma interfaz.");
        builder.buildDifficulty(DifficultyLevel.MEDIA);
        builder.buildCorrectAnswer("Falso");

        Question question = builder.build();

        assertNotNull(question);
        assertEquals("Falso", question.getCorrectAnswer());
        assertTrue(question.getOptions().stream().anyMatch(o -> o.getText().equals("Falso") && o.isCorrect()));
    }

    @Test
    @DisplayName("Validación de campos obligatorios: No debe ser posible construir sin enunciado")
    public void testValidacionSinEnunciado() {
        builder.buildCorrectAnswer("Verdadero");

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> builder.build());
        assertTrue(ex.getMessage().contains("El enunciado de la pregunta es obligatorio"));
    }

    @Test
    @DisplayName("Validación de campos obligatorios: Sin respuesta correcta especificada")
    public void testValidacionSinRespuestaCorrecta() {
        builder.buildStatement("Una declaracion de prueba.");
        // No se asigna respuesta correcta

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> builder.build());
        assertTrue(ex.getMessage().contains("Debe especificarse si la respuesta correcta es 'Verdadero' o 'Falso'"));
    }

    @Test
    @DisplayName("Respuestas válidas: La respuesta no puede ser distinta de Verdadero o Falso")
    public void testValidacionRespuestaInvalida() {
        builder.buildStatement("Una declaracion de prueba.");
        builder.buildCorrectAnswer("Quizas");

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> builder.build());
        assertTrue(ex.getMessage().contains("debe ser 'Verdadero' o 'Falso'"));
    }

    @Test
    @DisplayName("Construcción orquestada mediante QuestionDirector")
    public void testConstruccionConQuestionDirector() {
        QuestionDirector director = new QuestionDirector(builder);

        director.constructCompleteQuestion(
            "Contexto de evaluacion",
            "¿Java es un lenguaje de programacion fuertemente tipado?",
            DifficultyLevel.BAJA,
            "Programacion",
            "Docente",
            "Java realiza chequeo de tipos en tiempo de compilacion.",
            "Oracle Documentation",
            null,
            "Verdadero"
        );

        Question question = director.getQuestion();
        assertNotNull(question);
        assertEquals(QuestionType.TRUE_FALSE, question.getType());
        assertEquals("Verdadero", question.getCorrectAnswer());
    }
}
