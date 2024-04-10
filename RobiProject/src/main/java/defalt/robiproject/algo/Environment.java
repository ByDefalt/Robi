package defalt.robiproject.algo;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe représente l'environnement dans lequel s'exécutent les
 * commandes. Elle contient les références aux objets créés pendant l'exécution
 * du programme.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Environment {
	private Map<String, Reference> variables;

	/**
	 * Constructeur de la classe Environment.
	 */
	public Environment() {
		this.variables = new HashMap<>();
	}

	/**
	 * Ajoute une référence à l'environnement.
	 * 
	 * @param name      Le nom de la référence.
	 * @param reference La référence à ajouter.
	 */
	public void addReference(String name, Reference reference) {
		this.variables.put(name, reference);
	}

	/**
	 * Récupère une référence par son nom.
	 * 
	 * @param name Le nom de la référence à récupérer.
	 * @return La référence correspondante, ou null si elle n'existe pas.
	 */
	public Reference getReferenceByName(String name) {
		return this.variables.get(name);
	}

	/**
	 * Récupère toutes les références de l'environnement.
	 * 
	 * @return La map contenant les références.
	 */
	public Map<String, Reference> getVariables() {
		return this.variables;
	}

	/**
	 * Supprime une référence de l'environnement.
	 * 
	 * @param name Le nom de la référence à supprimer.
	 */
	public void removeReference(String name) {
		this.variables.remove(name);
	}
}
