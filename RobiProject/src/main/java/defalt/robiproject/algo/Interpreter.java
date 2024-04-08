package defalt.robiproject.algo;

import defalt.robiproject.parser.SNode;

public class Interpreter {

	public void compute(Environment environment, SNode next) {	
		if(!next.hasChildren()) {
			System.out.println("Problèmme de syntaxe !");
			return;
		}
		String receiverName = next.get(0).contents();
        if (environment.getReferenceByName(receiverName) != null) {
            environment.getReferenceByName(receiverName).run(next);
        } else {
            System.out.println("L'objet graphique : " + receiverName + " n'existe pas ! ");
        }
			 
	}

}