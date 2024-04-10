package defalt.robiproject.algo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import defalt.robiproject.parser.SDefaultNode;
import defalt.robiproject.parser.SNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SNodeJSONFormat {

    private String commandname;

    private List<SNodeJSONFormat> children=new ArrayList<>();


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
    public SNodeJSONFormat(){
    }
    public SNodeJSONFormat(String name){
        this.commandname=name;
    }
    public void addChildren(String name){
        this.children.add(new SNodeJSONFormat(name));
    }
    public static SNodeJSONFormat copyFromSDefaultNode(SNode defaultNode) {
        SNodeJSONFormat jsonFormatNode = new SNodeJSONFormat();
        jsonFormatNode.setCommandname(defaultNode.contents()==null ? "null":defaultNode.contents());
        if (defaultNode.children() != null) {
            for (SNode child : defaultNode.children()) {
                if (child instanceof SDefaultNode) {
                    jsonFormatNode.getChildren().add(copyFromSDefaultNode((SDefaultNode) child));
                }
            }
        }
        return jsonFormatNode;
    }
    @Override
    public String toString() {
        return "SNodeJSONFormat{" +
                "commandname='" + commandname + '\'' +
                ", children=" + children +
                '}';
    }
    public String toJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SNodeJSONFormat.class, new SNodeJSONFormatAdapter())
                .create();
        return gson.toJson(this);
    }

    public static SNodeJSONFormat fromJson(String json) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(SNodeJSONFormat.class, new SNodeJSONFormatAdapter())
                .create();
        return gson.fromJson(json, SNodeJSONFormat.class);
    }

    public String getCommandeChildren(){
        int a=1;
        StringBuilder res= new StringBuilder("( ");
        for(SNodeJSONFormat sNodeJSONFormat : this.getChildren()){
            res.append((Objects.equals(sNodeJSONFormat.getCommandname(), "null")) ? "\""+(a++)+"\" " : sNodeJSONFormat.getCommandname() + " ");
        }
            res.append(")");
        return res.toString();
    }
    public static void main(String[] args){
        SNode sNode=new SDefaultNode();
        SNode sNode2=new SDefaultNode();
        sNode2.setContents("space");
        SNode sNode3=new SDefaultNode();
        sNode3.setContents("setColor");
        SNode sNode4=new SDefaultNode();
        sNode4.setContents("null");
        sNode.addChild(sNode2);
        sNode.addChild(sNode3);
        sNode.addChild(sNode4);
        SNodeJSONFormat sNodeJSONFormat = SNodeJSONFormat.copyFromSDefaultNode(sNode);
        System.out.println(sNodeJSONFormat.getCommandeChildren());

    }

}
