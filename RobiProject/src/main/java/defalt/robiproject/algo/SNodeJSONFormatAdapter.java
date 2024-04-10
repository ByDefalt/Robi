package defalt.robiproject.algo;

import com.google.gson.*;

import java.lang.reflect.Type;

public class SNodeJSONFormatAdapter implements JsonSerializer<SNodeJSONFormat>, JsonDeserializer<SNodeJSONFormat> {
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
