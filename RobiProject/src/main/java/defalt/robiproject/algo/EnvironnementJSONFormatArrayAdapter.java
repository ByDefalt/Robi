package defalt.robiproject.algo;

import com.google.gson.*;

import java.lang.reflect.Type;
    public class EnvironnementJSONFormatArrayAdapter implements JsonSerializer<EnvironnementJSONFormat[]>, JsonDeserializer<EnvironnementJSONFormat[]> {
        @Override
        public JsonElement serialize(EnvironnementJSONFormat[] src, Type typeOfSrc, JsonSerializationContext context) {
            JsonArray jsonArray = new JsonArray();
            for (EnvironnementJSONFormat env : src) {
                jsonArray.add(context.serialize(env));
            }
            return jsonArray;
        }

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


