package defalt.robiproject.algo;

import java.util.List;

public class EnvironnementJSONFormat {

    private String name;

    private List<EnvironnementJSONFormat> children;


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
    public void searchandadd(String name){
        String[] split=name.split(".");
        if(this.name==split[split.length-2]){
            this.addChildren(split[split.length-1]);
        }
        for(EnvironnementJSONFormat s : children){
            s.searchandadd(name);
        }
    }
}
