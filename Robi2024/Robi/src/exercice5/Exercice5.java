package exercice5;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import exercice4.Environment;
import exercice4.Interpreter;
import exercice4.NewImage;
import exercice4.NewString;
import exercice4.Reference;
import exercice4.SetColor;
import exercice4.SetDim;
import exercice4.Translate;
import exercice4.SleepCommand;
import graphicLayer.GImage;
import graphicLayer.GOval;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import graphicLayer.GString;
import stree.parser.SNode;
import stree.parser.SParser;
/*
 * 
(space add robi (rect.class new))
(robi translate 130 50)
(robi setColor yellow)
(space add momo (oval.class new))
(momo setColor red)
(momo translate 80 80)
(space add pif (image.class new alien.gif))
(pif translate 100 0)
(space add hello (label.class new "Hello world"))
(hello translate 10 10)
(hello setColor black)


(space setDim 150 120)
(space add robi (rect.class new))
(space.robi setColor white)
(space.robi setDim 100 100)
(space.robi translate 20 10)
(space.robi add im (image.class new alien.gif))
(space.robi.im translate 20 20))
*/

/**
 * Classe principale pour l'exercice 5. Cette classe permet de créer un
 * espace graphique et d'interpréter une série de commandes définies dans une
 * s-expression.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Exercice5 {
	public Environment environment = new Environment();
	String script = "(space setDim 150 120)\n" +
            "(space add robi (rect.class new))\n" +
            "(space.robi setColor white)\n" +
            "(space.robi setDim 100 100)\n" +
            "(space.robi translate 20 10)\n" +
            "(space.robi add im (image.class new alien.gif))\n" +
            "(space.robi.im translate 20 20)";
	
	String script2 = "(space add robi (Rect new))\n"
			+ "(space.robi setDim 50 50)\n"
			+ "(space.robi add robi (Rect new))\n"
			+ "(space.robi.robi setColor red)\n"
			+ "(space.robi setColor white)";
	
	String script2Del = "(space add robi (rect.class new))\n"
			+ "(space.robi setDim 50 50)\n"
			+ "(space.robi add robi (rect.class new))\n"
			+ "(space.robi.robi setColor red)\n"
			+ "(space.robi setColor white)"
			+ "(space del robi)";


	/**
	 * Constructeur de la classe Exercice5.
	 */
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
		spaceRef.addCommand("translate", new Translate());
		
		rectClassRef.addCommand("new", new NewElement(environment));
		ovalClassRef.addCommand("new", new NewElement(environment));
		imageClassRef.addCommand("new", new NewImage());
		stringClassRef.addCommand("new", new NewString());

		environment.addReference("space", spaceRef);
		environment.addReference("rect.class", rectClassRef);
		environment.addReference("oval.class", ovalClassRef);
		environment.addReference("image.class", imageClassRef);
		environment.addReference("label.class", stringClassRef);
    }
    
    /**
	 * Exécute une liste de commandes en une seule fois.
	 * 
	 * @param script contient les commandes.
	 */
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
    
    /**
	 * Méthode principale, point d'entrée de l'application.
	 * 
	 * @param args Arguments de la ligne de commande (non utilisés).
	 */
    public static void main(String[] args) {
        Exercice5 exo = new Exercice5();
        exo.oneShot(exo.script);
    }
}
