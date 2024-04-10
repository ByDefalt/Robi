package exercice4;

import stree.parser.SNode;

/**
 * Classe Interpreter. Cette classe permet d'interpréter une instruction en
 * fonction de l'environnement donné.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Interpreter {

	/**
	 * Méthode compute. Cette méthode interprète une instruction en fonction de
	 * l'environnement donné.
	 * 
	 * @param environment L'environnement contenant les références aux objets
	 *                    graphiques.
	 * @param next        Le nœud représentant l'instruction à interpréter.
	 */
	public void compute(Environment environment, SNode next) {

		if (!next.hasChildren()) {
			System.out.println("Problème de syntaxe !");
			return;
		}

		String receiverName = next.get(0).contents();

		if (environment.getReferenceByName(receiverName) != null) {
			environment.getReferenceByName(receiverName).run(next);
		} else {
			System.out.println("L'objet graphique : " + receiverName + " n'existe pas ! ");
		}
	}

}
