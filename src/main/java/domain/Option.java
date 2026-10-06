package domain;

import java.util.Objects;

/**
 * Representa una opción de respuesta en una pregunta del Banco de Preguntas.
 */
public class Option {
    private String id;
    private String text;
    private boolean isCorrect;

    public Option() {
    }

    public Option(String text) {
        this("", text, false);
    }

    public Option(String text, boolean isCorrect) {
        this("", text, isCorrect);
    }

    public Option(String id, String text, boolean isCorrect) {
        this.id = id != null ? id.trim() : "";
        this.text = text != null ? text.trim() : "";
        this.isCorrect = isCorrect;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id != null ? id.trim() : "";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text != null ? text.trim() : "";
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return Objects.equals(text, option.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (id != null && !id.isEmpty()) {
            sb.append("[").append(id).append("] ");
        }
        sb.append(text);
        if (isCorrect) {
            sb.append(" (Correcta)");
        }
        return sb.toString();
    }
}
