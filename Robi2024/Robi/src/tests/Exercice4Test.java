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
import graphicLayer.GElement;
import graphicLayer.GImage;
import graphicLayer.GOval;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import graphicLayer.GString;
import stree.parser.SNode;
import stree.parser.SParser;

public class Exercice4Test {
    private Exercice4_2_0 exercice;


    @Test
    public void testEnvironmentInitialization() {
    	this.exercice = new Exercice4_2_0("");
        assertNotNull(this.exercice.getEnvironment());
    }

    @Test
    public void testSpaceReference() {
    	this.exercice = new Exercice4_2_0("");
        assertNotNull(this.exercice.getEnvironment().getReferenceByName("space"));
    }

    @Test
    public void testSetColorCommand2() {
    	this.exercice = new Exercice4_2_0("(space setColor black)");
        assertEquals(Color.BLACK, this.exercice.getSpace().getBackgroundColor());
    }
    
    @Test
    public void testAddElementCommand() {
    	this.exercice = new Exercice4_2_0("(space add robi (Rect new))");
        assertNotNull(this.exercice.getEnvironment().getReferenceByName("robi"));
    }

    @Test
    public void testSetColorRobiCommand() {
    	this.exercice = new Exercice4_2_0("(space add robi (Rect new))\n(robi setColor red)");
    	GRect robi = null;
    	for(GElement element : this.exercice.getSpace().contents()) {
    		if(element instanceof GRect) {
    			robi = (GRect) element;
    		}
    	}
        assertEquals(Color.RED, robi.getColor());
    }
    
    @Test
    public void testTranslateCommand() {
    	this.exercice = new Exercice4_2_0("(space add robi (Rect new))\n(robi translate 100 50)");
    	GRect robi = null;
    	for(GElement element : this.exercice.getSpace().contents()) {
    		if(element instanceof GRect) {
    			robi = (GRect) element;
    		}
    	}
        assertEquals(new Point(100, 50), robi.getPosition());

    }
    
    @Test
    public void testAllCommand() {
        this.exercice = new Exercice4_2_0("(space add robi (Rect new))\n"
                    + "(robi translate 130 50)\n"
                    + "(robi setColor yellow)\n"
                    + "(space add momo (Oval new))\n"
                    + "(momo setColor red)\n"
                    + "(momo translate 80 80)\n"
                    + "(space add pif (Image new alien.gif))\n"
                    + "(pif translate 100 0)\n"
                    + "(space add hello (Label new Hello world))\n"
                    + "(hello translate 10 10)\n"
                    + "(hello setColor black)");

        GRect robi = (GRect) this.exercice.getEnvironment().getReferenceByName("robi").getReceiver();
        assertNotNull(robi);
        assertEquals(new Point(130, 50), robi.getPosition());
        assertEquals(Color.YELLOW, robi.getColor());

        GOval momo = (GOval) this.exercice.getEnvironment().getReferenceByName("momo").getReceiver();
        assertNotNull(momo);
        assertEquals(new Point(80, 80), momo.getPosition());
        assertEquals(Color.RED, momo.getColor());

        GImage pif = (GImage) this.exercice.getEnvironment().getReferenceByName("pif").getReceiver();
        assertNotNull(pif);
        assertEquals(new Point(100, 0), pif.getPosition());

        GString hello = (GString) this.exercice.getEnvironment().getReferenceByName("hello").getReceiver();
        assertNotNull(hello);
        assertEquals(new Point(10, 10), hello.getPosition());
        assertEquals(Color.BLACK, hello.getColor());
    }

}
