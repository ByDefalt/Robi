package defalt.robiproject.algo;

import java.awt.Point;

import defalt.robiproject.graphicLayer.GElement;
import defalt.robiproject.graphicLayer.GRect;
import defalt.robiproject.parser.SNode;

public class Translate implements Command {
    @Override
    public Reference run(Reference receiver, SNode method) {
    	Object objet;
        int dx = Integer.parseInt(method.get(2).contents());
        int dy = Integer.parseInt(method.get(3).contents());

        objet = receiver.getReceiver();
        if(objet instanceof GRect){
        	((GRect)objet).translate(new Point(dx, dy));
        }else{
        	((GElement)objet).translate(new Point(dx, dy));
        }
        return receiver;
    }
}






