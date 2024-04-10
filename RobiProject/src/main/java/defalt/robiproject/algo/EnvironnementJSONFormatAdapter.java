package defalt.robiproject.algo;

import com.google.gson.*;

import java.lang.reflect.Type;

public class EnvironnementJSONFormatAdapter implements JsonSerializer<EnvironnementJSONFormat>,JsonDeserializer<EnvironnementJSONFormat> {
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
