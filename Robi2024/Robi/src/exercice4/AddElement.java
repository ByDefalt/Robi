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
        try {
            Reference RefElement = environment.getReferenceByName(method.get(2).get(0).contents());          
            if (RefElement != null) {
                GSpace space = (GSpace) receiver.getReceiver();
                space.addElement((GElement) RefElement.getReceiver());
                return receiver;
            } else {
                System.out.println("Element introuvable");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}