package exercice4;

import stree.parser.SNode;

/**
 * Cette classe représente la commande pour mettre en pause l'exécution pendant
 * une durée spécifiée. Elle implémente l'interface Command.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class SleepCommand implements Command {

	/**
	 * Cette méthode exécute la commande pour mettre en pause l'exécution pendant
	 * une durée spécifiée. Elle prend en paramètres une référence et un nœud
	 * représentant la commande.
	 * 
	 * @param receiver La référence vers l'objet à mettre en pause.
	 * @param method   Le nœud représentant la commande.
	 * @return La référence vers l'objet après mise en pause.
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
