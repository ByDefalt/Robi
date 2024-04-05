package exercice4;

import graphicLayer.GString;
import stree.parser.SNode;

public class NewString implements Command {

    @Override
    public Reference run(Reference receiver, SNode expr) {
        StringBuilder textBuilder = new StringBuilder();
        for (int i = 2; i < expr.size(); i++) {
            textBuilder.append(expr.get(i).contents());
            if (i < expr.size() - 1) {
                textBuilder.append(" ");
            }
        }
        String text = textBuilder.toString();
        
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

