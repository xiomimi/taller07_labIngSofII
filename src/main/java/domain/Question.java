package domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa el producto principal del patron Builder:
 * Una pregunta en el Sistema de Gestion del Banco de Preguntas Saber Pro.
 */
public class Question {
    private String statement;
    private DifficultyLevel difficulty;
    private String category;
    private String author;
    private String justification;
    private String source;
    private QuestionType type;
    private final List<Option> options = new ArrayList<>();
    private String correctAnswer;

    // Atributos adicionales acordes al proyecto Banco de Preguntas
    private String context;
    private String topic;
    private String subtopic;
    private String status = "Borrador";

    public Question() {
    }

    public String getStatement() {
        return statement;
    }

    public void setStatement(String statement) {
        this.statement = statement;
    }

    public DifficultyLevel getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(DifficultyLevel difficulty) {
        this.difficulty = difficulty;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getJustification() {
        return justification;
    }

    public void setJustification(String justification) {
        this.justification = justification;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
        this.type = type;
    }

    public List<Option> getOptions() {
        return Collections.unmodifiableList(options);
    }

    public void setOptions(List<Option> newOptions) {
        this.options.clear();
        if (newOptions != null) {
            this.options.addAll(newOptions);
        }
    }

    public void addOption(Option option) {
        if (option != null) {
            this.options.add(option);
        }
    }

    public void clearOptions() {
        this.options.clear();
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubtopic() {
        return subtopic;
    }

    public void setSubtopic(String subtopic) {
        this.subtopic = subtopic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------------------\n");
        sb.append("Tipo: ").append(type != null ? type.getDisplayName() : "No especificado").append("\n");
        sb.append("Estado: ").append(status).append("\n");
        if (category != null && !category.isEmpty()) {
            sb.append("Categoria: ").append(category).append("\n");
        }
        if (topic != null && !topic.isEmpty()) {
            sb.append("Tema: ").append(topic);
            if (subtopic != null && !subtopic.isEmpty()) {
                sb.append(" | Subtema: ").append(subtopic);
            }
            sb.append("\n");
        }
        if (difficulty != null) {
            sb.append("Dificultad: ").append(difficulty.getDescripcion()).append("\n");
        }
        if (author != null && !author.isEmpty()) {
            sb.append("Autor: ").append(author).append("\n");
        }
        if (context != null && !context.isEmpty()) {
            sb.append("Contexto: ").append(context).append("\n");
        }
        sb.append("Enunciado: ").append(statement).append("\n");

        if (!options.isEmpty()) {
            sb.append("Opciones:\n");
            for (int i = 0; i < options.size(); i++) {
                Option opt = options.get(i);
                char letter = (char) ('A' + i);
                String prefix = opt.getId() != null && !opt.getId().isEmpty() ? opt.getId() : String.valueOf(letter);
                sb.append("  ").append(prefix).append(") ").append(opt.getText());
                if (opt.isCorrect()) {
                    sb.append(" (Correcta)");
                }
                sb.append("\n");
            }
        }

        if (correctAnswer != null && !correctAnswer.isEmpty()) {
            sb.append("Respuesta correcta: ").append(correctAnswer).append("\n");
        }
        if (justification != null && !justification.isEmpty()) {
            sb.append("Justificacion: ").append(justification).append("\n");
        }
        if (source != null && !source.isEmpty()) {
            sb.append("Fuente: ").append(source).append("\n");
        }
        sb.append("--------------------------------------------------");
        return sb.toString();
    }
}
