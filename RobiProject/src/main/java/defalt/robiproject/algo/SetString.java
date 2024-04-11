package defalt.robiproject.algo;

import defalt.robiproject.graphicLayer.GString;
import defalt.robiproject.parser.SNode;

/**
 * Cette classe implémente la commande "setString" qui permet de définir le
 * texte d'un élément graphique GString.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class SetString implements Command {

	/**
	 * Méthode pour exécuter la commande "setString". Elle récupère le texte à
	 * partir des éléments de l'expression et le définit pour l'objet GString
	 * associé.
	 * 
	 * @param receiver la référence de l'objet GString à modifier
	 * @param expr     le nœud de la méthode contenant le texte à définir
	 * @return la référence de l'objet GString modifié
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
