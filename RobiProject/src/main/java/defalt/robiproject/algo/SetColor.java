package defalt.robiproject.algo;

import java.awt.Color;

import defalt.robiproject.graphicLayer.GElement;
import defalt.robiproject.graphicLayer.GRect;
import defalt.robiproject.graphicLayer.GSpace;
import defalt.robiproject.parser.SNode;

/**
 * Cette classe implémente la commande "setColor" qui permet de définir la
 * couleur d'un élément graphique.
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
	 * @param colorStr la chaîne de caractères représentant la couleur
	 * @return l'objet Color correspondant, ou null si la couleur n'est pas reconnue
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
		case "blue":
			return Color.BLUE;
		case "green":
			return Color.GREEN;
		default:
			return null;
		}
	}

	/**
	 * Méthode pour exécuter la commande "setColor". Elle récupère la couleur à
	 * partir du nœud de la méthode et l'applique à l'élément graphique concerné.
	 * 
	 * @param receiver la référence de l'élément graphique à colorer
	 * @param method   le nœud de la méthode contenant les informations sur la
	 *                 couleur
	 * @return la référence de l'élément graphique modifié
	 */
	@Override
	public Reference run(Reference receiver, SNode method) {
		Color newColor = getColorFromString(method.get(2).contents());
		Object objet = receiver.getReceiver();
		if (newColor != null) {
			if (objet instanceof GSpace) {
				((GSpace) objet).setColor(newColor);
			} else if (objet instanceof GRect) {
				((GRect) objet).setColor(newColor);
			} else {
				((GElement) objet).setColor(newColor);
			}
		}
		return receiver;
	}
}
