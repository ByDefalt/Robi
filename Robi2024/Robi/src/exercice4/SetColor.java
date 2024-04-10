package exercice4;

import java.awt.Color;
import graphicLayer.GElement;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;

/**
 * Cette classe représente la commande pour définir la couleur d'un élément
 * graphique. Elle implémente l'interface Command.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class SetColor implements Command {

	/**
	 * Méthode pour obtenir un objet Color à partir d'une chaîne de caractères
	 * représentant une couleur.
	 * 
	 * @param colorStr La chaîne de caractères représentant la couleur.
	 * @return L'objet Color correspondant.
	 */
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

	/**
	 * Cette méthode exécute la commande pour définir la couleur d'un élément
	 * graphique. Elle prend en paramètres une référence vers l'élément graphique
	 * concerné et un nœud représentant la commande.
	 * 
	 * @param receiver La référence vers l'élément graphique.
	 * @param method   Le nœud représentant la commande.
	 * @return La référence vers l'élément graphique après exécution de la commande.
	 */
	@Override
	public Reference run(Reference receiver, SNode method) {
		Object objet = receiver.getReceiver();
		Color newColor = getColorFromString(method.get(2).contents());

		// Selon le type de l'objet, on détermine comment définir la couleur
		if (objet instanceof GSpace) {
			((GSpace) objet).setColor(newColor);
		} else if (objet instanceof GRect) {
			((GRect) objet).setColor(newColor);
		} else if (objet instanceof GElement) {
			((GElement) objet).setColor(newColor);
		}
		return receiver;
	}
}
