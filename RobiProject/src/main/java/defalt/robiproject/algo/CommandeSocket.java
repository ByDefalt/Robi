package defalt.robiproject.algo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Cette classe représente une commande à envoyer via un socket, avec prise en charge de la sérialisation/désérialisation JSON.
 * Elle permet de créer des commandes avec un nom, un type et un objet associé.
 * Elle offre également des méthodes pour convertir les objets CommandeSocket en format JSON et vice versa.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class CommandeSocket {
    private String name;
    private String type;
    private Object object;

    /**
     * Constructeur pour créer une commande avec un nom, un type et un objet associé.
     * 
     * @param name Le nom de la commande.
     * @param type Le type de la commande.
     * @param object L'objet associé à la commande.
     */
    public CommandeSocket(String name, String type, Object object) {
        this.name = name;
        this.type = type;
        this.object = object;
    }

    /**
     * Constructeur pour créer une commande avec seulement un nom.
     * 
     * @param name Le nom de la commande.
     */
    public CommandeSocket(String name) {
        this.name = name;
    }

    /**
     * Obtient le nom de la commande.
     * 
     * @return Le nom de la commande.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtient l'objet associé à la commande.
     * 
     * @return L'objet associé à la commande.
     */
    public Object getObject() {
        return object;
    }

    /**
     * Convertit l'objet CommandeSocket en format JSON.
     * 
     * @return La représentation JSON de la commande.
     */
    public String Commande2Json() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CommandeSocket.class, new CommandeSocketAdapter())
                .registerTypeAdapter(EnvironnementJSONFormat.class, new EnvironnementJSONFormatAdapter())
                .registerTypeAdapter(EnvironnementJSONFormat[].class, new EnvironnementJSONFormatArrayAdapter())
                .registerTypeAdapter(SNodeJSONFormat.class, new SNodeJSONFormatAdapter())
                .create();
        return gson.toJson(this);
    }

    /**
     * Convertit une chaîne JSON en objet CommandeSocket.
     * 
     * @param json La chaîne JSON à convertir.
     * @return L'objet CommandeSocket correspondant à la chaîne JSON.
     */
    public CommandeSocket Json2Commande(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CommandeSocket.class, new CommandeSocketAdapter())
                .registerTypeAdapter(EnvironnementJSONFormat.class, new EnvironnementJSONFormatAdapter())
                .registerTypeAdapter(EnvironnementJSONFormat[].class, new EnvironnementJSONFormatArrayAdapter())
                .registerTypeAdapter(SNodeJSONFormat.class, new SNodeJSONFormatAdapter())
                .create();
        return gson.fromJson(json, CommandeSocket.class);
    }

    /**
     * Obtient le type de la commande.
     * 
     * @return Le type de la commande.
     */
    public String getType() {
        return type;
    }
}
