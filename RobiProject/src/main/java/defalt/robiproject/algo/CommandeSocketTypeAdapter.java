package defalt.robiproject.algo;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class CommandeSocketTypeAdapter extends TypeAdapter<CommandeSocket> {

    @Override
    public void write(JsonWriter out, CommandeSocket commandeSocket) throws IOException {
        out.beginObject();
        out.name("name").value(commandeSocket.getName());
        out.name("code").value(commandeSocket.getCode());
        out.endObject();
    }

    @Override
    public CommandeSocket read(JsonReader in) throws IOException {
        String name = null;
        String code = null;

        in.beginObject();
        while (in.hasNext()) {
            String key = in.nextName();
            if (key.equals("name")) {
                name = in.nextString();
            } else if (key.equals("code")) {
                code = in.nextString();
            } else {
                in.skipValue(); // Skip values of other keys
            }
        }
        in.endObject();

        if (name != null && code != null) {
            return new CommandeSocket(name, code);
        } else if (name != null) {
            return new CommandeSocket(name);
        } else {
            return null;
        }
    }
}

