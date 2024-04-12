package exercice4;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GImage;
import graphicLayer.GOval;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import graphicLayer.GString;
import stree.parser.SNode;
import stree.parser.SParser;
import tools.Tools;

/**
 * Classe principale pour l'exercice 4_2_0. Cette classe permet de créer un
 * espace graphique et d'interpréter une série de commandes définies dans une
 * s-expression.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Exercice4_2_0 {
	// Une seule variable d'instance
	private Environment environment;
	private GSpace space;

	/**
	 * Constructeur de la classe Exercice4_2_0.
	 */
	public Exercice4_2_0() {
		this.space = new GSpace("Exercice 4", new Dimension(200, 100));
		this.environment = new Environment();

		Reference spaceRef = new Reference(this.space);
		Reference rectClassRef = new Reference(GRect.class);
		Reference ovalClassRef = new Reference(GOval.class);
		Reference imageClassRef = new Reference(GImage.class);
		Reference stringClassRef = new Reference(GString.class);

		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("setDim", new SetDim());
		spaceRef.addCommand("sleep", new SleepCommand());

		spaceRef.addCommand("add", new AddElement(this.environment));
		spaceRef.addCommand("del", new DelElement(this.environment));

		rectClassRef.addCommand("new", new NewElement());
		ovalClassRef.addCommand("new", new NewElement());
		imageClassRef.addCommand("new", new NewImage());
		stringClassRef.addCommand("new", new NewString());

		this.environment.addReference("space", spaceRef);
		this.environment.addReference("rect.class", rectClassRef);
		this.environment.addReference("oval.class", ovalClassRef);
		this.environment.addReference("image.class", imageClassRef);
		this.environment.addReference("label.class", stringClassRef);
		this.space.open();
		
		this.mainLoop();
	}
	
	/**
	 * Constructeur utilisé par les tests Junit.
	 * 
	 * @param script
	 */
	public Exercice4_2_0(String script) {
		this.space = new GSpace("Exercice 4", new Dimension(200, 100));
		this.environment = new Environment();

		Reference spaceRef = new Reference(this.space);
		Reference rectClassRef = new Reference(GRect.class);
		Reference ovalClassRef = new Reference(GOval.class);
		Reference imageClassRef = new Reference(GImage.class);
		Reference stringClassRef = new Reference(GString.class);

		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("setDim", new SetDim());
		spaceRef.addCommand("sleep", new SleepCommand());

		spaceRef.addCommand("add", new AddElement(this.environment));
		spaceRef.addCommand("del", new DelElement(this.environment));

		rectClassRef.addCommand("new", new NewElement());
		ovalClassRef.addCommand("new", new NewElement());
		imageClassRef.addCommand("new", new NewImage());
		stringClassRef.addCommand("new", new NewString());

		this.environment.addReference("space", spaceRef);
		this.environment.addReference("rect.class", rectClassRef);
		this.environment.addReference("oval.class", ovalClassRef);
		this.environment.addReference("image.class", imageClassRef);
		this.environment.addReference("label.class", stringClassRef);
		this.space.open();
		
		this.oneShot(script);
	}
	
	public Environment getEnvironment() {
		return this.environment;
	}
	
	public GSpace getSpace() {
		return this.space;
	}

	/**
	 * Boucle principale de l'application, permettant de lire et exécuter les
	 * commandes entrées par l'utilisateur.
	 */
	private void mainLoop() {
		while (true) {

			System.out.print("> ");
			String input = Tools.readKeyboard();
			SParser<SNode> parser = new SParser<>();
			List<SNode> compiled = null;
			try {
				compiled = parser.parse(input);
			} catch (IOException e) {
				e.printStackTrace();
			}
			Iterator<SNode> itor = compiled.iterator();
			while (itor.hasNext()) {
				new Interpreter().compute(this.environment, itor.next());
			}
		}
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
                new Interpreter().compute(this.environment, itor.next());
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
		new Exercice4_2_0();
	}

}
