package exercice4;

import graphicLayer.GElement;
import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;

public class AddElement implements Command {
    private Environment environment;
    
    public AddElement(Environment environment) {
        this.environment = environment;
    }

    public Reference run(Reference receiver, SNode method) {
        GSpace space = (GSpace) receiver.getReceiver();
        String elementClassName = method.get(3).get(0).contents();
        
        Reference refElementClass = this.environment.getReferenceByName(elementClassName);
        try {
            NewElement newElementCommand = (NewElement) refElementClass.getCommandByName("new");
            Reference newElementReference = newElementCommand.run(refElementClass, method);
            
            GElement element = (GElement) newElementReference.getReceiver();
            this.environment.addReference(method.get(2).contents(), newElementReference);
            space.addElement(element);
            return receiver;
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return null;
    }
}




