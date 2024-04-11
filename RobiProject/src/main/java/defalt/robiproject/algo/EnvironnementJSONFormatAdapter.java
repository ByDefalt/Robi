package defalt.robiproject.algo;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Cet adaptateur GSON est utilisé pour sérialiser et désérialiser des objets EnvironnementJSONFormat.
 * Il est utilisé pour convertir des objets EnvironnementJSONFormat en format JSON et vice versa.
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime
 */
public class EnvironnementJSONFormatAdapter implements JsonSerializer<EnvironnementJSONFormat>, JsonDeserializer<EnvironnementJSONFormat> {

    /**
     * Méthode pour sérialiser un objet EnvironnementJSONFormat en format JSON.
     * @param src L'objet EnvironnementJSONFormat à sérialiser.
     * @param typeOfSrc Le type de l'objet source.
     * @param context Le contexte de la sérialisation JSON.
     * @return Un élément JSON représentant l'objet EnvironnementJSONFormat.
     */
    @Override
    public JsonElement serialize(EnvironnementJSONFormat src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.getName());
        JsonArray childrenArray = new JsonArray();
        for (EnvironnementJSONFormat child : src.getChildren()) {
            childrenArray.add(serialize(child, typeOfSrc, context));
        }
        jsonObject.add("children", childrenArray);
        return jsonObject;
    }

    /**
     * Méthode pour désérialiser un objet EnvironnementJSONFormat à partir du format JSON.
     * @param json Le format JSON à désérialiser.
     * @param typeOfT Le type de l'objet à désérialiser.
     * @param context Le contexte de la désérialisation JSON.
     * @return L'objet EnvironnementJSONFormat désérialisé.
     * @throws JsonParseException Si une erreur se produit lors de la désérialisation JSON.
     */
    @Override
    public EnvironnementJSONFormat deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();
        EnvironnementJSONFormat environment = new EnvironnementJSONFormat(name);
        JsonArray childrenArray = jsonObject.getAsJsonArray("children");
        for (JsonElement childElement : childrenArray) {
            EnvironnementJSONFormat child = context.deserialize(childElement, EnvironnementJSONFormat.class);
            environment.getChildren().add(child);
        }
        return environment;
    }
}
