package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;

import org.junit.Test;

import exercice3.Exercice3_0;
import graphicLayer.GRect;
import graphicLayer.GSpace;

public class Exercice3Test {

    @Test
    public void testExercice3_0() {
        Exercice3_0 exo3_0 = new Exercice3_0();
        GSpace space = exo3_0.space;
        GRect robi = exo3_0.robi;
        assertNotNull(space);
        assertNotNull(robi);
        assertEquals(Color.WHITE, space.getBackgroundColor());
        assertEquals(Color.RED, robi.getColor());
        // Vérification de la translation de robi
        assertEquals(new java.awt.Point(0, 10), robi.recupPosition());
    }
}
