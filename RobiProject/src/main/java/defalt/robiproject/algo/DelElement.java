package defalt.robiproject.algo;

import defalt.robiproject.graphicLayer.*;
import defalt.robiproject.parser.SNode;

/**
 * Cette classe représente la commande pour supprimer un élément graphique.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class DelElement implements Command {
	private Environment environment;

	/**
	 * Constructeur de la classe DelElement.
	 * 
	 * @param environment L'environnement dans lequel s'exécute la commande.
	 */
	public DelElement(Environment environment) {
		this.environment = environment;
	}

	/**
	 * Exécute la commande de suppression d'un élément graphique.
	 * 
	 * @param receiver La référence du récepteur de la commande.
	 * @param method   L'expression décrivant la commande.
	 * @return La référence du récepteur de la commande après exécution.
	 */
	public Reference run(Reference receiver, SNode method) {
		try {
			Reference refElement = environment
					.getReferenceByName(method.get(0).contents() + "." + method.get(2).contents());
			if (refElement != null) {
				GSpace space = (GSpace) receiver.getReceiver();
				space.removeElement((GElement) refElement.getReceiver());
				this.environment.removeReference(method.get(0).contents() + "." + method.get(2).contents());
				this.environment.removeReference(method.get(2).contents());
				return receiver;
			} else {
				System.out.println("Element introuvable ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
