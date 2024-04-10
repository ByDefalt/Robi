package defalt.robiproject.algo;

import defalt.robiproject.parser.SNode;

/**
 * L'interpréteur analyse et exécute les instructions représentées par les nœuds
 * de l'arbre syntaxique.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Interpreter {

	/**
	 * Exécute une instruction représentée par un nœud de l'arbre syntaxique.
	 *
	 * @param environment L'environnement dans lequel l'instruction est exécutée.
	 * @param next        Le nœud de l'arbre syntaxique représentant l'instruction à
	 *                    exécuter.
	 * @return La réponse produite par l'exécution de l'instruction.
	 */
	public Response compute(Environment environment, SNode next) {
		// Vérifie si le nœud contient des enfants
		if (!next.hasChildren()) {
			System.out.println("Problème de syntaxe !");
			return null;
		}

		String receiverName = next.get(0).contents();

		// Vérifie si le récepteur de l'instruction existe dans l'environnement
		if (environment.getReferenceByName(receiverName) != null) {
			environment.getReferenceByName(receiverName).run(next);
		} else {
			System.out.println("L'objet graphique : " + receiverName + " n'existe pas ! ");
		}

		return new Response(environment, next);
	}
}
