package defalt.robiproject.algo;

import java.util.List;

public class SNodeJSONFormat {

    private String commandname;

    private List<SNodeJSONFormat> children;


    public String getCommandname() {
        return commandname;
    }

    public void setCommandname(String commandname) {
        this.commandname = commandname;
    }

    public List<SNodeJSONFormat> getChildren() {
        return children;
    }

    public void setChildren(List<SNodeJSONFormat> children) {
        this.children = children;
    }
    public SNodeJSONFormat(String name){
        this.commandname=name;
    }
    public void addChildren(String name){
        this.children.add(new SNodeJSONFormat(name));
    }


}
