package exercice4;

import graphicLayer.GElement;
import graphicLayer.GSpace;
import stree.parser.SNode;

/**
 * Commande pour ajouter un élément graphique à l'espace graphique.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class AddElement implements Command {
	private Environment environment;

	/**
	 * Constructeur de la classe AddElement.
	 * 
	 * @param environment L'environnement d'exécution.
	 */
	public AddElement(Environment environment) {
		this.environment = environment;
	}

	/**
	 * Exécute la commande pour ajouter un élément graphique à l'espace graphique.
	 * 
	 * @param receiver Le récepteur de la commande.
	 * @param method   La méthode à exécuter.
	 * @return Le récepteur de la commande.
	 */
	public Reference run(Reference receiver, SNode method) {
		GSpace space = (GSpace) receiver.getReceiver();
		String elementType = method.get(3).get(0).contents();

		Reference refElementClass = this.environment.getReferenceByName(elementType);

		try {
			Command newElementCommand = getNewElementCommand(refElementClass, elementType);
			if (newElementCommand != null) {
				Reference newElementReference = newElementCommand.run(refElementClass, method);
				GElement element = (GElement) newElementReference.getReceiver();
				this.environment.addReference(method.get(2).contents(), newElementReference);
				space.addElement(element);
				return receiver;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Obtient la commande pour créer un nouvel élément graphique.
	 * 
	 * @param refElementClass La référence de la classe de l'élément.
	 * @param elementType     Le type de l'élément.
	 * @return La commande pour créer un nouvel élément graphique.
	 */
	private Command getNewElementCommand(Reference refElementClass, String elementType) {
		switch (elementType) {
		case "GRect":
			return (NewElement) this.environment.getReferenceByName("rect.class").getCommandByName("new");
		case "GOval":
			return (NewElement) this.environment.getReferenceByName("oval.class").getCommandByName("new");
		case "rect.class":
			return (NewElement) refElementClass.getCommandByName("new");
		case "oval.class":
			return (NewElement) refElementClass.getCommandByName("new");
		case "image.class":
			return (NewImage) refElementClass.getCommandByName("new");
		case "label.class":
			return (NewString) refElementClass.getCommandByName("new");
		default:
			return null;
		}
	}
}
