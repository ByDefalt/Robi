package defalt.robiproject.algo;

import java.lang.reflect.InvocationTargetException;

import defalt.robiproject.graphicLayer.GElement;
import defalt.robiproject.parser.SNode;

/**
 * Cette classe représente une commande pour créer un nouvel élément graphique.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class NewElement implements Command {
	private Environment environment;

	/**
	 * Constructeur de la classe NewElement.
	 * 
	 * @param environment L'environnement dans lequel l'élément est créé.
	 */
	public NewElement(Environment environment) {
		this.environment = environment;
	}

	/**
	 * Exécute la commande pour créer un nouvel élément graphique.
	 * 
	 * @param reference La référence de l'élément à créer.
	 * @param method    Le nœud de l'arbre syntaxique représentant la méthode de
	 *                  création de l'élément.
	 * @return La référence de l'élément créé.
	 */
	public Reference run(Reference reference, SNode method) {
		try {
			@SuppressWarnings("unchecked")
			GElement element = ((Class<GElement>) reference.getReceiver()).getDeclaredConstructor().newInstance();
			Reference ref = new Reference(element);
			ref.addCommand("setColor", new SetColor());
			ref.addCommand("translate", new Translate());
			ref.addCommand("setDim", new SetDim());
			ref.addCommand("add", new AddElement(this.environment));
			return ref;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
}
