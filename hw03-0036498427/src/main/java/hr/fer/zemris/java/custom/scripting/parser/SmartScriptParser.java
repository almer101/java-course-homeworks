package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexerState;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptToken;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptTokenType;
import hr.fer.zemris.java.custom.scripting.nodes.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.hw03.prob1.LexerException;

/**
 * This class is a parser which uses one lexer which
 * generates tokens. 
 * 
 * @author ivan
 *
 */
public class SmartScriptParser {
	
	/**
	 * This is this parser's lexer which
	 * generates tokens.
	 * 
	 */
	private SmartScriptLexer lexer;
	
	/**
	 * This is the root node of out tree.
	 * 
	 */
	private DocumentNode program;
	
	/**
	 * This is stack for storing the nodes,
	 * as help to be able to solve
	 * nested tags.
	 * 
	 */
	private ObjectStack stack;
	
	/**
	 * This constructor get one {@link String} 
	 * parameter <code>document</code> and 
	 * initializes the <code>lexer</code> 
	 * property with the value of 
	 * <code>document</code>
	 * 
	 * @param document
	 * 		{@link String} for initializing
	 * 		the <code>lexer</code>
	 */
	public SmartScriptParser(String document) {
		lexer = new SmartScriptLexer(document);
		program = new DocumentNode();
		
		stack = new ObjectStack();
		stack.push(program);
		program = parse();
	}

	/**
	 * This method parses the given document.
	 * 
	 * @return
	 * 		a value of the DocumentNode with
	 * 		all other nodes added to the tree.
	 */
	private DocumentNode parse() {
		
		SmartScriptToken currentToken = lexer.getToken();
		
		while(currentToken.getType() != SmartScriptTokenType.EOF) {
			
			if(currentToken.getType() == SmartScriptTokenType.TEXT) {
				//create text node and add it to the element on
				//the top of the stack
				TextNode newNode = new TextNode(
						(String)currentToken.getValue());
				Node firstElementOnStack = (Node)stack.peek();
				
				firstElementOnStack.addChildNode(newNode);
				currentToken = lexer.nextToken();
				
				continue;
			}
			
			if(currentToken.getType() == SmartScriptTokenType.OPENED_PARENTHESES) {
				//it may be the beginning of a tag 
				//if $ sign is immediately after
				currentToken = lexer.nextToken();
				
				if(currentToken.getType() != SmartScriptTokenType.DOLLAR) {
					throw new SmartScriptParserException(" Invalid tag beginning!") ;
				} 
				
				lexer.setState(SmartScriptLexerState.TAG_ENTER_STATE);
				currentToken = lexer.nextToken();
				
				continue;
			}
			
			if(currentToken.getType() == SmartScriptTokenType.KEYWORD) {
				
				try {
					parseTag();
				} catch (LexerException | SmartScriptParserException e) {
					throw new SmartScriptParserException(e.getMessage());
				}
				
				currentToken = lexer.getToken();
				continue;
			}
			
			if(currentToken.getType() == SmartScriptTokenType.DOLLAR) {
				//this may be the ending of the tag
				//if the } is immediately after the $
				currentToken = lexer.nextToken();
				
				if(currentToken.getType() != SmartScriptTokenType.CLOSED_PARENTHESES) {
					throw new SmartScriptParserException("Invalid tag ending!");
				}
				
				//if everything is fine, return lexer to TEXT state.
				lexer.setState(SmartScriptLexerState.TEXT_STATE);
				currentToken = lexer.nextToken();
				continue;
			}
			
			//if none of the above then throw exception
			throw new SmartScriptParserException("Unable to parse!! Unrecognized element"
					+ " in the document! <" + currentToken.getValue() + ">");
		}
		
		//if the stack size is not 1 there were some unclosed tags so
		//throws SmartScriptParserException
		if(stack.size() != 1) {
			throw new SmartScriptParserException("There are some unclosed tags!"
					+ " The program can not be parsed.");
		}
		
		return (DocumentNode)stack.pop();
	}

	/**
	 * This method parses FOR, END and ECHO tag,
	 * depending on the keyword this lexer is on.
	 * This method also creates and pushes new node
	 * to the stack if necessary.
	 * 
	 */
	private void parseTag() {
		SmartScriptToken currentToken = lexer.getToken();
		
		if(currentToken.getValue().equals("FOR")) {
			ForLoopNode node = parseFor();
			
			Node firstElementOnStack = (Node)stack.peek();
			
			firstElementOnStack.addChildNode(node);
			stack.push(node);
			return;
		}
		
		if(currentToken.getValue().equals("=")) {
			EchoNode node = parseEcho();

			Node firstElementOnStack = (Node)stack.peek();
			
			firstElementOnStack.addChildNode(node);
			return;
		}
		
		if(currentToken.getValue().equals("END")) {
			parseEnd();
			
			stack.pop();
			if(stack.size() == 0) {
				throw new SmartScriptParserException("No more elements "
						+ "on stack. Too many END tags!");
			}
			lexer.nextToken();
			return;
		}
		
		throw new SmartScriptParserException("Unrecognized keyword! " + currentToken.getValue());
	}

