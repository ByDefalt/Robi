package defalt.robiproject.algo;

import defalt.robiproject.parser.SNode;

/**
 * Cette classe implémente la commande "sleep" qui permet de mettre en pause
 * l'exécution du programme pendant un certain temps.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class SleepCommand implements Command {

	/**
	 * Méthode pour exécuter la commande "sleep". Elle met en pause l'exécution du
	 * programme pendant la durée spécifiée.
	 * 
	 * @param receiver la référence de l'objet sur lequel la commande est appliquée
	 *                 (non utilisé dans cette commande)
	 * @param method   le nœud de la méthode contenant la durée de la pause
	 * @return la référence de l'objet sur lequel la commande est appliquée
	 */
	@Override
	public Reference run(Reference receiver, SNode method) {
		int duration = Integer.parseInt(method.get(2).contents());

		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return receiver;
	}
}
