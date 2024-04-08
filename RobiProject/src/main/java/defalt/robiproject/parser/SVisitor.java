package defalt.robiproject.parser;

public interface SVisitor {
	public void visitNode(SNode node);
	public void visitLeaf(SNode node);
}