	/**
	 * This method parses a for-loop, and
	 * returns a {@link ForLoopNode} of the
	 * corresponding tag.
	 * 
	 * @return
	 * 		a {@link ForLoopNode} with all the
	 * 		values.
	 */
	private ForLoopNode parseFor() {
		
		lexer.setState(SmartScriptLexerState.FOR_STATE);
		SmartScriptToken currentToken = lexer.nextToken();
		ArrayIndexedCollection elements = new ArrayIndexedCollection();

		//first must come a variable!
		if(currentToken.getType() != SmartScriptTokenType.ID) {
			throw new SmartScriptParserException("Illegal form of "
					+ "FOR tag.");
		}
		
		
		while(currentToken.getType() != SmartScriptTokenType.DOLLAR) {
			
			if(currentToken.getType() == SmartScriptTokenType.ID) {
				elements.add(new ElementVariable(
				(String)currentToken.getValue()));
				
			} else if(currentToken.getType() == SmartScriptTokenType.NUMBER) {
				
				if(currentToken.getValue() instanceof Double) {
					elements.add(new ElementConstantDouble(
							(double)currentToken.getValue()));
					
				} else {
					elements.add(new ElementConstantInteger(
							(int)currentToken.getValue()));
				}
				
			} else if(currentToken.getType() == SmartScriptTokenType.STRING) {
				elements.add(new ElementString(
						(String)currentToken.getValue()));
				
			} else {
				throw new SmartScriptParserException("Unrecognized token"
						+ " in tag FOR");
			}
			
			currentToken = lexer.nextToken();
		}
		
		if(elements.size() >= 5 || elements.size() <= 2) {
			throw new SmartScriptParserException("Illegal number "
					+ "of arguments in for tag");
			
		}
		
		ForLoopNode newNode;
		if(elements.size() == 4) {
			newNode = new ForLoopNode(
					(ElementVariable)elements.get(0), 
					(Element)elements.get(1),
					(Element)elements.get(2),
					(Element)elements.get(3)
			);
		} else {
			newNode = new ForLoopNode(
					(ElementVariable)elements.get(0), 
					(Element)elements.get(1),
					(Element)elements.get(2),
					null
			);
		}
		
		return newNode; 
	}

	/**
	 * This method parses a echo tag, and
	 * returns a {@link EchoNode} of the
	 * corresponding tag.
	 * 
	 * @return
	 * 		a {@link EchoNode} with all the
	 * 		values.
	 */
	private EchoNode parseEcho() {
		
		lexer.setState(SmartScriptLexerState.ECHO_STATE);
		SmartScriptToken currentToken = lexer.nextToken();
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		
		while(true) {
			if(currentToken.getType() == SmartScriptTokenType.ID) {
				elements.add(new ElementVariable(
						(String) currentToken.getValue()));
				
			} else if (currentToken.getType() == SmartScriptTokenType.FUNCTION_ID) {
				elements.add(new ElementFunction(
						(String)currentToken.getValue()));
			
			} else if (currentToken.getType() == SmartScriptTokenType.STRING) {
				elements.add(new ElementString(
						(String)currentToken.getValue()));
			
			} else if (currentToken.getType() == SmartScriptTokenType.SYMBOL) {
				elements.add(new ElementOperator(
						(String)currentToken.getValue()));
			
			} else if (currentToken.getType() == SmartScriptTokenType.DOLLAR) {
				break;
				
			} else if (currentToken.getType() == SmartScriptTokenType.NUMBER) {
				
				if(currentToken.getValue() instanceof Double) {
					elements.add(new ElementConstantDouble(
							(double)currentToken.getValue()));
					
				} else {
					elements.add(new ElementConstantInteger(
							(int)currentToken.getValue()));
				}
			} else {
				throw new SmartScriptParserException("The token could not be parsed! "
						+ "-> " + currentToken.getValue());
				
			}
			currentToken = lexer.nextToken();
		}
		Element[] elems = new Element[elements.size()];
		for(int i = 0, len = elements.size(); i < len; i++) {
			elems[i] = (Element)elements.get(i);
		}
		
		return new EchoNode(elems);
	}

	/**
	 * This method parses the END tag. If the tag is invalid 
	 * it will throw an exception.
	 * 
	 * @throws SmartScriptParserException
	 * 		if the END tag is invalid.
	 * 
	 */
	private void parseEnd() {
		
		SmartScriptToken token1 = lexer.nextToken();
		SmartScriptToken token2 = lexer.nextToken();
		
		//if the end tag is not closed properly throw an exception.
		if(token1.getType() != SmartScriptTokenType.DOLLAR || 
				token2.getType() != SmartScriptTokenType.CLOSED_PARENTHESES) {
			
			throw new SmartScriptParserException("Invalid END tag!");
			
		}
		
		lexer.setState(SmartScriptLexerState.TEXT_STATE);
	}

	/**
	 * This method returns the value of the
	 * <code>program</code> property.
	 * 
	 * @return
	 * 		the value of the <code>program</code>
	 * 		property.
	 */
	public DocumentNode getDocumentNode() {
		return program;
	}
	
	/**
	 * This method returns a string representation of the
	 * tree of {@link Node}s. The algorithm is recursive.
	 * 
	 * @param document
	 * 		root {@link Node} of the document
	 * @return
	 * 		a {@link String} representation of the given tree.
	 */
	public static String createOriginalDocumentBody(Node document) {
		if(document == null) {
			return null;
		}
		
		String result = document.toString();
		int size = document.numberOfChildren();
		
		for(int i = 0; i < size; i++) {
			String s = createOriginalDocumentBody(document.getChild(i));
			result += s;
		}
		
		if(document instanceof ForLoopNode) {
			return result + "{$END$}";
		}
		
		return result;
	}
}
