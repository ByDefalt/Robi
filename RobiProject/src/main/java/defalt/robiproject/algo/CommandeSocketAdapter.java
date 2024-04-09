package defalt.robiproject.algo;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public class CommandeSocketAdapter extends TypeAdapter<CommandeSocket> {
    @Override
    public void write(JsonWriter out, CommandeSocket commandeSocket) throws IOException {
        out.beginObject();
        out.name("name").value(commandeSocket.getName());
        if (commandeSocket.getObject() != null) {
            out.name("object").value(commandeSocket.getObject().toString());
        }
        out.endObject();
    }

    @Override
    public CommandeSocket read(JsonReader in) throws IOException {
        String name = null;
        Object object = null;

        in.beginObject();
        while (in.hasNext()) {
            String fieldName = in.nextName();
            if ("name".equals(fieldName)) {
                name = in.nextString();
            } else if ("object".equals(fieldName)) {
                // Note: This might need to be adapted depending on the type of objects you're dealing with
                object = in.nextString();
            } else {
                in.skipValue(); // ignore the unknown field
            }
        }
        in.endObject();

        if (object != null) {
            return new CommandeSocket(name, object);
        } else {
            return new CommandeSocket(name);
        }
    }
}
