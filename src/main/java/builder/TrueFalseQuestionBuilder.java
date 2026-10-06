package builder;

import domain.Option;
import domain.Question;
import domain.QuestionType;
import java.util.List;

/**
 * Builder concreto para preguntas de tipo Verdadero / Falso.
 * Aplica las siguientes reglas de negocio:
 * - Enunciado obligatorio.
 * - Opciones fijas automaticas: "Verdadero" y "Falso".
 * - La respuesta correcta debe ser obligatoriamente "Verdadero" o "Falso".
 */
public class TrueFalseQuestionBuilder extends QuestionBuilder {

    public TrueFalseQuestionBuilder() {
        reset();
    }

    @Override
    public void reset() {
        this.question = new Question();
        this.question.setType(QuestionType.TRUE_FALSE);
        // Opciones predeterminadas para Verdadero / Falso
        this.question.clearOptions();
        this.question.addOption(new Option("A", "Verdadero", false));
        this.question.addOption(new Option("B", "Falso", false));
    }

    @Override
    public void buildOptions(List<Option> options) {
        // En preguntas de tipo verdadero/falso, las opciones son fijas (Verdadero y Falso).
        // Si se proveen opciones externas, solo se actualizan si cumplen con el esquema binario.
        if (options != null && options.size() == 2) {
            this.question.clearOptions();
            for (Option opt : options) {
                this.question.addOption(opt);
                if (opt.isCorrect()) {
                    this.question.setCorrectAnswer(opt.getText());
                }
            }
        }
    }

    @Override
    public void buildCorrectAnswer(String correctAnswer) {
        if (correctAnswer != null) {
            String normalizado = normalizarRespuesta(correctAnswer);
            this.question.setCorrectAnswer(normalizado);
            sincronizarOpcionCorrecta(normalizado);
        }
    }

    private String normalizarRespuesta(String respuesta) {
        String val = respuesta.trim();
        if (val.equalsIgnoreCase("verdadero") || val.equalsIgnoreCase("v") || val.equalsIgnoreCase("true") || val.equalsIgnoreCase("t")) {
            return "Verdadero";
        }
        if (val.equalsIgnoreCase("falso") || val.equalsIgnoreCase("f") || val.equalsIgnoreCase("false")) {
            return "Falso";
        }
        return val;
    }

    private void sincronizarOpcionCorrecta(String normalizado) {
        for (Option opt : this.question.getOptions()) {
            if (opt.getText().equalsIgnoreCase(normalizado)) {
                opt.setCorrect(true);
            } else {
                opt.setCorrect(false);
            }
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

        // 2. Respuesta correcta obligatoria
        String correctAnswer = question.getCorrectAnswer();
        if (correctAnswer == null || correctAnswer.trim().isEmpty()) {
            Option opt = question.getOptions().stream().filter(Option::isCorrect).findFirst().orElse(null);
            if (opt != null) {
                correctAnswer = opt.getText();
                question.setCorrectAnswer(correctAnswer);
            } else {
                throw new IllegalStateException("Debe especificarse si la respuesta correcta es 'Verdadero' o 'Falso'.");
            }
        }

        // 3. La respuesta debe ser exactamente Verdadero o Falso
        if (!correctAnswer.equalsIgnoreCase("Verdadero") && !correctAnswer.equalsIgnoreCase("Falso")) {
            throw new IllegalStateException("La respuesta correcta de una pregunta de verdadero/falso debe ser 'Verdadero' o 'Falso'.");
        }

        sincronizarOpcionCorrecta(correctAnswer);
    }
}
