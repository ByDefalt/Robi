package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import exercice4.Environment;
import exercice4.Exercice4_1_0;
import exercice4.Exercice4_2_0;
import exercice4.Reference;


public class Exercice4Test {

    @Test
    public void testEnvironmentInitialization() {
        Exercice4_1_0 exercice = new Exercice4_1_0();
        Environment environment = exercice.environment;

        assertNotNull(environment); // si l'environment est initialisé
    }

    @Test
    public void testSpaceReferenceAddedToEnvironment() {
        Exercice4_1_0 exercice = new Exercice4_1_0();
        Environment environment = exercice.environment;
        Reference spaceReference = environment.getReferenceByName("space");

        assertNotNull(spaceReference); // Check if the space reference is added to the environment
    }

    @Test
    public void testRobiReferenceAddedToEnvironment() {
        Exercice4_1_0 exercice = new Exercice4_1_0();
        Environment environment = exercice.environment;
        Reference robiReference = environment.getReferenceByName("robi");

        assertNotNull(robiReference); // Check if the robi reference is added to the environment
    }

    @Test
    public void testSpaceAndRobiReferencesHaveCommands() {
        Exercice4_1_0 exercice = new Exercice4_1_0();
        Environment environment = exercice.environment;
        Reference spaceReference = environment.getReferenceByName("space");
        Reference robiReference = environment.getReferenceByName("robi");

        assertTrue(spaceReference.hasCommand("setColor")); // Check if space reference has setColor command
        assertTrue(spaceReference.hasCommand("sleep")); // Check if space reference has sleep command
        assertTrue(robiReference.hasCommand("setColor")); // Check if robi reference has setColor command
        assertTrue(robiReference.hasCommand("translate")); // Check if robi reference has translate command
    }

    @Test
    public void testMainLoopRuns() {
        // test de l'execution du programme et vérifie qu'il n'y a pas d'exceptions
        new Exercice4_1_0();
    }



//exercice 4_2
    
    @Test
    public void testEnvironmentInitialization2() {
        Exercice4_2_0 exercice = new Exercice4_2_0();
        Environment environment = exercice.environment;

        assertNotNull(environment); // Check if the environment is initialized
    }

    @Test
    public void testSpaceReferenceAddedToEnvironment2() {
        Exercice4_2_0 exercice = new Exercice4_2_0();
        Environment environment = exercice.environment;
        Reference spaceReference = environment.getReferenceByName("space");

        assertNotNull(spaceReference); // Check if the space reference is added to the environment
    }

    @Test
    public void testCommandsAddedToSpaceReference() {
        Exercice4_2_0 exercice = new Exercice4_2_0();
        Environment environment = exercice.environment;
        Reference spaceReference = environment.getReferenceByName("space");

        assertTrue(spaceReference.hasCommand("setColor")); // Check if space reference has setColor command
        assertTrue(spaceReference.hasCommand("setDim")); // Check if space reference has setDim command
        assertTrue(spaceReference.hasCommand("sleep")); // Check if space reference has sleep command
        assertTrue(spaceReference.hasCommand("add")); // Check if space reference has add command
        assertTrue(spaceReference.hasCommand("del")); // Check if space reference has del command
    }

    @Test
    public void testRectClassReferenceAddedToEnvironment() {
        Exercice4_2_0 exercice = new Exercice4_2_0();
        Environment environment = exercice.environment;
        Reference rectClassReference = environment.getReferenceByName("rect.class");

        assertNotNull(rectClassReference); // Check if the rect.class reference is added to the environment
    }

    @Test
    public void testOvalClassReferenceAddedToEnvironment() {
        Exercice4_2_0 exercice = new Exercice4_2_0();
        Environment environment = exercice.environment;
        Reference ovalClassReference = environment.getReferenceByName("oval.class");

        assertNotNull(ovalClassReference); // Check if the oval.class reference is added to the environment
    }

    @Test
    public void testImageClassReferenceAddedToEnvironment() {
        Exercice4_2_0 exercice = new Exercice4_2_0();
        Environment environment = exercice.environment;
        Reference imageClassReference = environment.getReferenceByName("image.class");

        assertNotNull(imageClassReference); // Check if the image.class reference is added to the environment
    }

    @Test
    public void testLabelClassReferenceAddedToEnvironment() {
        Exercice4_2_0 exercice = new Exercice4_2_0();
        Environment environment = exercice.environment;
        Reference labelClassReference = environment.getReferenceByName("label.class");

        assertNotNull(labelClassReference); // Check if the label.class reference is added to the environment
    }

    @Test
    public void testMainLoopRuns2() {
        // test de l'execution du programme et vérifie qu'il n'y a pas d'exceptions
        new Exercice4_2_0();
    }
}
