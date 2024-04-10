package exercice3;

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

/**
 * Cette classe représente l'exercice 3.0.
 *
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Exercice3_0 {
    GSpace space = new GSpace("Exercice 3", new Dimension(200, 100));
    GRect robi = new GRect();
    String script = "" +
            "   (space setColor black) " +
            "   (robi setColor yellow)" +
            "   (space sleep 1000)" +
            "   (space setColor white)\n" + 
            "   (space sleep 1000)" +
            "	(robi setColor red) \n" + 
            "   (space sleep 1000)" +
            "	(robi translate 100 0)\n" + 
            "	(space sleep 1000)\n" + 
            "	(robi translate 0 50)\n" + 
            "	(space sleep 1000)\n" + 
            "	(robi translate -100 0)\n" + 
            "	(space sleep 1000)\n" + 
            "	(robi translate 0 -40)";

    /**
     * Initialise l'exercice 3.0.
     */
    public Exercice3_0() {
        this.space.addElement(this.robi);
        this.space.open();
        this.runScript();
    }

    /**
     * Exécute le script.
     */
    private void runScript() {
        SParser<SNode> parser = new SParser<>();
        List<SNode> rootNodes = null;
        try {
            rootNodes = parser.parse(script);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<SNode> itor = rootNodes.iterator();
        while (itor.hasNext()) {
            this.run(itor.next());
        }
    }

    /**
     * Exécute un nœud.
     * @param expr Le nœud à exécuter.
     */
    private void run(SNode expr) {
        Command cmd = getCommandFromExpr(expr);
        if (cmd == null)
            throw new Error("unable to get command for: " + expr);
        cmd.run();
    }

    /**
     * Obtient la commande à partir du nœud.
     * @param expr Le nœud.
     * @return La commande.
     */
    Command getCommandFromExpr(SNode expr) {
        String target = expr.children().get(0).contents();
        String command = expr.children().get(1).contents();
        String[] params = new String[expr.children().size() - 2];

        for (int i = 0; i < params.length; i++) {
            params[i] = expr.children().get(i + 2).contents();
        }

        switch (target) {
            case "space":
                return getSpaceCommand(command, params);
            case "robi":
                return getRobiCommand(command, params);
            default:
                return null;
        }
    }

    /**
     * Obtient la commande pour l'espace.
     * @param command La commande.
     * @param params Les paramètres.
     * @return La commande pour l'espace.
     */
    private Command getSpaceCommand(String command, String[] params) {
        switch (command) {
            case "setColor":
                return new SpaceChangeColor(getColorFromString(params[0]));
            case "sleep":
                return new SleepCommand(Integer.parseInt(params[0]));
            default:
                return null;
        }
    }

    /**
     * Obtient la commande pour Robi.
     * @param command La commande.
     * @param params Les paramètres.
     * @return La commande pour Robi.
     */
    private Command getRobiCommand(String command, String[] params) {
        switch (command) {
            case "setColor":
                return new RobiChangeColor(this.robi, getColorFromString(params[0]));
            case "translate":
                int x = Integer.parseInt(params[0]);
                int y = Integer.parseInt(params[1]);
                return new RobiTranslate(this.robi, x, y);
            default:
                return null;
        }
    }

    /**
     * Obtient la couleur à partir d'une chaîne de caractères.
     * @param colorStr La chaîne de caractères représentant la couleur.
     * @return La couleur correspondante.
     */
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

    /**
     * Méthode principale de l'exercice 3.0.
     * @param args Arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        new Exercice3_0();
    }

    /**
     * Interface pour les commandes.
     */
    public interface Command {
        /**
         * Exécute la commande.
         */
        void run();
    }

    /**
     * Commande pour changer la couleur de l'espace.
     */
    class SpaceChangeColor implements Command {
        Color newColor;

        public SpaceChangeColor(Color newColor) {
            this.newColor = newColor;
        }

        @Override
        public void run() {
            space.setColor(this.newColor);
        }
    }

    /**
     * Commande pour changer la couleur de Robi.
     */
    class RobiChangeColor implements Command {
        GRect robi;
        Color newColor;

        public RobiChangeColor(GRect robi, Color newColor) {
            this.robi = robi;
            this.newColor = newColor;
        }

        @Override
        public void run() {
            this.robi.setColor(this.newColor);
        }
    }

    /**
     * Commande pour déplacer Robi.
     */
    class RobiTranslate implements Command {
        GRect robi;
        int dx, dy;

        public RobiTranslate(GRect robi, int dx, int dy) {
            this.robi = robi;
            this.dx = dx;
            this.dy = dy;
        }

        @Override
        public void run() {
            this.robi.translate(new Point(dx, dy));
        }
    }

    /**
     * Commande pour faire une pause.
     */
    class SleepCommand implements Command {
        int duration;

        public SleepCommand(int duration) {
            this.duration = duration;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(this.duration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
