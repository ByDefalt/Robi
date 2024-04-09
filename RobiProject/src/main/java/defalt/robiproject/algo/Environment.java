package defalt.robiproject.algo;

import java.util.HashMap;
import java.util.Map;

public class Environment {
	private Map<String, Reference> variables;

	public Environment() {
		this.variables = new HashMap<>();
	}

	public void addReference(String name, Reference reference) {
		this.variables.put(name, reference);
	}

	public Reference getReferenceByName(String name) {
		return this.variables.get(name);
	}

	public Map<String, Reference> getVariables() {
		return this.variables;
	}
}
