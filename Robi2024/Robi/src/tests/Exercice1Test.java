package tests;

import static org.junit.Assert.*;
import org.junit.Test;

import exercice1.Exercice1_0;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

public class Exercice1Test {

    @Test
    public void testSpaceCreation() {
        Exercice1_0 exo1 = new Exercice1_0();
        assertNotNull(exo1.space);
    }

    @Test
    public void testRobiCreation() {
        Exercice1_0 exo1 = new Exercice1_0();
        assertNotNull(exo1.robi);
    }

    @Test
    public void testRobiColorChange() {
        Exercice1_0 exo1 = new Exercice1_0();
        Color initialColor = exo1.robi.defaultColor();
        exo1.robi.setColor(Color.RED);
        assertNotEquals(initialColor, exo1.robi.defaultColor());
    }

    @Test
    public void testRobiMovement() {
        Exercice1_0 exo1 = new Exercice1_0();
        Point initialPosition = exo1.robi.getPosition();
        exo1.tourFenetre();
        assertNotEquals(initialPosition, exo1.robi.getPosition());
    }

    @Test
    public void testSpaceDimensions() {
        Exercice1_0 exo1 = new Exercice1_0();
        Dimension expectedDimensions = new Dimension(400, 400);
        assertEquals(expectedDimensions, exo1.space.getSize());
    }
}
