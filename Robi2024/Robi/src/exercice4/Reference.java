package exercice4;

import java.util.HashMap;
import java.util.Map;

import stree.parser.SNode;

public class Reference {
	private Object receiver;
	private Map<String, Command> primitives;
	
	public Reference(Object receiver) {
		this.receiver = receiver;
		this.primitives = new HashMap<>();
	}
	
	public Object getReceiver() {
		return this.receiver;
	}
	
	public Command getCommandByName(String selector) {
		return	this.primitives.get(selector);
	}
	
	public void addCommand(String selector, Command primitive) {
		this.primitives.put(selector, primitive);
	}

	public Reference run(SNode method) {
	    String methodName = method.get(1).contents();
	    Command command = getCommandByName(methodName);
	    if (command != null) {
	        return command.run(this, method);
	    } else {
	        System.out.println("Erreur : Commande inconnue - " + methodName);
	        return null;
	    }
	}
}
