import builder.MultipleChoiceQuestionBuilder;
import builder.OpenQuestionBuilder;
import builder.TrueFalseQuestionBuilder;
import director.QuestionDirector;
import domain.DifficultyLevel;
import domain.Option;
import domain.Question;
import java.util.Arrays;
import java.util.List;

public class ApplicationMain {

    public static void main(String[] args) {
        System.out.println("Banco de Preguntas Saber Pro - Patron Builder\n");

        QuestionDirector director = new QuestionDirector();

        MultipleChoiceQuestionBuilder mcBuilder = new MultipleChoiceQuestionBuilder();
        TrueFalseQuestionBuilder tfBuilder = new TrueFalseQuestionBuilder();
        OpenQuestionBuilder openBuilder = new OpenQuestionBuilder();

        // 1. Pregunta de seleccion multiple
        System.out.println("1. Pregunta de seleccion multiple:");
        director.changeBuilder(mcBuilder);

        List<Option> opcionesMC = Arrays.asList(
                new Option("A", "Abstract Factory", false),
                new Option("B", "Builder", true),
                new Option("C", "Prototype", false),
                new Option("D", "Singleton", false));

        director.constructCompleteQuestion(
                "En el desarrollo de software orientado a objetos, se requiere desacoplar " +
                        "la construccion de un objeto complejo de su representacion particular.",
                "Cual de los siguientes patrones creacionales permite construir objetos complejos " +
                        "paso a paso separando el proceso de construccion de su representacion?",
                DifficultyLevel.MEDIA,
                "Ingenieria de Software / Patrones de Diseno",
                "Docente Ing. Sistemas",
                "El patron Builder permite separar la construccion de un objeto complejo de su representacion.",
                "Design Patterns: Elements of Reusable Object-Oriented Software (GoF)",
                opcionesMC,
                "Builder");

        Question pregunta1 = director.getQuestion();
        System.out.println(pregunta1);
        System.out.println();

        // 2. Pregunta de verdadero o falso
        System.out.println("2. Pregunta de verdadero o falso:");
        director.changeBuilder(tfBuilder);

        director.constructCompleteQuestion(
                "En la arquitectura de software aplicando el patron Builder:",
                "El objeto Director debe conocer los detalles internos de cada producto concreto que construye?",
                DifficultyLevel.BAJA,
                "Arquitectura y Diseno de Software",
                "Comite Curricular",
                "Falso. El Director solo interactua con la interfaz Builder, no conoce los detalles internos.",
                "Refactoring.Guru - Builder Pattern",
                null,
                "Falso");

        Question pregunta2 = director.getQuestion();
        System.out.println(pregunta2);
        System.out.println();

        // 3. Pregunta abierta
        System.out.println("3. Pregunta abierta:");
        director.changeBuilder(openBuilder);

        director.constructCompleteQuestion(
                "En el contexto de evaluacion para Saber Pro:",
                "Explique la diferencia principal entre el patron Builder y Factory Method.",
                DifficultyLevel.ALTA,
                "Ingenieria de Software",
                "Coordinacion Saber Pro",
                "Rubrica: Debe mencionar la construccion paso a paso frente a la creacion en un solo paso.",
                "Guia de Evaluacion ICFES Saber Pro",
                null,
                "Respuesta esperada: Factory Method crea objetos en un solo paso por subclases, Builder ensambla paso a paso.");

        Question pregunta3 = director.getQuestion();
        System.out.println(pregunta3);
        System.out.println();

        // 4. Validaciones de reglas
        System.out.println("4. Validaciones de reglas de negocio:");
        try {
            MultipleChoiceQuestionBuilder inv1 = new MultipleChoiceQuestionBuilder();
            inv1.addOption("Opcion 1", false);
            inv1.addOption("Opcion 2", true);
            inv1.buildCorrectAnswer("Opcion 2");
            inv1.build();
        } catch (IllegalStateException e) {
            System.out.println("Validacion sin enunciado: " + e.getMessage());
        }

        try {
            MultipleChoiceQuestionBuilder inv2 = new MultipleChoiceQuestionBuilder();
            inv2.buildStatement("Pregunta con opcion prohibida");
            inv2.addOption("Opcion A", false);
            inv2.addOption("Todas las anteriores", true);
            inv2.build();
        } catch (IllegalStateException e) {
            System.out.println("Validacion opcion prohibida: " + e.getMessage());
        }

        try {
            TrueFalseQuestionBuilder inv3 = new TrueFalseQuestionBuilder();
            inv3.buildStatement("Pregunta con respuesta incorrecta");
            inv3.buildCorrectAnswer("Tal vez");
            inv3.build();
        } catch (IllegalStateException e) {
            System.out.println("Validacion respuesta invalida: " + e.getMessage());
        }
    }
}
