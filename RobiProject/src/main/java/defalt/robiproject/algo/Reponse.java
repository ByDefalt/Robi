package defalt.robiproject.algo;

import defalt.robiproject.parser.SNode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente une réponse à une requête, contenant l'environnement et les nœuds S.
 */
public class Reponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<String> environment; 
    private String snode; 

    /**
     * Constructeur de la classe Reponse.
     * @param environment L'environnement à inclure dans la réponse.
     * @param snode Les nœuds S à inclure dans la réponse.
     *
     * @author LE BRAS Erwan
     * @author ROUSVAL Romain
     * @author NICOLAS Pierre
     * @author KERVRAN Maxime
     */
    public Reponse(Environment environment, SNode snode) {
        this.environment = new ArrayList<>();
        this.environment.addAll(environment.getVariables().keySet());

        for(int i = 0; i < snode.size(); i++) {
            this.snode += snode.get(i).contents() + " ";
            System.out.println(snode.get(i).contents());
            if(snode.get(i).contents() == null) {
                for(int j = 0; j < snode.get(i).size(); j++) {
                    System.out.println(snode.get(i).get(j).contents());
                }
            }
        }
        this.snode += "\n";
    }

    /**
     * Obtient la liste des variables de l'environnement.
     * @return La liste des variables de l'environnement.
     */
    public List<String> getEnvironment() {
        return this.environment;
    }

    /**
     * Obtient la chaîne de caractères représentant les nœuds S.
     * @return La chaîne de caractères représentant les nœuds S.
     */
    public String getSNode() {
        return this.snode;
    }
}
