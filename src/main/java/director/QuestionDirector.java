package director;

import builder.QuestionBuilder;
import domain.DifficultyLevel;
import domain.Option;
import domain.Question;
import java.util.List;

/**
 * Clase Director en el patron Builder.
 * Su responsabilidad es orquestar el proceso de construccion de una pregunta,
 * definiendo el orden de los pasos sin conocer los detalles internos de cada
 * tipo especifico de pregunta.
 */
public class QuestionDirector {
    private QuestionBuilder builder;

    public QuestionDirector() {
    }

    public QuestionDirector(QuestionBuilder builder) {
        this.builder = builder;
    }

    /**
     * Permite cambiar el builder en tiempo de ejecucion.
     * @param builder nuevo builder a utilizar
     */
    public void changeBuilder(QuestionBuilder builder) {
        this.builder = builder;
    }

    /**
     * Asigna el builder.
     * @param builder builder a utilizar
     */
    public void setBuilder(QuestionBuilder builder) {
        this.builder = builder;
    }

    public QuestionBuilder getBuilder() {
        return this.builder;
    }

    /**
     * Orquesta la construccion basica de una pregunta con metadatos estandar.
     */
    public void constructQuestion(String statement, DifficultyLevel difficulty, String category,
                                  String author, String justification, String source) {
        verificarBuilder();
        builder.reset();
        builder.buildStatement(statement);
        builder.buildDifficulty(difficulty);
        builder.buildCategory(category);
        builder.buildAuthor(author);
        builder.buildJustification(justification);
        builder.buildSource(source);
    }

    /**
     * Orquesta la construccion completa de una pregunta incluyendo contexto situacional,
     * opciones y clave de respuesta.
     */
    public void constructCompleteQuestion(String context, String statement, DifficultyLevel difficulty,
                                          String category, String author, String justification,
                                          String source, List<Option> options, String correctAnswer) {
        verificarBuilder();
        builder.reset();
        builder.buildContext(context);
        builder.buildStatement(statement);
        builder.buildDifficulty(difficulty);
        builder.buildCategory(category);
        builder.buildAuthor(author);
        builder.buildJustification(justification);
        builder.buildSource(source);
        if (options != null) {
            builder.buildOptions(options);
        }
        if (correctAnswer != null) {
            builder.buildCorrectAnswer(correctAnswer);
        }
    }

    /**
     * Obtiene el producto final construido llamando al build() del builder actual.
     * @return instancia de Question
     */
    public Question getQuestion() {
        verificarBuilder();
        return builder.build();
    }

    private void verificarBuilder() {
        if (this.builder == null) {
            throw new IllegalStateException("No se ha configurado ningun QuestionBuilder en el Director.");
        }
    }
}
