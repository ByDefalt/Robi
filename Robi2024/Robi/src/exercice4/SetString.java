package exercice4;

import graphicLayer.GString;
import stree.parser.SNode;

/**
 * Cette classe représente la commande pour définir le texte d'un élément
 * graphique de type GString. Elle implémente l'interface Command.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class SetString implements Command {

	/**
	 * Cette méthode exécute la commande pour définir le texte d'un élément
	 * graphique GString. Elle prend en paramètres une référence vers l'élément
	 * graphique GString et un nœud représentant la commande.
	 * 
	 * @param receiver La référence vers l'élément graphique GString.
	 * @param expr     Le nœud représentant la commande.
	 * @return La référence vers l'élément graphique GString après exécution de la
	 *         commande.
	 */
	@Override
	public Reference run(Reference receiver, SNode expr) {
		GString objet = (GString) receiver.getReceiver();

		StringBuilder textBuilder = new StringBuilder();
		for (int i = 2; i < expr.size(); i++) {
			textBuilder.append(expr.get(i).contents());
			if (i < expr.size() - 1) {
				textBuilder.append(" ");
			}
		}
		String texte = textBuilder.toString();
		objet.setString(texte);
		receiver.setReceiver(objet);
		objet.getContainer().repaint();

		return receiver;
	}

}
