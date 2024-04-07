package exercice5;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import exercice4.DelElement;
import exercice4.Environment;
import exercice4.Interpreter;
import exercice4.NewElement;
import exercice4.NewImage;
import exercice4.NewString;
import exercice4.Reference;
import exercice4.SetColor;
import exercice4.SetDim;
import exercice4.SleepCommand;
import exercice5.examples.Example1;
import graphicLayer.GImage;
import graphicLayer.GOval;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import graphicLayer.GString;
import stree.parser.SNode;
import stree.parser.SParser;

public class Exercice5 {
	Environment environment = new Environment();
	String script = "(space setDim 150 120)";
    public Exercice5() {
    	GSpace space = new GSpace("Exercice 5", new Dimension(800, 500));
		space.open();

		Reference spaceRef = new Reference(space);
		Reference rectClassRef = new Reference(GRect.class);
		Reference ovalClassRef = new Reference(GOval.class);
		Reference imageClassRef = new Reference(GImage.class);
		Reference stringClassRef = new Reference(GString.class);

		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("sleep", new SleepCommand());

		spaceRef.addCommand("add", new AddElement(environment));
		spaceRef.addCommand("del", new DelElement(environment));
		
		spaceRef.addCommand("setDim", new SetDim());

		rectClassRef.addCommand("new", new NewElement());
		ovalClassRef.addCommand("new", new NewElement());
		imageClassRef.addCommand("new", new NewImage());
		stringClassRef.addCommand("new", new NewString());

		environment.addReference("space", spaceRef);
		environment.addReference("rect.class", rectClassRef);
		environment.addReference("oval.class", ovalClassRef);
		environment.addReference("image.class", imageClassRef);
		environment.addReference("label.class", stringClassRef);
    }

    public void oneShot(String script) {
        SParser<SNode> parser = new SParser<>();
        List<SNode> compiled;
        try {
            compiled = parser.parse(script);
            Iterator<SNode> itor = compiled.iterator();
            while (itor.hasNext()) {
                new Interpreter().compute(environment, itor.next());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Exercice5 exo = new Exercice5();
        exo.oneShot(exo.script);
    }
}
