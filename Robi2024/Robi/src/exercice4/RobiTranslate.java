package exercice4;

import java.awt.Point;

import graphicLayer.GRect;
import stree.parser.SNode;

class RobiTranslate implements Command {
    @Override
    public Reference run(Reference receiver, SNode method) {
        int dx = Integer.parseInt(method.get(2).contents());
        int dy = Integer.parseInt(method.get(3).contents());

        GRect robi = (GRect) receiver.getReceiver();
        robi.translate(new Point(dx, dy));

        return receiver;
    }
}
