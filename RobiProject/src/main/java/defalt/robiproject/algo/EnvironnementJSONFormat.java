package defalt.robiproject.algo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe représente un format d'environnement en JSON.
 * Elle est utilisée pour structurer les données d'environnement dans un format JSON pour le projet robiproject.
 *
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class EnvironnementJSONFormat {

    private String name;
 
    private List<EnvironnementJSONFormat> children = new ArrayList<>();

    /**
     * Constructeur par défaut.
     */
    public EnvironnementJSONFormat() {
    }

    /**
     * Constructeur avec un nom d'environnement spécifié.
     * @param name Le nom de l'environnement.
     */
    public EnvironnementJSONFormat(String name) {
        this.name = name;
    }

    /**
     * Obtient le nom de l'environnement.
     * @return Le nom de l'environnement.
     */
    public String getName() {
        return name;
    }

    /**
     * Définit le nom de l'environnement.
     * @param name Le nom de l'environnement.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtient la liste des enfants de cet environnement.
     * @return La liste des enfants de cet environnement.
     */
    public List<EnvironnementJSONFormat> getChildren() {
        return children;
    }

    /**
     * Définit la liste des enfants de cet environnement.
     * @param children La liste des enfants de cet environnement.
     */
    public void setChildren(List<EnvironnementJSONFormat> children) {
        this.children = children;
    }

    /**
     * Ajoute un enfant à cet environnement avec le nom spécifié.
     * @param nameofchildren Le nom de l'enfant à ajouter.
     */
    public void addChildren(String nameofchildren) {
        this.children.add(new EnvironnementJSONFormat(nameofchildren));
    }

    /**
     * Ajoute un environnement avec le nom spécifié en respectant la structure de point.
     * @param name Le nom de l'environnement à ajouter.
     */
    public void add(String name) {
        String[] split = name.split("\\.");
        if (split.length >= 2) {
            searchandadd(split[split.length - 2], split[split.length - 1]);
        }
    }

    /**
     * Compare cet objet avec un autre pour vérifier s'ils sont égaux.
     * @param o L'objet à comparer.
     * @return true si les objets sont égaux, sinon false.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvironnementJSONFormat that = (EnvironnementJSONFormat) o;
        return this.name.equals(that.getName());
    }

    /**
     * Parcours les enfants de cet environnement pour trouver le parent et y ajouter un enfant.
     * @param nameparent Le nom du parent à rechercher.
     * @param namechildren Le nom de l'enfant à ajouter.
     */
    public void searchandadd(String nameparent, String namechildren) {
        if (this.name.equals(nameparent) && !this.children.contains(new EnvironnementJSONFormat(namechildren))) {
            this.addChildren(namechildren);
        }
        for (EnvironnementJSONFormat s : children) {
            s.searchandadd(nameparent, namechildren);
        }
    }

    /**
     * Convertit cet objet en format JSON.
     * @return La représentation JSON de cet objet.
     */
    public String toJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(EnvironnementJSONFormat.class, new EnvironnementJSONFormatAdapter())
                .create();
        return gson.toJson(this);
    }

    /**
     * Convertit un format JSON en objet EnvironnementJSONFormat.
     * @param json Le format JSON à convertir.
     * @return L'objet EnvironnementJSONFormat correspondant au JSON.
     */
    public static EnvironnementJSONFormat fromJson(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(EnvironnementJSONFormat.class, new EnvironnementJSONFormatAdapter())
                .create();
        return gson.fromJson(json, EnvironnementJSONFormat.class);
    }

    /**
     * Méthode principale utilisée pour tester les fonctionnalités de la classe.
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
 
        EnvironnementJSONFormat space=new EnvironnementJSONFormat("space");
  
        space.add("space.robi");
        space.add("space.ibor");
        space.add("space.robi.jsp1");
        space.add("space.jsp1.jsp2");

        System.out.println(space);
  
        String json = space.toJson();
    
        System.out.println(json);
 
        EnvironnementJSONFormat env=EnvironnementJSONFormat.fromJson(json);
   
        System.out.println(env);
    }
}
