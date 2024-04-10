package exercice4;

import java.awt.Dimension;

import graphicLayer.GBounded;
import graphicLayer.GSpace;
import stree.parser.SNode;

/**
 * Cette classe représente la commande pour définir la dimension d'un élément
 * graphique. Elle implémente l'interface Command.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class SetDim implements Command {

	/**
	 * Cette méthode exécute la commande pour définir la dimension d'un élément
	 * graphique. Elle prend en paramètres une référence vers l'élément graphique
	 * concerné et un nœud représentant la commande.
	 * 
	 * @param receiver La référence vers l'élément graphique.
	 * @param expr     Le nœud représentant la commande.
	 * @return La référence vers l'élément graphique après exécution de la commande.
	 */
	@Override
	public Reference run(Reference receiver, SNode expr) {
		Object objet = receiver.getReceiver();
		int x, y;
		x = Integer.parseInt(expr.get(2).contents());
		y = Integer.parseInt(expr.get(3).contents());
		Dimension dim = new Dimension(x, y);
		if (objet instanceof GSpace) {
			((GSpace) objet).changeWindowSize(dim);
		} else {
			((GBounded) objet).setDimension(dim);
		}
		receiver.setReceiver(objet);
		return receiver;
	}

}
