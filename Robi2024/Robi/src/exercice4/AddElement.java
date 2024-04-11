package exercice4;

import graphicLayer.GElement;
import graphicLayer.GSpace;
import stree.parser.SNode;


public class AddElement implements Command {
    private Environment environment;

    public AddElement(Environment environment) {
        this.environment = environment;
    }

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

    public Command getNewElementCommand(Reference refElementClass, String elementType) {
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







