package defalt.robiproject.algo;


import defalt.robiproject.graphicLayer.GContainer;
import defalt.robiproject.graphicLayer.GElement;
import defalt.robiproject.parser.SNode;

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
        
        Reference refElementClass = this.getRefererenceClass(elementType);
        try {
            Command newElementCommand = this.getNewElementCommand(refElementClass, elementType);
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
