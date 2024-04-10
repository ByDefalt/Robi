package defalt.robiproject.algo;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
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
