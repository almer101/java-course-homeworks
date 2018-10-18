package hr.fer.zemris.java.custom.scripting.parser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SmartScriptParserTest {

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
		try(InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) { 
			byte[] buffer = new byte[1024];
			while(true) {
				int read = is.read(buffer); if(read<1) break; 
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8); 
		} catch(IOException ex) {
			return null; 
		}
	}
	
	@Test
	public void testingTreeComponents() {
		String document = loader("document1.txt");
		//System.out.println(document);
		
		SmartScriptParser parser = null;
		DocumentNode rootNode = new DocumentNode();
		
		try {
			parser = new SmartScriptParser(document);
		} catch (SmartScriptParserException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		rootNode = parser.getDocumentNode();
		assertTrue(rootNode.getChild(1) instanceof ForLoopNode);
		assertTrue(rootNode.getChild(1).getChild(0) instanceof TextNode);
		assertEquals("var", ((ForLoopNode)rootNode.getChild(1)).getVariable().asText());
		assertEquals("5.0", ((ForLoopNode)rootNode.getChild(1)).getEndExpression().asText());
		assertTrue(((ForLoopNode)rootNode.getChild(1)).getStepExpression() == null);
		
		assertEquals(4, rootNode.numberOfChildren());
		assertEquals(6, rootNode.getChild(1).numberOfChildren());
	}
	
	@Test
	public void testingTreeComponents2() {
		String document = loader("document2.txt");
		
		SmartScriptParser parser = null;
		DocumentNode rootNode = new DocumentNode();
		
		try {
			parser = new SmartScriptParser(document);
		} catch (SmartScriptParserException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		
		rootNode = parser.getDocumentNode();
		
		assertTrue(rootNode.getChild(0) instanceof TextNode);
		assertTrue(rootNode.getChild(1) instanceof EchoNode);
		assertTrue(rootNode.getChild(2) instanceof TextNode);
		assertTrue(rootNode.getChild(3) instanceof ForLoopNode);
		
		assertTrue(rootNode.getChild(3).getChild(2) instanceof EchoNode);
		assertTrue(rootNode.getChild(3).getChild(4) instanceof ForLoopNode);
		assertTrue(rootNode.getChild(3).getChild(4).getChild(0) instanceof TextNode);
		
		assertTrue(rootNode.getChild(4) instanceof TextNode);
	}
	
	@Test
	public void testingTreeComponents3() {
		String document = loader("document3.txt");
		
		SmartScriptParser parser = null;
		DocumentNode rootNode = new DocumentNode();
		
		try {
			parser = new SmartScriptParser(document);
		} catch (SmartScriptParserException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}
		
		rootNode = parser.getDocumentNode();
		assertTrue(rootNode.numberOfChildren() == 2);
		
		assertTrue(rootNode.getChild(0) instanceof TextNode);
		assertTrue(rootNode.getChild(1) instanceof ForLoopNode);
		
		assertTrue(rootNode.getChild(1).getChild(0) instanceof TextNode);
		assertTrue(rootNode.getChild(1).getChild(1) instanceof EchoNode);
		assertTrue(rootNode.getChild(1).getChild(2) instanceof TextNode);
		assertTrue(rootNode.getChild(1).getChild(3) instanceof EchoNode);
		assertTrue(rootNode.getChild(1).getChild(4) instanceof TextNode);
	}
}
