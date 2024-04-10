package defalt.robiproject.algo;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Cette classe est un adaptateur pour la sérialisation et la désérialisation d'objets SNodeJSONFormat en JSON.
 *
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class SNodeJSONFormatAdapter implements JsonSerializer<SNodeJSONFormat>, JsonDeserializer<SNodeJSONFormat> {

    /**
     * Convertit un objet SNodeJSONFormat en élément JSON.
     * @param src L'objet SNodeJSONFormat à convertir.
     * @param typeOfSrc Le type de l'objet source.
     * @param context Le contexte de sérialisation.
     * @return L'élément JSON correspondant à l'objet SNodeJSONFormat.
     */
    @Override
    public JsonElement serialize(SNodeJSONFormat src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("commandname", src.getCommandname());
        JsonArray childrenArray = new JsonArray();
        for (SNodeJSONFormat child : src.getChildren()) {
            childrenArray.add(serialize(child, typeOfSrc, context));
        }
        jsonObject.add("children", childrenArray);
        return jsonObject;
    }

    /**
     * Convertit un élément JSON en objet SNodeJSONFormat.
     * @param json L'élément JSON à convertir.
     * @param typeOfT Le type de l'objet cible.
     * @param context Le contexte de désérialisation.
     * @return L'objet SNodeJSONFormat correspondant à l'élément JSON.
     * @throws JsonParseException Si une erreur de désérialisation se produit.
     */
    @Override
    public SNodeJSONFormat deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String commandName = jsonObject.get("commandname").getAsString();
        SNodeJSONFormat node = new SNodeJSONFormat(commandName);
        JsonArray childrenArray = jsonObject.getAsJsonArray("children");
        for (JsonElement childElement : childrenArray) {
            SNodeJSONFormat childNode = deserialize(childElement, typeOfT, context);
            node.getChildren().add(childNode);
        }
        return node;
    }
}
