package defalt.robiproject.algo;

import java.util.HashMap;
import java.util.Map;

import defalt.robiproject.parser.SNode;

/**
 * Cette classe représente une référence vers un objet et permet d'exécuter des
 * commandes sur cet objet. Elle associe des sélecteurs de commandes à des
 * primitives pour les exécuter.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Reference {
	private Object receiver;
	private Map<String, Command> primitives;

	/**
	 * Initialise une nouvelle référence avec un objet récepteur.
	 * 
	 * @param receiver L'objet récepteur de la référence.
	 */
	public Reference(Object receiver) {
		this.setReceiver(receiver);
		this.primitives = new HashMap<>();
	}

	/**
	 * Définit l'objet récepteur de la référence.
	 * 
	 * @param receiver L'objet récepteur à définir.
	 */
	public void setReceiver(Object receiver) {
		this.receiver = receiver;
	}

	/**
	 * Récupère l'objet récepteur de la référence.
	 * 
	 * @return L'objet récepteur de la référence.
	 */
	public Object getReceiver() {
		return this.receiver;
	}

	/**
	 * Récupère la commande associée à un sélecteur donné.
	 * 
	 * @param selector Le sélecteur de commande.
	 * @return La commande associée au sélecteur.
	 */
	public Command getCommandByName(String selector) {
		return this.primitives.get(selector);
	}

	/**
	 * Ajoute une commande associée à un sélecteur.
	 * 
	 * @param selector  Le sélecteur de commande.
	 * @param primitive La commande à associer au sélecteur.
	 */
	public void addCommand(String selector, Command primitive) {
		this.primitives.put(selector, primitive);
	}

	/**
	 * Exécute une méthode représentée par un nœud de l'arbre syntaxique.
	 * 
	 * @param method Le nœud de l'arbre syntaxique représentant la méthode à
	 *               exécuter.
	 * @return La référence résultante après l'exécution de la méthode.
	 */
	public Reference run(SNode method) {
		String methodName = method.get(1).contents();
		Command command = getCommandByName(methodName);
		if (command != null) {
			return command.run(this, method);
		} else {
			System.out.println("Erreur : Commande inconnue - " + methodName);
			return null;
		}
	}
}
