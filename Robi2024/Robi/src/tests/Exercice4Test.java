package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import exercice4.Environment;
import exercice4.Exercice4_1_0;
import exercice4.Exercice4_2_0;
import exercice4.Interpreter;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;

public class Exercice4Test {
    private Exercice4_1_0 exercice;
    private Exercice4_2_0 exercice2;


    @Before
    public void setUp() {
        exercice = new Exercice4_1_0();
    }

    @Test
    public void testEnvironmentInitialization() {
        Environment environment = exercice.environment;
        assertNotNull(environment);
    }

    @Test
    public void testSpaceAndRobiReferences() {
        Environment environment = exercice.environment;
        assertNotNull(environment.getReferenceByName("space"));
        assertNotNull(environment.getReferenceByName("robi"));
    }

    @Test
    public void testSetColorCommand() {
        GSpace space = new GSpace("Test Space", new Dimension(200, 100));
        GRect robi = new GRect();

        space.addElement(robi);
        space.open();

        SParser<SNode> parser = new SParser<>();
        List<SNode> compiled = null;
		try {
			compiled = parser.parse("(space setColor black)");
		} catch (IOException e) {
			e.printStackTrace();
		}

        for (SNode node : compiled) {
            exercice.run(node);
        }

        assertEquals(Color.BLACK, space.getBackgroundColor());
    }






    @Before
    public void setUp2() {
        exercice2 = new Exercice4_2_0();
    }

    @Test
    public void testEnvironmentInitialization2() {
        Environment environment = exercice.environment;
        assertNotNull(environment);
    }

    @Test
    public void testSpaceReference() {
        Environment environment = exercice.environment;
        assertNotNull(environment.getReferenceByName("space"));
    }

    @Test
    public void testSetColorCommand2() {
        GSpace space = new GSpace("Test Space", new Dimension(200, 100));

        Environment environment = exercice.environment;
        SParser<SNode> parser = new SParser<>();
        List<SNode> compiled = null;
		try {
			compiled = parser.parse("(space setColor black)");
		} catch (IOException e) {
			e.printStackTrace();
		}

        for (SNode node : compiled) {
            new Interpreter().compute(environment, node);
        }

        assertEquals(Color.BLACK, space.getBackgroundColor());
    }
    
    @Test
    public void testAddElementCommand() {
    	
        Environment environment = exercice.environment;
        SParser<SNode> parser = new SParser<>();
        List<SNode> compiled = null;
		try {
			compiled = parser.parse("(space add robi (GRect new))");
		} catch (IOException e) {
			e.printStackTrace();
		}

        for (SNode node : compiled) {
            new Interpreter().compute(environment, node);
        }

        assertNotNull(environment.getReferenceByName("robi"));
    }

    @Test
    public void testSleepCommand() {
        long startTime = System.currentTimeMillis();

        Environment environment = exercice.environment;
        SParser<SNode> parser = new SParser<>();
        List<SNode> compiled = null;
		try {
			compiled = parser.parse("(space sleep 1000)");
		} catch (IOException e) {
			e.printStackTrace();
		}

        for (SNode node : compiled) {
            new Interpreter().compute(environment, node);
        }

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        assertEquals(1000, duration, 100);
    }
    
    @Test
    public void testTranslateCommand() {
        GSpace space = new GSpace("Test Space", new Dimension(200, 100));
        GRect robi = new GRect();
        assertNotNull(robi);
        space.addElement(robi);

        Environment environment = exercice.environment;
        SParser<SNode> parser = new SParser<>();
        List<SNode> compiled = null;
		try {
			compiled = parser.parse("(robi translate 100 50)");
		} catch (IOException e) {
			e.printStackTrace();
		}

        for (SNode node : compiled) {
            new Interpreter().compute(environment, node);
        }
        assertEquals(new Point(100, 50), robi.recupPosition());

    }
}
