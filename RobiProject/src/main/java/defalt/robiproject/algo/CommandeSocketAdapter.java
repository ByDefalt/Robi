package defalt.robiproject.algo;

import com.google.gson.*;
import defalt.robiproject.algo.CommandeSocket;
import defalt.robiproject.algo.EnvironnementJSONFormat;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CommandeSocketAdapter implements JsonSerializer<CommandeSocket>, JsonDeserializer<CommandeSocket> {
    @Override
    public JsonElement serialize(CommandeSocket src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", src.getName());

        Object object = src.getObject();
        if (object != null) {
            if (object instanceof String || object instanceof Integer) {
                jsonObject.addProperty("object", object.toString());
            } else if (object instanceof EnvironnementJSONFormat) {
                JsonElement element = context.serialize(object);
                System.out.println(element);
                jsonObject.add("object", element);
            }if (object instanceof List<?>) {
                JsonArray environmentsArray = new JsonArray();
                for (Object env : (List<?>) object) {
                    if (env instanceof EnvironnementJSONFormat) {
                        JsonElement element = context.serialize(env);
                        environmentsArray.add(element);
                    }
                }
                jsonObject.add("object", environmentsArray);
            }
        }

        return jsonObject;
    }


    @Override
    public CommandeSocket deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String name = jsonObject.get("name").getAsString();

        Object object = null;
        if (jsonObject.has("object")) {
            JsonElement objectElement = jsonObject.get("object");
            if (objectElement.isJsonPrimitive()) {
                // Si l'élément est une valeur primitive
                object = objectElement.getAsString();
            } else if (objectElement.isJsonObject()) {
                // Si l'élément est un objet JSON (EnvironnementJSONFormat)
                object = context.deserialize(objectElement, EnvironnementJSONFormat.class);
            } else if (objectElement.isJsonArray()) {
                // Si l'élément est un tableau JSON (List<EnvironnementJSONFormat>)
                List<EnvironnementJSONFormat> environments = new ArrayList<>();
                JsonArray environmentsArray = objectElement.getAsJsonArray();
                for (JsonElement element : environmentsArray) {
                    EnvironnementJSONFormat environment = context.deserialize(element, EnvironnementJSONFormat.class);
                    environments.add(environment);
                }
                object = environments;
            }
        }

        return new CommandeSocket(name, object);
    }
}
