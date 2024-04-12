package exercice5;

import java.lang.reflect.InvocationTargetException;

import exercice4.Command;
import exercice4.Environment;
import exercice4.Reference;
import exercice4.SetColor;
import exercice4.SetDim;
import exercice4.Translate;
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
	private Environment environment;
	
	public NewElement(Environment environment) {
		this.environment = environment;
	}
	
	/**
	 * Méthode run. Cette méthode crée un nouvel élément graphique en fonction du
	 * type spécifié, par rapport a la version de l'exercice 4 permet de créer un 
	 * element dans un autre element.
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