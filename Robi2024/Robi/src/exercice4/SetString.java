package exercice4;

import graphicLayer.GString;
import stree.parser.SNode;

public class SetString implements Command {
	
	@Override
	public Reference run(Reference receiver, SNode expr) {
		GString objet = (GString) receiver.getReceiver();

		// Concaténation du texte à partir des éléments de l'expression
		StringBuilder textBuilder = new StringBuilder();
		for (int i = 2; i < expr.size(); i++) {
			textBuilder.append(expr.get(i).contents());
			if (i < expr.size() - 1) {
				textBuilder.append(" ");
			}
		}
		String texte = textBuilder.toString();
		objet.setString(texte);		// Déf + maj objet GString
		receiver.setReceiver(objet);
		objet.getContainer().repaint();// voir changements
		
		return receiver;
	}

}

