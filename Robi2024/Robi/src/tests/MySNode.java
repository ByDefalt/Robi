package tests;

import java.util.ArrayList;
import java.util.List;

import stree.parser.SNode;

public class MySNode implements SNode {
    private Boolean isLeaf;
    private int quote;
    private String contents;
    private List<SNode> children;
    private SNode parent;
    private Object alien;

    public MySNode(Boolean isLeaf, int quote, String contents, List<SNode> children) {
        this.isLeaf = isLeaf;
        this.quote = quote;
        this.contents = contents;
        if (children != null) {
            this.children = new ArrayList<>(children); // Crée une nouvelle liste contenant les mêmes éléments que la liste passée en paramètre
            for (SNode child : this.children) {
                child.setParent(this);
            }
        } else {
            this.children = new ArrayList<>(); // Initialise une liste vide si children est null
        }
    }


    @Override
    public Boolean isLeaf() {
        return isLeaf;
    }

    @Override
    public int quote() {
        return quote;
    }

    @Override
    public void quote(int q) {
        this.quote = q;
    }

    @Override
    public String contents() {
        return contents;
    }

    @Override
    public void setContents(String contents) {
        this.contents = contents;
    }

    @Override
    public void addToContents(Character c) {
        if (this.contents == null) {
            this.contents = "";
        }
        this.contents += c;
    }

    @Override
    public List<SNode> children() {
        if (children == null) {
            children = new ArrayList<>();
        }
        return children;
    }

    @Override
    public void setParent(SNode parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(SNode child) {
        children().add(child);
        child.setParent(this);
    }

    @Override
    public SNode parent() {
        return parent;
    }

    @Override
    public void setAlien(Object alien) {
        this.alien = alien;
    }

    @Override
    public Object alien() {
        return alien;
    }
}

