package exercice4;

import graphicLayer.GString;
import stree.parser.SNode;

/**
 * Classe NewString. Cette classe implémente la commande pour créer une nouvelle
 * chaîne graphique.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class NewString implements Command {

	/**
	 * Méthode run. Cette méthode crée une nouvelle chaîne graphique à partir du
	 * texte spécifié.
	 * 
	 * @param receiver La référence de la classe de la chaîne graphique à créer.
	 * @param expr     Le nœud représentant la méthode à exécuter.
	 * @return La référence de la nouvelle chaîne graphique créée.
	 */
	@Override
	public Reference run(Reference receiver, SNode expr) {
		String text = expr.get(3).children().get(2).contents().replace("\"", "");

		Reference ref = null;
		try {
			GString str = new GString(text);
			ref = new Reference(str);
			ref.addCommand("setString", new SetString());
			ref.addCommand("setColor", new SetColor());
			ref.addCommand("translate", new Translate());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ref;
	}

}
