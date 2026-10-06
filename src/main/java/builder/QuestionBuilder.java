package builder;

import domain.DifficultyLevel;
import domain.Option;
import domain.Question;
import java.util.List;

/**
 * Clase abstracta Builder que define las operaciones necesarias para
 * construir progresivamente una pregunta en el Banco de Preguntas.
 */
public abstract class QuestionBuilder {
    protected Question question;

    /**
     * Reinicia el builder creando una nueva instancia del producto
     * y configurando los valores iniciales segun el tipo de pregunta.
     */
    public abstract void reset();

    /**
     * Retorna la pregunta en construccion o ya construida.
     * @return instancia de Question
     */
    public Question getQuestion() {
        return this.question;
    }

    /**
     * Construye el enunciado de la pregunta.
     * @param statement enunciado o pregunta directa
     */
    public void buildStatement(String statement) {
        if (this.question != null) {
            this.question.setStatement(statement);
        }
    }

    /**
     * Asigna el nivel de dificultad de la pregunta.
     * @param difficulty nivel de dificultad
     */
    public void buildDifficulty(DifficultyLevel difficulty) {
        if (this.question != null) {
            this.question.setDifficulty(difficulty);
        }
    }

    /**
     * Asigna la competencia o categoria a la que pertenece la pregunta.
     * @param category competencia o categoria
     */
    public void buildCategory(String category) {
        if (this.question != null) {
            this.question.setCategory(category);
        }
    }

    /**
     * Asigna el autor de la pregunta.
     * @param author autor
     */
    public void buildAuthor(String author) {
        if (this.question != null) {
            this.question.setAuthor(author);
        }
    }

    /**
     * Asigna la justificacion pedagogica o tecnica de la respuesta.
     * @param justification justificacion
     */
    public void buildJustification(String justification) {
        if (this.question != null) {
            this.question.setJustification(justification);
        }
    }

    /**
     * Asigna la fuente bibliografica o referencia de la pregunta.
     * @param source fuente o bibliografia
     */
    public void buildSource(String source) {
        if (this.question != null) {
            this.question.setSource(source);
        }
    }

    /**
     * Asigna el contexto de la pregunta (Diseno Centrado en Evidencia).
     * @param context contexto situacional
     */
    public void buildContext(String context) {
        if (this.question != null) {
            this.question.setContext(context);
        }
    }

    /**
     * Asigna el tema de la pregunta.
     * @param topic tema
     */
    public void buildTopic(String topic) {
        if (this.question != null) {
            this.question.setTopic(topic);
        }
    }

    /**
     * Asigna el subtema de la pregunta.
     * @param subtopic subtema
     */
    public void buildSubtopic(String subtopic) {
        if (this.question != null) {
            this.question.setSubtopic(subtopic);
        }
    }

    /**
     * Asigna la lista de opciones de respuesta.
     * @param options opciones
     */
    public abstract void buildOptions(List<Option> options);

    /**
     * Asigna la respuesta correcta o clave esperada.
     * @param correctAnswer respuesta correcta
     */
    public abstract void buildCorrectAnswer(String correctAnswer);

    /**
     * Construye y retorna el producto final, aplicando todas las reglas de negocio
     * y validaciones requeridas para garantizar la validez de la pregunta.
     * @return Question construida y validada
     */
    public abstract Question build();
}