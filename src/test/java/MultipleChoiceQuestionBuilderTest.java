import builder.MultipleChoiceQuestionBuilder;
import director.QuestionDirector;
import domain.DifficultyLevel;
import domain.Option;
import domain.Question;
import domain.QuestionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para MultipleChoiceQuestionBuilder.
 * Verifica la construccion correcta, validacion de campos obligatorios,
 * validacion especifica de opciones, respuestas validas y reglas de negocio
 * Saber Pro.
 */
public class MultipleChoiceQuestionBuilderTest {

    private MultipleChoiceQuestionBuilder builder;

    @BeforeEach
    public void setUp() {
        builder = new MultipleChoiceQuestionBuilder();
    }

    @Test
    @DisplayName("Construccion correcta: Debe ser posible construir una pregunta valida")
    public void testConstruccionCorrectaPreguntaValida() {
        builder.buildStatement("Cual es el patron que separa la construccion de un objeto de su representacion?");
        builder.buildDifficulty(DifficultyLevel.MEDIA);
        builder.buildCategory("Patrones Creacionales");
        builder.buildAuthor("Docente LISW2");
        builder.buildJustification("El patron Builder permite construir objetos complejos paso a paso.");
        builder.buildSource("Libro GoF");

        builder.addOption("Factory Method", false);
        builder.addOption("Builder", true);
        builder.addOption("Prototype", false);
        builder.addOption("Singleton", false);

        Question question = builder.build();

        assertNotNull(question);
        assertEquals(QuestionType.MULTIPLE_CHOICE, question.getType());
        assertEquals("Cual es el patron que separa la construccion de un objeto de su representacion?",
                question.getStatement());
        assertEquals(DifficultyLevel.MEDIA, question.getDifficulty());
        assertEquals(4, question.getOptions().size());
        assertEquals("Builder", question.getCorrectAnswer());
        assertTrue(question.getOptions().stream().anyMatch(o -> o.getText().equals("Builder") && o.isCorrect()));
    }

    @Test
    @DisplayName("Validacion de campos obligatorios: No debe ser posible construir una pregunta sin enunciado")
    public void testValidacionCamposObligatoriosSinEnunciado() {
        builder.addOption("Opcion 1", true);
        builder.addOption("Opcion 2", false);
        builder.buildCorrectAnswer("Opcion 1");

        // statement no establecido (null o vacio)
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> builder.build());
        assertTrue(ex.getMessage().contains("El enunciado de la pregunta es obligatorio"));
    }

    @Test
    @DisplayName("Validacion de campos obligatorios: Enunciado en blanco no es valido")
    public void testValidacionEnunciadoEnBlanco() {
        builder.buildStatement("    ");
        builder.addOption("Opcion 1", true);
        builder.addOption("Opcion 2", false);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> builder.build());
        assertTrue(ex.getMessage().contains("El enunciado de la pregunta es obligatorio"));
    }

    @Test
    @DisplayName("Validacion especifica por tipo: Pregunta de seleccion multiple debe tener opciones")
    public void testValidacionEspecificaPorTipoSinOpciones() {
        builder.buildStatement("Cual es la respuesta?");
        // No se agregan opciones

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> builder.build());
        assertTrue(ex.getMessage().contains("debe tener opciones"));
    }

    @Test
    @DisplayName("Validacion especifica por tipo: Debe tener al menos dos opciones")
    public void testValidacionMenosDeDosOpciones() {
        builder.buildStatement("Cual es la capital?");
        builder.addOption("Bogota", true);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> builder.build());
        assertTrue(ex.getMessage().contains("al menos 2 opciones"));
    }

    @Test
    @DisplayName("Respuestas validas: Una respuesta correcta debe corresponder a una opcion existente")
    public void testValidacionRespuestaCorrectaNoExistente() {
        builder.buildStatement("Cual es el lenguaje principal?");
        builder.addOption("Python", false);
        builder.addOption("Java", false);
        builder.buildCorrectAnswer("C++"); // No esta entre las opciones

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> builder.build());
        assertTrue(ex.getMessage().contains("debe corresponder a una opcion existente"));
    }

    @Test
    @DisplayName("Regla RF-12: Impedir expresiones prohibidas como 'Todas las anteriores'")
    public void testValidacionExpresionProhibidaTodasLasAnteriores() {
        builder.buildStatement("Seleccione las ventajas del patron Builder:");
        builder.addOption("Mayor control en la construccion", false);
        builder.addOption("Todas las anteriores", true);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> builder.build());
        assertTrue(ex.getMessage().contains("Todas las anteriores"));
    }

    @Test
    @DisplayName("Regla RF-12: Impedir expresiones prohibidas como 'Ninguna de las anteriores'")
    public void testValidacionExpresionProhibidaNingunaDeLasAnteriores() {
        builder.buildStatement("Seleccione la opcion valida:");
        builder.addOption("Opcion 1", true);
        builder.addOption("Ninguna de las anteriores", false);

        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> builder.build());
        assertTrue(ex.getMessage().contains("Ninguna de las anteriores"));
    }

    @Test
    @DisplayName("Construccion orquestada mediante QuestionDirector")
    public void testConstruccionConQuestionDirector() {
        QuestionDirector director = new QuestionDirector(builder);

        List<Option> opciones = Arrays.asList(
                new Option("A", "Opcion A", true),
                new Option("B", "Opcion B", false));

        director.constructCompleteQuestion(
                "Contexto de prueba",
                "Pregunta orquestada?",
                DifficultyLevel.ALTA,
                "Ingenieria",
                "Profesor",
                "Justificacion valida",
                "Fuente valida",
                opciones,
                "Opcion A");

        Question result = director.getQuestion();
        assertNotNull(result);
        assertEquals("Pregunta orquestada?", result.getStatement());
        assertEquals("Opcion A", result.getCorrectAnswer());
    }
}
