package defalt.robiproject.algo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class CommandeSocket {
    private String name;
    private Object object;
    public  CommandeSocket(String name,Object object){
        this.name=name;
        this.object=object;
    }
    public  CommandeSocket(String name){
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public Object getObject() {
        return object;
    }
    public String Commande2Json(){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CommandeSocket.class, new CommandeSocketAdapter()) // Enregistrer l'adaptateur de type
                .registerTypeAdapter(EnvironnementJSONFormat.class, new EnvironnementJSONFormatAdapter())
                .registerTypeAdapter(EnvironnementJSONFormat[].class, new EnvironnementJSONFormatArrayAdapter())
                .create();
        return gson.toJson(this);
    }
    public CommandeSocket Json2Commande(String json){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(CommandeSocket.class, new CommandeSocketAdapter())
                .registerTypeAdapter(EnvironnementJSONFormat.class, new EnvironnementJSONFormatAdapter())
                .registerTypeAdapter(EnvironnementJSONFormat[].class, new EnvironnementJSONFormatArrayAdapter())
                .create();
        return gson.fromJson(json, CommandeSocket.class);
    }
}
