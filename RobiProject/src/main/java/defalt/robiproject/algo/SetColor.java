package defalt.robiproject.algo;

import java.awt.Color;

import defalt.robiproject.graphicLayer.GElement;
import defalt.robiproject.graphicLayer.GRect;
import defalt.robiproject.graphicLayer.GSpace;
import defalt.robiproject.parser.SNode;

public class SetColor implements Command {

    private Color getColorFromString(String colorStr) {
        switch (colorStr) {
            case "black":
                return Color.BLACK;
            case "yellow":
                return Color.YELLOW;
            case "white":
                return Color.WHITE;
            case "red":
                return Color.RED;
            default:
                return null;
        }
    }

	@Override
	public Reference run(Reference receiver, SNode method) {
		Object objet ;
	    Color newColor = getColorFromString(method.get(2).contents());
	    
	    
	    objet = receiver.getReceiver();
		if(objet instanceof GSpace){
			((GSpace)objet).setColor(newColor);
		} else if(objet instanceof GRect){
			((GRect)objet).setColor(newColor);
		}else{
			((GElement)objet).setColor(newColor);
		}
	return receiver;
	}
}