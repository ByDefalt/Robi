package defalt.robiproject.algo;

import java.awt.Dimension;

import defalt.robiproject.graphicLayer.GBounded;
import defalt.robiproject.graphicLayer.GSpace;
import defalt.robiproject.parser.SNode;

public class SetDim implements Command {

	@Override
	public Reference run(Reference receiver, SNode expr) {
		Object objet = receiver.getReceiver();
		int x,y;
		x = Integer.parseInt(expr.get(2).contents());
		y = Integer.parseInt(expr.get(3).contents());
		Dimension dim = new Dimension(x, y);
		if(objet instanceof GSpace) {
			((GSpace) objet).changeWindowSize(dim);
		} else {
			((GBounded) objet).setDimension(dim);
		}
		receiver.setReceiver(objet);
		return receiver;
	}

}
