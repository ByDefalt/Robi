package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import exercice4.Environment;
import exercice4.Interpreter;
import exercice4.Reference;
import exercice5.Exercice5;
import graphicLayer.GElement;
import graphicLayer.GImage;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;

public class Exercice5Test {
    private Exercice5 exercice;

    @Before
    public void setUp() {
        exercice = new Exercice5();
    }

    @Test
    public void testAddImageCommand() {
        GSpace space = new GSpace("Test Space", new Dimension(200, 100));
        GRect robi = new GRect();

        space.addElement(robi);
        space.open();

        Environment environment = exercice.environment;
        SParser<SNode> parser = new SParser<>();
        List<SNode> compiled = null;
        try {
            compiled = parser.parse("(space add robi (rect.class new))");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (SNode node : compiled) {
            new Interpreter().compute(environment, node);
        }

        // Ajouter la référence "im" à l'environnement
        Image image = Toolkit.getDefaultToolkit().createImage("alien.gif");
        GImage im = new GImage(image);
        environment.addReference("im", new Reference(im));

        // Vérifier si la référence "im" est ajoutée à l'environnement
        Reference imRef = environment.getReferenceByName("im");
        assertNotNull("La référence 'im' n'a pas été ajoutée à l'environnement", imRef);

        // Vérifier si le récepteur de la référence est null
        assertNotNull("Le récepteur de la référence 'im' est null", imRef.getReceiver());

        GElement imElement = (GElement) imRef.getReceiver();
        assertNotNull(imElement);
        assertEquals(GImage.class, imElement.getClass());

        // Vérifie si l'image a été récupérée
        assertNotNull(image);

        // compare image et celle passée en GImage pour voir si c'est les mêmes
        assertEquals(image, ((GImage)imElement).getRawImage());
    }
}
