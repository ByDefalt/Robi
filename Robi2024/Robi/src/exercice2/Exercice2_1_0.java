package exercice2;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;

/**
 * Classe principale pour l'exercice 2_1. Cette classe crée un espace graphique
 * et un rectangle, puis exécute un script pour modifier les couleurs de
 * l'espace et du rectangle.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Exercice2_1_0 {
	public GSpace space = new GSpace("Exercice 2_1", new Dimension(200, 100));
	public GRect robi = new GRect();
	String script = "(space setColor black) (robi setColor yellow)";

	/**
	 * Constructeur de la classe Exercice2_1_0. Initialise l'espace graphique, le
	 * rectangle et exécute le script.
	 */
	public Exercice2_1_0() {
		space.addElement(robi);
		space.open();
		this.runScript();
	}

	/**
	 * Méthode pour exécuter le script.
	 */
	private void runScript() {
		SParser<SNode> parser = new SParser<>();
		List<SNode> rootNodes = null;
		try {
			rootNodes = parser.parse(script);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<SNode> itor = rootNodes.iterator();
		while (itor.hasNext()) {
			this.run(itor.next());
		}
	}

	/**
	 * Méthode pour exécuter un nœud du script.
	 * 
	 * @param expr Le nœud à exécuter.
	 */
	private void run(SNode expr) {
		if (!expr.isLeaf()) {
			List<SNode> children = expr.children();
			if (children.size() >= 3) {
				String cible = children.get(0).contents();
				String propriete = children.get(1).contents();
				String valeur = children.get(2).contents();

<<<<<<< HEAD
	    private Color getColorFromString(String colorStr) {
	        switch (colorStr) {
	            case "black":
	                return Color.BLACK;
	            case "yellow":
	                return Color.YELLOW;
	            case "white":
	                return Color.WHITE;
	            case "red":
	                return Color.RED;
	            default:
	                return null;
	        }
	    }

	public static void main(String[] args) {
		new Exercice2_1_0();
=======
				switch (cible) {
				case "space":
					if (propriete.equals("setColor")) {
						space.setColor(getColorFromString(valeur));
					}
					break;
				case "robi":
					if (propriete.equals("setColor")) {
						robi.setColor(getColorFromString(valeur));
					}
					break;
				}
			}
		}
>>>>>>> 1a688b8e99892dffe5cd195b5dbd9c3d807d036c
	}

	/**
	 * Méthode pour obtenir une couleur à partir d'une chaîne de caractères.
	 * 
	 * @param colorStr La chaîne de caractères représentant la couleur.
	 * @return La couleur correspondante.
	 */
	private Color getColorFromString(String colorStr) {
		switch (colorStr) {
		case "black":
			return Color.BLACK;
		case "yellow":
			return Color.YELLOW;
		default:
			return null;
		}
	}

	/**
	 * Méthode principale, point d'entrée de l'application. Crée une instance de la
	 * classe Exercice2_1_0.
	 * 
	 * @param args Arguments de la ligne de commande (non utilisés).
	 */
	public static void main(String[] args) {
		new Exercice2_1_0(); 
	}
}
