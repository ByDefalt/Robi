package exercice2;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import graphicLayer.GRect;
import graphicLayer.GSpace;
import stree.parser.SNode;
import stree.parser.SParser;


public class Exercice2_1_0 {
	public GSpace space = new GSpace("Exercice 2_1", new Dimension(200, 100));
	public GRect robi = new GRect();
	String script = "(space setColor black) (robi setColor yellow)";

	public Exercice2_1_0() {
		space.addElement(robi);
		space.open();
		this.runScript();
	}

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
	
		private void run(SNode expr) {
			 if (!expr.isLeaf()) {
		            List<SNode> children = expr.children();
		            if (children.size() >= 3) {
		                String cible = children.get(0).contents();
		                String propriete = children.get(1).contents();
		                String valeur = children.get(2).contents();

		                switch (cible) {
	                    case "space":
	                        if (propriete.equals("setColor")) {
	                            space.setColor(getColorFromString(valeur));
	                        }
	                        break;
	                    case "robi":
	                        if (propriete.equals("setColor")) {
	                            robi.setColor(getColorFromString(valeur));
	                        }
	                        break;
	                }
	            }
	        }
	    }

	    private Color getColorFromString(String colorStr) {
	        switch (colorStr) {
	            case "black":
	                return Color.BLACK;
	            case "yellow":
	                return Color.YELLOW;
	            default:
	                return null;
	        }
	    }

	public static void main(String[] args) {
		new Exercice2_1_0();
	}

}