package defalt.robiproject.algo;

import defalt.robiproject.parser.SNode;

public class Reponse {
    private Environment environment;
    private SNode snode;
    public Reponse(Environment environment, SNode snode) {
        this.environment = environment;
        this.snode = snode;
    }

    public Environment getEnvironment() {
        return this.environment;
    }

    public SNode getSNode() {
        return this.snode;
    }
}
