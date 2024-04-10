package exercice2;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;

public class Exercice2_2_0 {
    public GSpace space = new GSpace("Exercice 2_1", new Dimension(200, 100));
    public GRect robi = new GRect();
    String script = "(space setColor white) " + "(robi setColor red) " + "(robi translate 10 0) " + "(space sleep 100) " + "(robi translate 0 10) " + "(space sleep 100) " + "(robi translate -10 0) " + "(space sleep 100) " + "(robi translate 0 -10)";

    public Exercice2_2_0() {
        this.space.addElement(this.robi);
        this.space.open();
        this.runScript();
    }

    private void runScript() {
        SParser<SNode> parser = new SParser<>();
        List<SNode> rootNodes = null;
        try {
            rootNodes = parser.parse(this.script);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<SNode> itor = rootNodes.iterator();
        while (itor.hasNext()) {
            this.run(itor.next());
        }
    }

    private void run(SNode expr) {
        if (!expr.isLeaf()) {
            List<SNode> children = expr.children();
            String cible = children.get(0).contents();
            String commande = children.get(1).contents();
            
            switch (cible) {
                case "space":
                    handleSpaceCommand(commande, children);
                    break;
                case "robi":
                    handleRobiCommand(commande, children);
                    break;
            }
        }
    }

    private void handleSpaceCommand(String commande, List<SNode> params) {
        switch (commande) {
            case "setColor":
            	this.space.setColor(getColorFromString(params.get(2).contents()));
                break;
            case "sleep":
                try {
                    Thread.sleep(Integer.parseInt(params.get(2).contents()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void handleRobiCommand(String commande, List<SNode> params) {
        switch (commande) {
            case "setColor":
            	this.robi.setColor(getColorFromString(params.get(2).contents()));
                break;
            case "translate":
                int x = Integer.parseInt(params.get(2).contents());
                int y = Integer.parseInt(params.get(3).contents());
                this.robi.translate(new Point(x, y));
                break;
        }
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

    public static void main(String[] args) {
        new Exercice2_2_0();
    }
}
