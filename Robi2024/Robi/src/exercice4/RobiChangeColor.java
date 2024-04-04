package exercice4;

import java.awt.Color;

import graphicLayer.GRect;
import stree.parser.SNode;

class RobiChangeColor implements Command {

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
        Color newColor = getColorFromString(method.get(2).contents());

        GRect robi = (GRect) receiver.getReceiver();
        robi.setColor(newColor);

        return receiver;
    }
}