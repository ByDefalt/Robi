package defalt.robiproject.algo;

import java.awt.Dimension;

import defalt.robiproject.graphicLayer.GBounded;
import defalt.robiproject.graphicLayer.GSpace;
import defalt.robiproject.parser.SNode;

/**
 * Cette classe implémente la commande "setDim" qui permet de définir les
 * dimensions d'un élément graphique.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class SetDim implements Command {

	/**
	 * Méthode pour exécuter la commande "setDim". Elle récupère les dimensions à
	 * partir du nœud de la méthode et les applique à l'élément graphique concerné.
	 * 
	 * @param receiver la référence de l'élément graphique à redimensionner
	 * @param expr     le nœud de la méthode contenant les dimensions
	 * @return la référence de l'élément graphique modifié
	 */
	@Override
	public Reference run(Reference receiver, SNode expr) {
		Object objet = receiver.getReceiver();
		int x, y;
		x = Integer.parseInt(expr.get(2).contents());
		y = Integer.parseInt(expr.get(3).contents());
		Dimension dim = new Dimension(x, y);
		if (objet instanceof GSpace) {
			((GSpace) objet).changeWindowSize(dim);
		} else {
			((GBounded) objet).setDimension(dim);
		}
		receiver.setReceiver(objet);
		return receiver;
	}

}
