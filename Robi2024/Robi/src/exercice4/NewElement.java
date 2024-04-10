package exercice4;

import java.lang.reflect.InvocationTargetException;

import graphicLayer.GElement;
import stree.parser.SNode;

/**
 * Classe NewElement. Cette classe implémente la commande pour créer un nouvel
 * élément graphique.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class NewElement implements Command {

	/**
	 * Méthode run. Cette méthode crée un nouvel élément graphique en fonction du
	 * type spécifié.
	 * 
	 * @param reference La référence de la classe de l'élément graphique à créer.
	 * @param method    Le nœud représentant la méthode à exécuter.
	 * @return La référence du nouvel élément graphique créé.
	 */
	public Reference run(Reference reference, SNode method) {
		try {
			@SuppressWarnings("unchecked")
			GElement e = ((Class<GElement>) reference.getReceiver()).getDeclaredConstructor().newInstance();
			Reference ref = new Reference(e);
			ref.addCommand("setColor", new SetColor());
			ref.addCommand("translate", new Translate());
			ref.addCommand("setDim", new SetDim());
			return ref;
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}
