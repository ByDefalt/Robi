package exercice4;

import graphicLayer.GElement;
import graphicLayer.GSpace;
import stree.parser.SNode;

public class DelElement implements Command {
    private Environment environment;
    
    public DelElement(Environment environment) {
        this.environment = environment;
    }

	public Reference run(Reference receiver, SNode method) {
        try {
            Reference RefElement = environment.getReferenceByName(method.get(2).contents());
            if (RefElement != null) {
	            GSpace space = (GSpace) receiver.getReceiver();
	            space.removeElement((GElement) RefElement.getReceiver());
	            return receiver;
            } else {
                System.out.println("Element introuvable ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}