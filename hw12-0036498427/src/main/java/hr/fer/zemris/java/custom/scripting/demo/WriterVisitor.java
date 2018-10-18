package hr.fer.zemris.java.custom.scripting.demo;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

/**
 * This is the implementation of the {@link INodeVisitor}
 * which reproduces the file from the tree of nodes.
 * 
 * @author ivan
 *
 */
public class WriterVisitor implements INodeVisitor {

	@Override
	public void visitTextNode(TextNode node) {
		System.out.println(node.getText());
	}

	@Override
	public void visitForLoopNode(ForLoopNode node) {
		System.out.println(node);
		int size = node.numberOfChildren();
		for(int i = 0; i < size; i++) {
			System.out.println(node.getChild(i));
		}
	}

	@Override
	public void visitEchoNode(EchoNode node) {
		System.out.println(node);
	}

	@Override
	public void visitDocumentNode(DocumentNode node) {
		int size = node.numberOfChildren();
		for(int i = 0; i < size; i++) {
			System.out.println(node.getChild(i));
		}
	}

}
