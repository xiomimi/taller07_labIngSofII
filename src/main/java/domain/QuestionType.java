package domain;

/**
 * Enumeracion que representa los tipos de preguntas soportados
 * en el Banco de Preguntas.
 */
public enum QuestionType {
    MULTIPLE_CHOICE("Seleccion Multiple"),
    TRUE_FALSE("Verdadero / Falso"),
    OPEN("Pregunta Abierta");

    private final String displayName;

    QuestionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
