import builder.MultipleChoiceQuestionBuilder;
import builder.OpenQuestionBuilder;
import builder.TrueFalseQuestionBuilder;
import director.QuestionDirector;
import domain.DifficultyLevel;
import domain.Option;
import domain.Question;
import java.util.Arrays;
import java.util.List;

/**
 * Clase Cliente (ApplicationMain) que contiene el metodo main()
 * y demuestra el funcionamiento del patron de diseno Builder
 * para la creacion de preguntas del Banco de Preguntas Saber Pro.
 */
public class ApplicationMain {

    public static void main(String[] args) {
        System.out.println("=================================================================");
        System.out.println("     SISTEMA DE GESTIÓN DE BANCO DE PREGUNTAS (SABER PRO)        ");
        System.out.println("          DEMOSTRACIÓN DEL PATRÓN CREACIONAL BUILDER             ");
        System.out.println("=================================================================\n");

        // a. Crear un objeto QuestionDirector
        QuestionDirector director = new QuestionDirector();

        // b. Crear los diferentes objetos Builders
        MultipleChoiceQuestionBuilder multipleChoiceBuilder = new MultipleChoiceQuestionBuilder();
        TrueFalseQuestionBuilder trueFalseBuilder = new TrueFalseQuestionBuilder();
        OpenQuestionBuilder openQuestionBuilder = new OpenQuestionBuilder();

        // =========================================================================
        // c. y d. Construir pregunta de Selección Múltiple usando el Director
        // =========================================================================
        System.out.println(">>> 1. CONSTRUCCIÓN DE PREGUNTA DE SELECCIÓN MÚLTIPLE:");
        director.changeBuilder(multipleChoiceBuilder);

        List<Option> opcionesMC = Arrays.asList(
            new Option("A", "Abstract Factory", false),
            new Option("B", "Builder", true),
            new Option("C", "Prototype", false),
            new Option("D", "Singleton", false)
        );

        director.constructCompleteQuestion(
            "En el desarrollo de software orientado a objetos, frecuentemente se requiere desacoplar " +
            "la construccion de un objeto complejo de su representacion particular.",
            "¿Cual de los siguientes patrones de diseno creacionales permite construir objetos complejos " +
            "paso a paso separando el proceso de construccion de su representacion final?",
            DifficultyLevel.MEDIA,
            "Ingeniería de Software / Patrones de Diseño",
            "Docente Ing. Sistemas",
            "El patron Builder permite separar la construccion de un objeto complejo de su representacion, " +
            "permitiendo que el mismo proceso pueda crear diferentes representaciones.",
            "Design Patterns: Elements of Reusable Object-Oriented Software (GoF)",
            opcionesMC,
            "Builder"
        );

        Question pregunta1 = director.getQuestion();
        System.out.println(pregunta1);

        // =========================================================================
        // c. y d. Construir pregunta de Verdadero / Falso usando el Director
        // =========================================================================
        System.out.println("\n>>> 2. CONSTRUCCIÓN DE PREGUNTA DE VERDADERO / FALSO:");
        director.changeBuilder(trueFalseBuilder);

        director.constructCompleteQuestion(
            "En la arquitectura de software que aplica el patron Builder de GoF:",
            "¿El objeto Director debe conocer los detalles internos y tecnicos especificos " +
            "de cada producto concreto que construye el Builder?",
            DifficultyLevel.BAJA,
            "Arquitectura y Diseno de Software",
            "Comite Curricular",
            "Falso. El Director solo conoce la interfaz o clase abstracta Builder; no conoce " +
            "los detalles internos de los productos concretos.",
            "Refactoring.Guru - Builder Pattern",
            null, // TrueFalseQuestionBuilder inicializa automaticamente Verdadero/Falso
            "Falso"
        );

        Question pregunta2 = director.getQuestion();
        System.out.println(pregunta2);

        // =========================================================================
        // c. y d. Construir pregunta Abierta usando el Director
        // =========================================================================
        System.out.println("\n>>> 3. CONSTRUCCIÓN DE PREGUNTA ABIERTA:");
        director.changeBuilder(openQuestionBuilder);

        director.constructCompleteQuestion(
            "En el contexto de la preparacion para las pruebas de Estado Saber Pro para Ingenieria de Sistemas:",
            "Explique la diferencia conceptual entre el patron creacional Builder y el patron Factory Method, " +
            "destacando cuando es recomendable utilizar cada uno.",
            DifficultyLevel.ALTA,
            "Ingeniería de Software",
            "Coordinacion Saber Pro",
            "Rúbrica de evaluación: El estudiante debe identificar que Factory Method crea objetos en un solo paso " +
            "mediante herencia/metodos fabrica, mientras que Builder crea productos complejos paso a paso permitiendo " +
            "diferentes configuraciones del mismo producto.",
            "Guia de Evaluacion ICFES Saber Pro - Modulo Diseno de Software",
            null,
            "Respuesta modelo: Factory Method se enfoca en que una subclase decida la clase a instanciar en un paso, " +
            "mientras que Builder se enfoca en el ensamblado paso a paso de partes complejas."
        );

        Question pregunta3 = director.getQuestion();
        System.out.println(pregunta3);

        // =========================================================================
        // Demostración de validación de reglas de negocio
        // =========================================================================
        System.out.println("\n>>> 4. DEMOSTRACIÓN DE VALIDACIÓN DE REGLAS DE NEGOCIO:");
        try {
            System.out.println("Intentando construir una pregunta de seleccion multiple sin enunciado...");
            MultipleChoiceQuestionBuilder invalidBuilder = new MultipleChoiceQuestionBuilder();
            invalidBuilder.addOption("Opcion 1", false);
            invalidBuilder.addOption("Opcion 2", true);
            invalidBuilder.buildCorrectAnswer("Opcion 2");
            // No se llama a buildStatement
            invalidBuilder.build();
        } catch (IllegalStateException e) {
            System.out.println("-> REGLA ACTIVADA CON EXITO: " + e.getMessage());
        }

        try {
            System.out.println("\nIntentando construir pregunta de seleccion multiple con 'Todas las anteriores' (RF-12)...");
            MultipleChoiceQuestionBuilder invalidBuilder2 = new MultipleChoiceQuestionBuilder();
            invalidBuilder2.buildStatement("Pregunta de prueba");
            invalidBuilder2.addOption("Opcion A", false);
            invalidBuilder2.addOption("Todas las anteriores", true);
            invalidBuilder2.build();
        } catch (IllegalStateException e) {
            System.out.println("-> REGLA RF-12 ACTIVADA CON EXITO: " + e.getMessage());
        }

        try {
            System.out.println("\nIntentando construir pregunta V/F con respuesta invalida...");
            TrueFalseQuestionBuilder invalidTF = new TrueFalseQuestionBuilder();
            invalidTF.buildStatement("La tierra es plana.");
            invalidTF.buildCorrectAnswer("Tal vez");
            invalidTF.build();
        } catch (IllegalStateException e) {
            System.out.println("-> REGLA V/F ACTIVADA CON EXITO: " + e.getMessage());
        }

        System.out.println("\n=================================================================");
        System.out.println("         FIN DE LA DEMOSTRACIÓN - EJECUCIÓN EXITOSA             ");
        System.out.println("=================================================================");
    }
}
