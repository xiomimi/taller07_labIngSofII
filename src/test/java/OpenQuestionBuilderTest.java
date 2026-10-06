import builder.MultipleChoiceQuestionBuilder;
import builder.OpenQuestionBuilder;
import builder.TrueFalseQuestionBuilder;
import director.QuestionDirector;
import domain.DifficultyLevel;
import domain.Option;
import domain.Question;
import domain.QuestionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para OpenQuestionBuilder y demostracion
 * de construccion polimorfica de diferentes productos mediante el Director.
 */
public class OpenQuestionBuilderTest {

    private OpenQuestionBuilder builder;

    @BeforeEach
    public void setUp() {
        builder = new OpenQuestionBuilder();
    }

    @Test
    @DisplayName("Construcción correcta: Debe ser posible construir una pregunta abierta válida")
    public void testConstruccionCorrectaPreguntaAbierta() {
        builder.buildStatement("Explique en sus propias palabras el principio de Inversion de Dependencias (DIP).");
        builder.buildDifficulty(DifficultyLevel.ALTA);
        builder.buildCategory("Principios SOLID");
        builder.buildAuthor("Profesor LISW2");
        builder.buildJustification("El principio establece que los modulos de alto nivel no deben depender de los de bajo nivel.");
        builder.buildCorrectAnswer("Criterio: Mencionar abstracciones, interfaces y desacoplamiento.");

        Question question = builder.build();

        assertNotNull(question);
        assertEquals(QuestionType.OPEN, question.getType());
        assertEquals("Explique en sus propias palabras el principio de Inversion de Dependencias (DIP).", question.getStatement());
        assertTrue(question.getOptions().isEmpty(), "Las preguntas abiertas no deben tener opciones cerradas");
        assertEquals("Criterio: Mencionar abstracciones, interfaces y desacoplamiento.", question.getCorrectAnswer());
    }

    @Test
    @DisplayName("Validación de campos obligatorios: No debe ser posible construir sin enunciado")
    public void testValidacionSinEnunciado() {
        builder.buildAuthor("Profesor");
        builder.buildCorrectAnswer("Respuesta esperada");

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> builder.build());
        assertTrue(ex.getMessage().contains("El enunciado de la pregunta es obligatorio"));
    }

    @Test
    @DisplayName("Validación específica por tipo: Preguntas abiertas garantizan opciones vacías")
    public void testPreguntaAbiertaSinOpciones() {
        builder.buildStatement("¿Cuales son los desafios de la arquitectura de microservicios?");
        builder.buildOptions(Collections.singletonList(new Option("A", "Opcion irrelevante", false)));

        Question question = builder.build();
        assertTrue(question.getOptions().isEmpty(), "Las preguntas abiertas deben mantener lista de opciones vacía");
    }

    @Test
    @DisplayName("Construcción de diferentes productos: El mismo proceso del Director permite construir diferentes tipos de preguntas")
    public void testConstruccionDiferentesProductosConMismoProceso() {
        QuestionDirector director = new QuestionDirector();

        // 1. Construir pregunta de Selección Múltiple
        MultipleChoiceQuestionBuilder mcBuilder = new MultipleChoiceQuestionBuilder();
        director.changeBuilder(mcBuilder);
        director.constructCompleteQuestion(
            "Contexto común",
            "¿Pregunta de Seleccion Multiple?",
            DifficultyLevel.MEDIA,
            "Ingenieria",
            "Autor 1",
            "Justificacion 1",
            "Fuente 1",
            Arrays.asList(new Option("A", "Resp 1", true), new Option("B", "Resp 2", false)),
            "Resp 1"
        );
        Question p1 = director.getQuestion();

        // 2. Construir pregunta de Verdadero / Falso
        TrueFalseQuestionBuilder tfBuilder = new TrueFalseQuestionBuilder();
        director.changeBuilder(tfBuilder);
        director.constructCompleteQuestion(
            "Contexto común",
            "¿Pregunta de Verdadero o Falso?",
            DifficultyLevel.BAJA,
            "Ingenieria",
            "Autor 2",
            "Justificacion 2",
            "Fuente 2",
            null,
            "Verdadero"
        );
        Question p2 = director.getQuestion();

        // 3. Construir pregunta Abierta
        OpenQuestionBuilder openBuilder = new OpenQuestionBuilder();
        director.changeBuilder(openBuilder);
        director.constructCompleteQuestion(
            "Contexto común",
            "¿Pregunta Abierta de ensayo?",
            DifficultyLevel.ALTA,
            "Ingenieria",
            "Autor 3",
            "Justificacion 3",
            "Fuente 3",
            null,
            "Criterio de calificacion"
        );
        Question p3 = director.getQuestion();

        // Verificaciones de polimorfismo y tipos de producto
        assertNotNull(p1);
        assertNotNull(p2);
        assertNotNull(p3);

        assertEquals(QuestionType.MULTIPLE_CHOICE, p1.getType());
        assertEquals(QuestionType.TRUE_FALSE, p2.getType());
        assertEquals(QuestionType.OPEN, p3.getType());

        // Verificaciones de propiedades de cada producto
        assertEquals(2, p1.getOptions().size());
        assertEquals(2, p2.getOptions().size());
        assertEquals(0, p3.getOptions().size());
    }
}
