package exercice4;

import java.awt.Dimension;

import graphicLayer.GBounded;
import stree.parser.SNode;

public class SetDim implements Command {

	GBounded objet;
	@Override
	public Reference run(Reference receiver, SNode expr) {
		objet = (GBounded)receiver.getReceiver();
		int x,y;
		x = Integer.parseInt(expr.get(2).contents());
		y = Integer.parseInt(expr.get(3).contents());
		Dimension dim = new Dimension(x, y);
		objet.setDimension(dim);
		receiver.setReceiver(objet);
		System.out.println(expr.get(2).contents());
		return receiver;
	}

}
