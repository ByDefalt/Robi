package defalt.robiproject.algo;

import defalt.robiproject.parser.SNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Reponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> environment;
    private String snode;
    public Reponse(Environment environment, SNode snode) {
        this.environment = new ArrayList<>();
        this.environment.addAll(environment.getVariables().keySet());

        for(int i = 0; i < snode.size(); i++) {
            this.snode += snode.get(i).contents() + " ";
            System.out.println(snode.get(i).contents());
            if(snode.get(i).contents()==null){
                for(int j=0;j<snode.get(i).size();j++){
                    System.out.println(snode.get(i).get(j).contents());
                }
            }
        }
        this.snode += "\n";
    }

    public List<String> getEnvironment() {
        return this.environment;
    }

    public String getSNode() {
        return this.snode;
    }
}
