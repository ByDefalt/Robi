package exercice4;

import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;
import tools.Tools;

/**
 * Classe principale pour l'exercice 4_1_0. Cette classe permet de créer un
 * espace graphique et un rectangle, puis d'exécuter un script en boucle à
 * partir de l'entrée utilisateur.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Exercice4_1_0 {
	// Une seule variable d'instance
	Environment environment = new Environment();

	/**
	 * Constructeur de la classe Exercice4_1_0.
	 */
	public Exercice4_1_0() {
		GSpace space = new GSpace("Exercice 4", new Dimension(200, 100));
		GRect robi = new GRect();

		space.addElement(robi);
		space.open();

		Reference spaceRef = new Reference(space);
		Reference robiRef = new Reference(robi);

		spaceRef.addCommand("setColor", new SetColor());
		spaceRef.addCommand("sleep", new SleepCommand());

		robiRef.addCommand("setColor", new SetColor());
		robiRef.addCommand("translate", new Translate());

		environment.addReference("space", spaceRef);
		environment.addReference("robi", robiRef);

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
				this.run((SNode) itor.next());
			}
		}
	}

	/**
	 * Méthode pour exécuter une s-expression.
	 * 
	 * @param expr La s-expression à exécuter.
	 */
	private void run(SNode expr) {
		String receiverName = expr.get(0).contents();
		Reference receiver = environment.getReferenceByName(receiverName);
		receiver.run(expr);
	}

	/**
	 * Méthode principale, point d'entrée de l'application.
	 * 
	 * @param args Arguments de la ligne de commande (non utilisés).
	 */
	public static void main(String[] args) {
		new Exercice4_1_0();
	}
}
