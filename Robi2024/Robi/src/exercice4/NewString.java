package exercice4;

import graphicLayer.GString;
import stree.parser.SNode;

public class NewString implements Command {

    @Override
    public Reference run(Reference receiver, SNode expr) {
        for(int i = 0; i < expr.size(); i++) {
        	for(int j = 0; j < expr.get(i).children().size(); j++) {
        		System.out.println("i: " + i + ", j: " + j + ", contentss: " + expr.get(i).children().get(j).contents());
        	}
        }
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

