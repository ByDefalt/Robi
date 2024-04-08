package defalt.robiproject.algo;

import defalt.robiproject.graphicLayer.GString;
import defalt.robiproject.parser.SNode;

public class NewString implements Command {

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

