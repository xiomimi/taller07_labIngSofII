package builder;

import domain.Option;
import domain.Question;
import domain.QuestionType;
import java.util.List;

/**
 * Builder concreto para la construccion de preguntas de Selección Múltiple.
 * Aplica las reglas de negocio establecidas en el Banco de Preguntas Saber Pro:
 * - Enunciado obligatorio.
 * - Requiere opciones de respuesta (mínimo 2, típicamente 4).
 * - La respuesta correcta debe coincidir exactamente con una opción existente (RF-11).
 * - Impide el uso de expresiones prohibidas como "Todas las anteriores" o "Ninguna de las anteriores" (RF-12).
 */
public class MultipleChoiceQuestionBuilder extends QuestionBuilder {

    public MultipleChoiceQuestionBuilder() {
        reset();
    }

    @Override
    public void reset() {
        this.question = new Question();
        this.question.setType(QuestionType.MULTIPLE_CHOICE);
    }

    /**
     * Permite agregar una opcion de respuesta individualmente.
     * @param text texto de la opcion
     * @return this builder para encadenamiento fluido opcional
     */
    public MultipleChoiceQuestionBuilder addOption(String text) {
        return addOption(text, false);
    }

    /**
     * Permite agregar una opcion de respuesta con indicacion de si es correcta.
     * @param text texto de la opcion
     * @param isCorrect verdadero si es la respuesta correcta
     * @return this builder
     */
    public MultipleChoiceQuestionBuilder addOption(String text, boolean isCorrect) {
        if (text != null) {
            char letter = (char) ('A' + this.question.getOptions().size());
            Option opt = new Option(String.valueOf(letter), text.trim(), isCorrect);
            this.question.addOption(opt);
            if (isCorrect) {
                this.question.setCorrectAnswer(opt.getText());
            }
        }
        return this;
    }

    /**
     * Agrega un objeto Option existente.
     * @param option opcion a agregar
     * @return this builder
     */
    public MultipleChoiceQuestionBuilder addOption(Option option) {
        if (option != null) {
            this.question.addOption(option);
            if (option.isCorrect()) {
                this.question.setCorrectAnswer(option.getText());
            }
        }
        return this;
    }

    @Override
    public void buildOptions(List<Option> options) {
        this.question.clearOptions();
        if (options != null) {
            for (int i = 0; i < options.size(); i++) {
                Option opt = options.get(i);
                if (opt.getId() == null || opt.getId().isEmpty()) {
                    char letter = (char) ('A' + i);
                    opt.setId(String.valueOf(letter));
                }
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
            this.question.setCorrectAnswer(correctAnswer.trim());
            // Sincronizar con opciones existentes si ya se agregaron
            for (Option opt : this.question.getOptions()) {
                boolean matches = opt.getText().equalsIgnoreCase(correctAnswer.trim()) ||
                                  (opt.getId() != null && opt.getId().equalsIgnoreCase(correctAnswer.trim()));
                if (matches) {
                    opt.setCorrect(true);
                }
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

        // 2. Opciones obligatorias (mínimo 2)
        List<Option> options = question.getOptions();
        if (options == null || options.isEmpty()) {
            throw new IllegalStateException("Una pregunta de selección múltiple debe tener opciones.");
        }
        if (options.size() < 2) {
            throw new IllegalStateException("Una pregunta de selección múltiple debe tener al menos 2 opciones.");
        }

        // 3. Respuesta correcta obligatoria
        String correctAnswer = question.getCorrectAnswer();
        if (correctAnswer == null || correctAnswer.trim().isEmpty()) {
            // Verificar si alguna opcion vino marcada como correcta
            Option correctOpt = options.stream().filter(Option::isCorrect).findFirst().orElse(null);
            if (correctOpt != null) {
                correctAnswer = correctOpt.getText();
                question.setCorrectAnswer(correctAnswer);
            } else {
                throw new IllegalStateException("Debe especificarse una respuesta correcta para la pregunta.");
            }
        }

        // 4. La respuesta correcta debe coincidir con una de las opciones existentes
        boolean encontrada = false;
        for (Option opt : options) {
            if (opt.getText().equalsIgnoreCase(correctAnswer.trim()) ||
                (opt.getId() != null && !opt.getId().isEmpty() && opt.getId().equalsIgnoreCase(correctAnswer.trim()))) {
                opt.setCorrect(true);
                question.setCorrectAnswer(opt.getText());
                encontrada = true;
                break;
            }
        }
        if (!encontrada) {
            throw new IllegalStateException("La respuesta correcta debe corresponder a una opción existente.");
        }

        // 5. Debe existir únicamente una respuesta correcta (RF-11)
        long correctCount = options.stream().filter(Option::isCorrect).count();
        if (correctCount != 1) {
            throw new IllegalStateException("Debe existir únicamente una respuesta correcta en preguntas de selección múltiple.");
        }

        // 6. Prohibición de expresiones según RF-12
        for (Option opt : options) {
            String txt = opt.getText().toLowerCase();
            if (txt.contains("todas las anteriores") || txt.contains("ninguna de las anteriores")) {
                throw new IllegalStateException("No se permite el uso de expresiones como 'Todas las anteriores' o 'Ninguna de las anteriores' (RF-12).");
            }
        }
    }
}
