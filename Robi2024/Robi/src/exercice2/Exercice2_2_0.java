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

/**
 * Classe principale pour l'exercice 2_2_0. Cette classe crée un espace graphique
 * et un rectangle, puis exécute un script pour effectuer différentes actions telles que
 * changer la couleur de l'espace ou du rectangle, et déplacer le rectangle.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class Exercice2_2_0 {
<<<<<<< HEAD
    public GSpace space = new GSpace("Exercice 2_1", new Dimension(200, 100));
    public GRect robi = new GRect();
    String script = "(space setColor white) " + "(robi setColor red) " + "(robi translate 10 0) " + "(space sleep 100) " + "(robi translate 0 10) " + "(space sleep 100) " + "(robi translate -10 0) " + "(space sleep 100) " + "(robi translate 0 -10)";
=======
    GSpace space = new GSpace("Exercice 2_1", new Dimension(200, 100));
    GRect robi = new GRect();
    String script = "(space setColor white) " +
                    "(robi setColor red) " +
                    "(robi translate 10 0) " +
                    "(space sleep 100) " +
                    "(robi translate 0 10) " +
                    "(space sleep 100) " +
                    "(robi translate -10 0) " +
                    "(space sleep 100) " +
                    "(robi translate 0 -10)";
>>>>>>> 1a688b8e99892dffe5cd195b5dbd9c3d807d036c

    /**
     * Constructeur de la classe Exercice2_2_0. Initialise l'espace graphique, le
     * rectangle et exécute le script.
     */
    public Exercice2_2_0() {
        this.space.addElement(this.robi); 
        this.space.open(); 
        this.runScript(); 
    }

    /**
     * Méthode pour exécuter le script.
     */
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

    /**
     * Méthode pour exécuter un nœud du script.
     * 
     * @param expr Le nœud à exécuter.
     */
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

    /**
     * Méthode pour gérer les commandes de l'espace.
     * 
     * @param commande La commande à exécuter.
     * @param params   Les paramètres de la commande.
     */
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

    /**
     * Méthode pour gérer les commandes du rectangle.
     * 
     * @param commande La commande à exécuter.
     * @param params   Les paramètres de la commande.
     */
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

    /**
     * Méthode pour obtenir une couleur à partir d'une chaîne de caractères.
     * 
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
     * Méthode principale, point d'entrée de l'application. Crée une instance de la
     * classe Exercice2_2_0.
     * 
     * @param args Arguments de la ligne de commande (non utilisés).
     */
    public static void main(String[] args) {
        new Exercice2_2_0(); 
    }
}
