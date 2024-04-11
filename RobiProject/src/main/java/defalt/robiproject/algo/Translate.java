package defalt.robiproject.algo;

import java.awt.Point;

import defalt.robiproject.graphicLayer.GElement;
import defalt.robiproject.graphicLayer.GRect;
import defalt.robiproject.parser.SNode;

/**
 * Cette classe implémente la commande de translation pour déplacer un élément
 * graphique. Elle prend en charge la translation d'un élément graphique
 * (GElement) ou d'un rectangle (GRect) selon le type de l'objet récepteur.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Translate implements Command {
	/**
	 * Méthode exécutant la commande de translation. Elle déplace l'objet graphique
	 * selon les déplacements spécifiés en dx et dy.
	 * 
	 * @param receiver le récepteur de la commande
	 * @param method   le nœud de l'arbre syntaxique contenant les informations sur
	 *                 la translation
	 * @return le récepteur après exécution de la commande
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
