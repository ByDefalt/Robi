package defalt.robiproject.algo;

import java.util.ArrayList;
import java.util.List;

public class EnvironnementJSONFormat {

    private String name;

    private List<EnvironnementJSONFormat> children=new ArrayList<>();


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
    public EnvironnementJSONFormat(String name){
        this.name=name;
    }
    public void addChildren(String nameofchildren){
        this.children.add(new EnvironnementJSONFormat(nameofchildren));
    }
    public void search(String name){
        String[] split=name.split(".");
        //searchandadd(split[split.length-2],split[split.length-1]);
    }
    public void searchandadd(String nameparent,String namechildren){
        if(this.name==nameparent){
            this.addChildren(namechildren);
        }
        for(EnvironnementJSONFormat s : children){
            s.searchandadd(nameparent,namechildren);
        }
    }
    public static void main(String[] args) {
        EnvironnementJSONFormat space=new EnvironnementJSONFormat("space");
        space.search("space.robi");
        space.getChildren().toString();
    }
}
