package exercice4;

import java.awt.Color;

import graphicLayer.GSpace;
import stree.parser.SNode;


class SpaceChangeColor implements Command {
    @Override
    public Reference run(Reference receiver, SNode method) {
        Color newColor = getColorFromString(method.get(2).contents());
        GSpace space = (GSpace) receiver.getReceiver();
        space.setColor(newColor);
        return receiver;
    }

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
}
