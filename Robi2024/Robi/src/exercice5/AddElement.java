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

    public Reference run(Reference receiver, SNode method) {
        GContainer container = (GContainer) receiver.getReceiver();

        String elementName = method.get(2).contents();
        String containerNameAll = method.get(0).contents() + "." + elementName;
        String elementType = method.get(3).get(0).contents();

        Reference refElementClass = this.environment.getReferenceByName(elementType);

        try {
            Command newElementCommand = getNewElementCommand(refElementClass, elementType);
            if (newElementCommand != null) {
                Reference newElementReference = newElementCommand.run(refElementClass, method);
                GElement element = (GElement) newElementReference.getReceiver();
                
                this.environment.addReference(containerNameAll, newElementReference);
				container.addElement(element);
                return receiver;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

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
