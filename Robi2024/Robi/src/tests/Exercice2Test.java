package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;

import org.junit.Test;

import exercice2.Exercice2_1_0;
import exercice2.Exercice2_2_0;
import graphicLayer.GRect;
import graphicLayer.GSpace;

public class Exercice2Test {

    @Test
    public void testExercice2_1_0() {
        Exercice2_1_0 exo2_1 = new Exercice2_1_0();
        GSpace space = exo2_1.space;
        GRect robi = exo2_1.robi;
        assertNotNull(space);
        assertNotNull(robi);
        assertEquals(Color.BLACK, space.getColorModel());
        assertEquals(Color.YELLOW, robi.defaultColor());
    }

    @Test
    public void testExercice2_2_0() {
        Exercice2_2_0 exo2_2 = new Exercice2_2_0();
        GSpace space = exo2_2.space;
        GRect robi = exo2_2.robi;
        assertNotNull(space);
        assertNotNull(robi);
        assertEquals(Color.WHITE, space.getColorModel());
        assertEquals(Color.RED, robi.defaultColor());
        // Translation de robi
        assertEquals(new java.awt.Point(10, 10), robi.getPosition());
    }
}
