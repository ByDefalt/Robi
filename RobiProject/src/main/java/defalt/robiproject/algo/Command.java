package defalt.robiproject.algo;

import defalt.robiproject.parser.SNode;

/**
 * Interface représentant une commande.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public interface Command {
	/**
	 * Méthode pour exécuter la commande.
	 * 
	 * @param receiver Le récepteur qui va exécuter la méthode.
	 * @param method   La s-expression résultante de la compilation du code source à
	 *                 exécuter.
	 * @return La référence résultante de l'exécution de la commande.
	 */
	Reference run(Reference receiver, SNode method);
}
