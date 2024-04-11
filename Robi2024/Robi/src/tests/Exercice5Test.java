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
            compiled = parser.parse("(space.robi add im (image.class new alien.gif))");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (SNode node : compiled) {
            new Interpreter().compute(environment, node);
        }

        GElement im = (GElement) environment.getReferenceByName("im").getReceiver();
        assertNotNull(im);
        assertEquals(GImage.class, im.getClass());

        // Load an image to pass to the constructor of GImage
        Image image = Toolkit.getDefaultToolkit().createImage("alien.gif");

        // Check if the image is loaded
        assertNotNull(image);

        // Check if the image passed to GImage is the same as the one loaded
        assertEquals(image, ((GImage)im).getRawImage());
    }
}
