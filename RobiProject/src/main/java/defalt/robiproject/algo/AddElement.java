package defalt.robiproject.algo;

import defalt.robiproject.graphicLayer.GContainer;
import defalt.robiproject.graphicLayer.GElement;
import defalt.robiproject.parser.SNode;

/**
 * Cette classe représente une commande pour ajouter un élément à un conteneur
 * graphique. Elle implémente l'interface Command.
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
	 * @param environment L'environnement dans lequel la commande est exécutée.
	 */
	public AddElement(Environment environment) {
		this.environment = environment;
	}

	/**
	 * Méthode pour exécuter la commande d'ajout d'élément.
	 * 
	 * @param receiver L'objet receveur de la commande.
	 * @param method   La méthode contenant les détails de l'élément à ajouter.
	 * @return La référence du receveur de la commande.
	 */
	public Reference run(Reference receiver, SNode method) {
		GContainer container = (GContainer) receiver.getReceiver();

		String elementName = method.get(2).contents();
		String containerNameAll = method.get(0).contents() + "." + elementName;
		String elementType = method.get(3).get(0).contents();

		Reference refElementClass = this.getRefererenceClass(elementType);
		try {
			Command newElementCommand = this.getNewElementCommand(refElementClass, elementType);
			if (newElementCommand != null) {
				Reference newElementReference = newElementCommand.run(refElementClass, method);
				GElement element = (GElement) newElementReference.getReceiver();

				this.environment.addReference(elementName, newElementReference);
				this.environment.addReference(containerNameAll, newElementReference);
				container.addElement(element);
				return receiver;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Méthode pour obtenir la référence de la classe d'élément.
	 * 
	 * @param elementType Le type de l'élément.
	 * @return La référence de la classe d'élément.
	 */
	private Reference getRefererenceClass(String elementType) {
		if (elementType.equals("Rect")) {
			return this.environment.getReferenceByName("rect.class");
		} else if (elementType.equals("Oval")) {
			return this.environment.getReferenceByName("oval.class");
		} else if (elementType.equals("Image")) {
			return this.environment.getReferenceByName("image.class");
		} else if (elementType.equals("Label")) {
			return this.environment.getReferenceByName("label.class");
		}
		return this.environment.getReferenceByName(elementType);
	}

	/**
	 * Méthode pour obtenir la commande de création d'un nouvel élément.
	 * 
	 * @param refElementClass La référence de la classe d'élément.
	 * @param elementType     Le type de l'élément.
	 * @return La commande de création d'un nouvel élément.
	 */
	private Command getNewElementCommand(Reference refElementClass, String elementType) {
		if (elementType.equals("Rect") || elementType.equals("rect.class") || elementType.equals("Oval")
				|| elementType.equals("oval.class")) {
			return (NewElement) refElementClass.getCommandByName("new");
		} else if (elementType.equals("Label") || elementType.equals("label.class")) {
			return (NewString) refElementClass.getCommandByName("new");
		} else if (elementType.equals("Image") || elementType.equals("image.class")) {
			return (NewImage) refElementClass.getCommandByName("new");
		}
		return null;
	}
}
