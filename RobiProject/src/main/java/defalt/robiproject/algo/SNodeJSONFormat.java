package defalt.robiproject.algo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import defalt.robiproject.parser.SDefaultNode;
import defalt.robiproject.parser.SNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Cette classe représente un nœud S sous forme de format JSON.
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class SNodeJSONFormat {

    private String commandname; // Nom de la commande

    private List<SNodeJSONFormat> children = new ArrayList<>(); // Liste des enfants du nœud

    /**
     * Obtient le nom de la commande.
     * @return Le nom de la commande.
     */
    public String getCommandname() {
        return commandname;
    }

    /**
     * Définit le nom de la commande.
     * @param commandname Le nom de la commande à définir.
     */
    public void setCommandname(String commandname) {
        this.commandname = commandname;
    }

    /**
     * Obtient la liste des enfants du nœud.
     * @return La liste des enfants du nœud.
     */
    public List<SNodeJSONFormat> getChildren() {
        return children;
    }

    /**
     * Définit la liste des enfants du nœud.
     * @param children La liste des enfants du nœud à définir.
     */
    public void setChildren(List<SNodeJSONFormat> children) {
        this.children = children;
    }

    /**
     * Constructeur par défaut de la classe SNodeJSONFormat.
     */
    public SNodeJSONFormat() {
    }

    /**
     * Constructeur de la classe SNodeJSONFormat avec le nom de la commande.
     * @param name Le nom de la commande.
     */
    public SNodeJSONFormat(String name) {
        this.commandname = name;
    }

    /**
     * Ajoute un enfant au nœud.
     * @param name Le nom de la commande de l'enfant à ajouter.
     */
    public void addChildren(String name) {
        this.children.add(new SNodeJSONFormat(name));
    }

    /**
     * Crée un objet SNodeJSONFormat à partir d'un objet SDefaultNode.
     * @param defaultNode Le nœud SDefaultNode à convertir.
     * @return Un objet SNodeJSONFormat créé à partir du nœud SDefaultNode.
     */
    public static SNodeJSONFormat copyFromSDefaultNode(SNode defaultNode) {
        SNodeJSONFormat jsonFormatNode = new SNodeJSONFormat();
        jsonFormatNode.setCommandname(defaultNode.contents() == null ? "null" : defaultNode.contents());
        if (defaultNode.children() != null) {
            for (SNode child : defaultNode.children()) {
                if (child instanceof SDefaultNode) {
                    jsonFormatNode.getChildren().add(copyFromSDefaultNode((SDefaultNode) child));
                }
            }
        }
        return jsonFormatNode;
    }

    /**
     * Convertit l'objet SNodeJSONFormat en format JSON.
     * @return Le format JSON de l'objet SNodeJSONFormat.
     */
    public String toJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SNodeJSONFormat.class, new SNodeJSONFormatAdapter())
                .create();
        return gson.toJson(this);
    }

    /**
     * Convertit un format JSON en objet SNodeJSONFormat.
     * @param json Le format JSON à convertir.
     * @return L'objet SNodeJSONFormat converti.
     */
    public static SNodeJSONFormat fromJson(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SNodeJSONFormat.class, new SNodeJSONFormatAdapter())
                .create();
        return gson.fromJson(json, SNodeJSONFormat.class);
    }

    /**
     * Obtient une représentation textuelle des commandes des enfants du nœud.
     * @return La représentation textuelle des commandes des enfants du nœud.
     */
    public String getCommandeChildren() {
        int a = 1;
        StringBuilder res = new StringBuilder("( ");
        for (SNodeJSONFormat sNodeJSONFormat : this.getChildren()) {
            res.append((Objects.equals(sNodeJSONFormat.getCommandname(), "null")) ? "\"" + (a++) + "\" " : sNodeJSONFormat.getCommandname() + " ");
        }
        res.append(")");
        return res.toString();
    }

    /**
     * Méthode principale utilisée pour tester la classe.
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        SNode sNode = new SDefaultNode();
        SNode sNode2 = new SDefaultNode();
        sNode2.setContents("space");
        SNode sNode3 = new SDefaultNode();
        sNode3.setContents("setColor");
        SNode sNode4 = new SDefaultNode();
        sNode4.setContents("null");
        sNode.addChild(sNode2);
        sNode.addChild(sNode3);
        sNode.addChild(sNode4);
        SNodeJSONFormat sNodeJSONFormat = SNodeJSONFormat.copyFromSDefaultNode(sNode);
        System.out.println(sNodeJSONFormat.getCommandeChildren());
    }
}
