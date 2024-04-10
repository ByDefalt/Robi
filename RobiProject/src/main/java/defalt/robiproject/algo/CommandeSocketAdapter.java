package defalt.robiproject.algo;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import defalt.robiproject.algo.CommandeSocket;
import defalt.robiproject.algo.EnvironnementJSONFormat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Cet adaptateur personnalisé permet de sérialiser et désérialiser des objets CommandeSocket en format JSON.
 * Il gère la conversion des objets CommandeSocket en JSON et vice versa, en prenant en charge les différents types de données associées.
 * 
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class CommandeSocketAdapter implements JsonSerializer<CommandeSocket>, JsonDeserializer<CommandeSocket> {

    /**
     * Sérialise un objet CommandeSocket en JSON.
     * 
     * @param src L'objet CommandeSocket à sérialiser.
     * @param typeOfSrc Le type de l'objet source.
     * @param context Le contexte de sérialisation.
     * @return Un élément JSON représentant l'objet CommandeSocket.
     */
    @Override
    public JsonElement serialize(CommandeSocket src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.getName());
        jsonObject.addProperty("type", src.getType());

        Object object = src.getObject();
        if (object != null) {
            if (object instanceof String || object instanceof Integer) {
                jsonObject.addProperty("object", object.toString());
            } else if (object instanceof SNodeJSONFormat || object instanceof List<?>) {
                JsonElement element = context.serialize(object);
                jsonObject.add("object", element);
            }
        }

        return jsonObject;
    }


    /**
     * Désérialise un élément JSON en objet CommandeSocket.
     * 
     * @param json L'élément JSON à désérialiser.
     * @param typeOfT Le type de l'objet résultant.
     * @param context Le contexte de désérialisation.
     * @return Un objet CommandeSocket résultant de la désérialisation.
     * @throws JsonParseException Si une erreur se produit lors de la désérialisation.
     */
    @Override
    public CommandeSocket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.has("name") ? jsonObject.get("name").getAsString() : null;
        String type = jsonObject.has("type") ? jsonObject.get("type").getAsString() : null;

        JsonElement objectElement = jsonObject.get("object");
        Object object = null;
        if (objectElement != null) {
            if (type != null) {
                if (type.equals("String")) {
                    object = objectElement.getAsString();
                } else if (type.equals("Integer")) {
                    object = objectElement.getAsInt();
                } else if (type.equals("SNodeJSONFormat")) {
                    object = context.deserialize(objectElement, SNodeJSONFormat.class);
                } else if (type.equals("List<EnvironnementJSONFormat>")) {
                    object = context.deserialize(objectElement, new TypeToken<List<EnvironnementJSONFormat>>(){}.getType());
                }
            }
        }

        return new CommandeSocket(name, type, object);
    }
}
