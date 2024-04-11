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
<<<<<<< HEAD
	// Une seule variable d'instance
	public Environment environment = new Environment();
=======

	Environment environment = new Environment();
>>>>>>> 1a688b8e99892dffe5cd195b5dbd9c3d807d036c

	/**
	 * Constructeur de la classe Exercice4_2_0.
	 */
	public Exercice4_2_0() {
		GSpace space = new GSpace("Exercice 4", new Dimension(200, 100));
		space.open();

		Reference spaceRef = new Reference(space);
		Reference rectClassRef = new Reference(GRect.class);
		Reference ovalClassRef = new Reference(GOval.class);
		Reference imageClassRef = new Reference(GImage.class);
		Reference stringClassRef = new Reference(GString.class);

		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("setDim", new SetDim());
		spaceRef.addCommand("sleep", new SleepCommand());

		spaceRef.addCommand("add", new AddElement(environment));
		spaceRef.addCommand("del", new DelElement(environment));

		rectClassRef.addCommand("new", new NewElement());
		ovalClassRef.addCommand("new", new NewElement());
		imageClassRef.addCommand("new", new NewImage());
		stringClassRef.addCommand("new", new NewString());

		environment.addReference("space", spaceRef);
		environment.addReference("rect.class", rectClassRef);
		environment.addReference("oval.class", ovalClassRef);
		environment.addReference("image.class", imageClassRef);
		environment.addReference("label.class", stringClassRef);

		this.mainLoop();
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
				new Interpreter().compute(environment, itor.next());
			}
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
