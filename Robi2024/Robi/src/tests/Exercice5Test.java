package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import exercice5.Exercice5;
import stree.parser.SNode;

public class Exercice5Test {

    @Test
    public void testAddElementToContainer() {
        Exercice5 exercice = new Exercice5();

        // Ajouter des éléments internes au conteneur
        assertTrue(exercice.environment.getReferenceByName("space").getCommandByName("add").run(
                exercice.environment.getReferenceByName("space.robi"),
                createSNode("Rect")) != null);
        assertTrue(exercice.environment.getReferenceByName("space.robi").getCommandByName("add").run(
                exercice.environment.getReferenceByName("space.robi"),
                createSNode("Rect")) != null);
        assertTrue(exercice.environment.getReferenceByName("space.robi").getCommandByName("add").run(
                exercice.environment.getReferenceByName("space.robi"),
                createSNode("Image")) != null);
        assertTrue(exercice.environment.getReferenceByName("space.robi").getCommandByName("add").run(
                exercice.environment.getReferenceByName("space.robi"),
                createSNode("String")) != null);
    }

    @Test
    public void testModifyElementInContainer() {
        Exercice5 exercice = new Exercice5();

        // Ajouter un élément au conteneur
        assertTrue(exercice.environment.getReferenceByName("space").getCommandByName("add").run(
                exercice.environment.getReferenceByName("space.robi"),
                createSNode("Rect")) != null);

        // Modifier la couleur de l'élément interne
        assertTrue(exercice.environment.getReferenceByName("space.robi").getCommandByName("setColor").run(null, createSNode2("red")) != null);
    }

    @Test
    public void testRemoveContainerWithInnerElements() {
        Exercice5 exercice = new Exercice5();

        // Ajouter un élément et un élément interne au conteneur
        assertTrue(exercice.environment.getReferenceByName("space").getCommandByName("add").run(
                exercice.environment.getReferenceByName("space.robi"),
                createSNode("Rect")) != null);
        assertTrue(exercice.environment.getReferenceByName("space.robi").getCommandByName("add").run(
                exercice.environment.getReferenceByName("space.robi"),
                createSNode("Image")) != null);

        // Supprimer le conteneur
        assertTrue(exercice.environment.getReferenceByName("space").getCommandByName("del").run(null, createSNode("robi")) != null);

        // Vérifier si le conteneur et ses éléments internes sont supprimés
        assertNull(exercice.environment.getReferenceByName("space.robi"));
        assertNull(exercice.environment.getReferenceByName("space.robi.im"));
    }

    private SNode createSNode(String elementType) {
        return new MySNode(null, 0, elementType, null); // Remplacez MySNode par votre propre implémentation de SNode
    }

    // Méthode utilitaire pour créer un nœud avec une chaîne de couleur
    private SNode createSNode2(String color) {
        return new MySNode(null, 0, color, null); // Remplacez MySNode par votre propre implémentation de SNode
    }
}
