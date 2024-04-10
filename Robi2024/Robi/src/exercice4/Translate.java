package exercice4;

import java.awt.Point;

import graphicLayer.GElement;
import graphicLayer.GRect;
import stree.parser.SNode;

/**
 * Cette classe représente la commande pour effectuer une translation sur un
 * élément graphique. Elle implémente l'interface Command.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Translate implements Command {

	/**
	 * Cette méthode exécute la commande pour effectuer une translation sur un
	 * élément graphique. Elle prend en paramètres une référence et un nœud
	 * représentant la commande.
	 * 
	 * @param receiver La référence vers l'objet à translater.
	 * @param method   Le nœud représentant la commande.
	 * @return La référence vers l'objet après translation.
	 */
	@Override
	public Reference run(Reference receiver, SNode method) {
		Object objet;
		int dx = Integer.parseInt(method.get(2).contents());
		int dy = Integer.parseInt(method.get(3).contents());

		objet = receiver.getReceiver();
		if (objet instanceof GRect) {
			((GRect) objet).translate(new Point(dx, dy));
		} else {
			((GElement) objet).translate(new Point(dx, dy));
		}
		return receiver;
	}
}
