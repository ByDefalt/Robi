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
	
	public Command getCommandByName(String selector) {
		return	this.primitives.get(selector);
	}
	
	public void addCommand(String selector, Command primitive) {
		this.primitives.put(selector, primitive);
	}

	public Reference run(SNode method) {
		//to do
	    return null;
	}

}
