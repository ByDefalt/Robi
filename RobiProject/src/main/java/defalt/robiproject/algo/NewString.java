package defalt.robiproject.algo;

import defalt.robiproject.graphicLayer.GString;
import defalt.robiproject.parser.SNode;

/**
 * Cette classe représente une commande pour créer une nouvelle chaîne graphique.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class NewString implements Command {

    /**
     * Exécute la commande pour créer une nouvelle chaîne graphique.
     * 
     * @param receiver La référence de l'élément à créer.
     * @param expr     Le nœud de l'arbre syntaxique représentant l'expression pour créer la chaîne.
     * @return La référence de la chaîne créée.
     */
    @Override
    public Reference run(Reference receiver, SNode expr) {
        String text = expr.get(3).children().get(2).contents().replace("\"", "");
        
        Reference ref = null;
        try {
            GString str = new GString(text);
            ref = new Reference(str);
            ref.addCommand("setString", new SetString());
            ref.addCommand("setColor", new SetColor());
            ref.addCommand("translate", new Translate());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ref;
    }

}
