package defalt.robiproject.algo;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EnvironnementJSONFormat extends TypeAdapter<EnvironnementJSONFormat> {

    private String name;

    private List<EnvironnementJSONFormat> children = new ArrayList<>();

    public EnvironnementJSONFormat() {

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EnvironnementJSONFormat> getChildren() {
        return children;
    }

    public void setChildren(List<EnvironnementJSONFormat> children) {
        this.children = children;
    }

    public EnvironnementJSONFormat(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "EnvironnementJSONFormat{ name=" + name + " + children=" + children + '}';

    }

    public void addChildren(String nameofchildren) {
        this.children.add(new EnvironnementJSONFormat(nameofchildren));
    }

    public void add(String name) {
        String[] split = name.split("\\.");
        if (split.length >= 2) {
            searchandadd(split[split.length - 2], split[split.length - 1]);
        }
    }

    public void searchandadd(String nameparent, String namechildren) {
        if (namechildren.equals(nameparent)) {
            this.addChildren(namechildren);
        }
        for (EnvironnementJSONFormat s : children) {
            s.searchandadd(nameparent, namechildren);
        }
    }
    @Override
    public void write(JsonWriter out, EnvironnementJSONFormat environnement) throws IOException {
        out.beginObject();
        out.name("name").value(environnement.getName());
        out.name("children");
        writeChildren(out, environnement);
        out.endObject();
    }

    private void writeChildren(JsonWriter out, EnvironnementJSONFormat environnement) throws IOException {
        out.beginArray();
        for (EnvironnementJSONFormat child : environnement.getChildren()) {
            write(out, child);
        }
        out.endArray();
    }

    @Override
    public EnvironnementJSONFormat read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        in.beginObject();
        EnvironnementJSONFormat environnement = new EnvironnementJSONFormat("");
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "name":
                    environnement.setName(in.nextString());
                    break;
                case "children":
                    in.beginArray();
                    while (in.hasNext()) {
                        EnvironnementJSONFormat child = read(in);
                        if (child != null) {
                            environnement.getChildren().add(child);
                        }
                    }
                    in.endArray();
                    break;
                default:
                    in.skipValue();
                    break;
            }
        }
        in.endObject();
        return environnement;
    }
    public static void main(String[] args) {
        EnvironnementJSONFormat space = new EnvironnementJSONFormat("space");
        space.add("space.robi");
        space.add("space.ibor");
        space.add("space.robi.jsp1");
        space.add("space.jsp1.jsp2");
        System.out.println(space);
    }
}
