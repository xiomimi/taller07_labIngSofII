package builder;

import domain.Option;
import domain.Question;
import domain.QuestionType;
import java.util.List;

/**
 * Builder concreto para preguntas de tipo Abierta.
 * Aplica las siguientes reglas de negocio:
 * - Enunciado obligatorio.
 * - No requiere opciones de selección predefinidas (lista de opciones vacía).
 * - Puede incorporar una respuesta esperada o rúbrica de calificación.
 */
public class OpenQuestionBuilder extends QuestionBuilder {

    public OpenQuestionBuilder() {
        reset();
    }

    @Override
    public void reset() {
        this.question = new Question();
        this.question.setType(QuestionType.OPEN);
        this.question.clearOptions();
    }

    @Override
    public void buildOptions(List<Option> options) {
        // Las preguntas abiertas no tienen opciones de selección fija
        this.question.clearOptions();
    }

    @Override
    public void buildCorrectAnswer(String correctAnswer) {
        if (correctAnswer != null) {
            this.question.setCorrectAnswer(correctAnswer.trim());
        }
    }

    @Override
    public Question build() {
        validarReglasDeNegocio();
        return this.question;
    }

    private void validarReglasDeNegocio() {
        // 1. Enunciado obligatorio
        if (question.getStatement() == null || question.getStatement().trim().isEmpty()) {
            throw new IllegalStateException("El enunciado de la pregunta es obligatorio.");
        }

        // 2. Asegurar que no contenga opciones de selección
        this.question.clearOptions();
    }
}
