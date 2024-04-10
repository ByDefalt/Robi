package defalt.robiproject.algo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EnvironnementJSONFormat {
    @Override
    public String toString() {
        return "EnvironnementJSONFormat{" +
                "name='" + name + '\'' +
                ", children=" + children +
                '}';
    }

    public String name;

    public List<EnvironnementJSONFormat> children = new ArrayList<>();

    public EnvironnementJSONFormat() {
    }

    public EnvironnementJSONFormat(String name) {
        this.name = name;
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

    public void addChildren(String nameofchildren) {
        this.children.add(new EnvironnementJSONFormat(nameofchildren));
    }

    public void add(String name) {
        String[] split = name.split("\\.");
        if (split.length >= 2) {
            searchandadd(split[split.length - 2], split[split.length - 1]);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EnvironnementJSONFormat that = (EnvironnementJSONFormat) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, children);
    }

    public void searchandadd(String nameparent, String namechildren) {
        if (this.name.equals(nameparent) && this.children.contains(new EnvironnementJSONFormat(namechildren))) {
            this.addChildren(namechildren);
        }
        for (EnvironnementJSONFormat s : children) {
            s.searchandadd(nameparent, namechildren);
        }
    }
    public String toJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(EnvironnementJSONFormat.class, new EnvironnementJSONFormatAdapter())
                .create();
        return gson.toJson(this);
    }

    public static EnvironnementJSONFormat fromJson(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(EnvironnementJSONFormat.class, new EnvironnementJSONFormatAdapter())
                .create();
        return gson.fromJson(json, EnvironnementJSONFormat.class);
    }
    public static void main(String[] args) {
        EnvironnementJSONFormat space = new EnvironnementJSONFormat("space");
        space.add("space.robi");
        space.add("space.ibor");
        space.add("space.robi.jsp1");
        space.add("space.jsp1.jsp2");
        String json = space.toJson();
        System.out.println(json);
        EnvironnementJSONFormat env=EnvironnementJSONFormat.fromJson(json);
        System.out.println(env);
    }
}
