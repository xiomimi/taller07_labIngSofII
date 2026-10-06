package domain;

/**
 * Enumeración que representa los niveles de dificultad de una pregunta
 * en el Banco de Preguntas para las Pruebas Saber Pro.
 */
public enum DifficultyLevel {
    BAJA("Baja"),
    MEDIA("Media"),
    ALTA("Alta");

    private final String descripcion;

    DifficultyLevel(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static DifficultyLevel fromString(String valor) {
        if (valor == null) {
            return null;
        }
        String normalizado = valor.trim().toUpperCase();
        switch (normalizado) {
            case "BAJA":
            case "BAJO":
            case "FACIL":
            case "EASY":
            case "LOW":
                return BAJA;
            case "MEDIA":
            case "MEDIO":
            case "MEDIUM":
                return MEDIA;
            case "ALTA":
            case "ALTO":
            case "DIFICIL":
            case "HARD":
            case "HIGH":
                return ALTA;
            default:
                throw new IllegalArgumentException("Nivel de dificultad no valido: " + valor);
        }
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
