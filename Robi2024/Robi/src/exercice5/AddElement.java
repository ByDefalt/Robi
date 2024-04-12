package exercice5;

import exercice4.Command;
import exercice4.Environment;
import exercice4.NewImage;
import exercice4.NewString;
import exercice4.Reference;
import graphicLayer.GContainer;
import graphicLayer.GElement;
import stree.parser.SNode;

public class AddElement implements Command {
    private Environment environment;

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
        if (receiver == null || receiver.getReceiver() == null) {
            return null;
        }

        GContainer container = (GContainer) receiver.getReceiver();

        if (container == null) {
            return null;
        }
        
        String elementName = method.get(2).contents();
        //concatenation du nom du/des père de l'element qui va être ajouté
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
	 * Méthode pour obtenir la référence de l'element.
	 * 
	 * @param elementType Le type de l'élément.
	 * @return La référence de la classe d'élément.
	 */
    private Reference getRefererenceClass(String elementType) {
        if(elementType.equals("Rect")) {
            return this.environment.getReferenceByName("rect.class");
        } else if(elementType.equals("Oval")) {
            return this.environment.getReferenceByName("oval.class");
        } else if(elementType.equals("Image")) {
            return this.environment.getReferenceByName("image.class");
        } else if(elementType.equals("Label")) {
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
        if(elementType.equals("Rect") || elementType.equals("rect.class") || elementType.equals("Oval") || elementType.equals("oval.class")) {
            return (NewElement) refElementClass.getCommandByName("new");
        } else if(elementType.equals("Label") || elementType.equals("label.class")) {
            return (NewString) refElementClass.getCommandByName("new");
        } else if(elementType.equals("Image") || elementType.equals("image.class")) {
            return (NewImage) refElementClass.getCommandByName("new");
        }
        return null;
    }
}
