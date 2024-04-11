package defalt.robiproject.algo;

import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * Cet adaptateur GSON est utilisé pour sérialiser et désérialiser un tableau d'objets EnvironnementJSONFormat.
 * Il est utilisé pour convertir un tableau d'objets EnvironnementJSONFormat en format JSON et vice versa.
 * @author LE BRAS Erwan
 * @author ROUSVAL Romain
 * @author NICOLAS Pierre
 * @author KERVRAN Maxime 
 */
public class EnvironnementJSONFormatArrayAdapter implements JsonSerializer<EnvironnementJSONFormat[]>, JsonDeserializer<EnvironnementJSONFormat[]> {

    /**
     * Méthode pour sérialiser un tableau d'objets EnvironnementJSONFormat en format JSON.
     * @param src Le tableau d'objets EnvironnementJSONFormat à sérialiser.
     * @param typeOfSrc Le type de l'objet source.
     * @param context Le contexte de la sérialisation JSON.
     * @return Un élément JSON représentant le tableau d'objets EnvironnementJSONFormat.
     */
    @Override
    public JsonElement serialize(EnvironnementJSONFormat[] src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray jsonArray = new JsonArray();
        for (EnvironnementJSONFormat env : src) {
            jsonArray.add(context.serialize(env));
        }
        return jsonArray;
    }

    /**
     * Méthode pour désérialiser un tableau d'objets EnvironnementJSONFormat à partir du format JSON.
     * @param json Le format JSON à désérialiser.
     * @param typeOfT Le type de l'objet à désérialiser.
     * @param context Le contexte de la désérialisation JSON.
     * @return Le tableau d'objets EnvironnementJSONFormat désérialisé.
     * @throws JsonParseException Si une erreur se produit lors de la désérialisation JSON.
     */
    @Override
    public EnvironnementJSONFormat[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonArray = json.getAsJsonArray();
        EnvironnementJSONFormat[] environments = new EnvironnementJSONFormat[jsonArray.size()];
        for (int i = 0; i < jsonArray.size(); i++) {
            JsonElement element = jsonArray.get(i);
            EnvironnementJSONFormat environment = context.deserialize(element, EnvironnementJSONFormat.class);
            environments[i] = environment;
        }
        return environments;
    }
}
