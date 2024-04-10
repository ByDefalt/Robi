package exercice4;

import java.util.HashMap;
import java.util.Map;
import stree.parser.SNode;

/**
 * Cette classe représente une référence vers un objet, avec la possibilité
 * d'exécuter des commandes sur cet objet.
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
	 * Constructeur de la classe Reference.
	 * 
	 * @param receiver L'objet récepteur des commandes.
	 */
	public Reference(Object receiver) {
		this.setReceiver(receiver);
		this.primitives = new HashMap<>();
	}

	/**
	 * Méthode pour définir l'objet récepteur.
	 * 
	 * @param receiver L'objet récepteur des commandes.
	 */
	public void setReceiver(Object receiver) {
		this.receiver = receiver;
	}

	/**
	 * Méthode pour obtenir l'objet récepteur.
	 * 
	 * @return L'objet récepteur des commandes.
	 */
	public Object getReceiver() {
		return this.receiver;
	}

	/**
	 * Méthode pour obtenir une commande par son nom.
	 * 
	 * @param selector Le nom de la commande.
	 * @return La commande correspondante.
	 */
	public Command getCommandByName(String selector) {
		return this.primitives.get(selector);
	}

	/**
	 * Méthode pour ajouter une commande à la référence.
	 * 
	 * @param selector  Le nom de la commande.
	 * @param primitive La commande à ajouter.
	 */
	public void addCommand(String selector, Command primitive) {
		this.primitives.put(selector, primitive);
	}

	/**
	 * Méthode pour exécuter une commande sur la référence.
	 * 
	 * @param method La méthode à exécuter.
	 * @return La référence mise à jour après l'exécution de la commande.
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
