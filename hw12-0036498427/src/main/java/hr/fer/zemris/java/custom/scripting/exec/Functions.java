package hr.fer.zemris.java.custom.scripting.exec;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * This is the class which offers the variety of functions
 * which are applied to the elements of the stack which 
 * is given in the constructor.
 * 
 * @author ivan
 *
 */
public class Functions {

	//============================Properties==============================
	
	/**
	 * The stacks where the parameters for the
	 * function are.
	 */
	private Stack<Object> stack;
	
	/**
	 * The context which manages the parameters and the
	 * cookies.
	 */
	private RequestContext requestContext;
	
	/**
	 * The map mapping the function name to the function.
	 */
	private Map<String, Function> functions = new HashMap<>(); 
	
	//============================Constructor=============================
	
	/**
	 * This is the default constructor which gets the 
	 * stack with the parameters as a parameter.
	 * 
	 * @param stack
	 * 		the stack with the parameters.
	 * @param requestContext 
	 * 		the context which manages the parameters and
	 * 		the cookies.
	 */
	public Functions(Stack<Object> stack, 
			RequestContext requestContext) {
		this.stack = Objects.requireNonNull(stack);
		this.requestContext = Objects.requireNonNull(requestContext);
		initializeMap();
	}

	//================================Setters============================
	
	/**
	 * This method sets the value of the {@link #stack} to the
	 * specified value.
	 * 
	 * @param stack
	 * 		the new value for the stack.
	 */
	public void setStack(Stack<Object> stack) {
		this.stack = stack;
	}
	
	/**
	 * This method sets the value of the {@link #requestContext}
	 * to the specified value.
	 * 
	 * @param requestContext
	 * 		the new value of the {@link #requestContext}.
	 */
	public void setRequestContext(RequestContext requestContext) {
		this.requestContext = requestContext;
	}
	
	//=========================Method implementations=====================
	
	/**
	 * This method returns the function for the 
	 * specified name if the function exists and 
	 * <code>null</code> if it does not exist.
	 * 
	 * @param name the name of the function.
	 * @return
	 * 		the function for the specified name
	 * 		or <code>null</code> if no such
	 * 		function exists.
	 */
	public Function getForName(String name) {
		return functions.get(name);
	}
	
	/**
	 * This method initializes the map of the functions with
	 * functions and their names.
	 */
	private void initializeMap() {
		functions.put("sin", new Function() {
			@Override
			public void apply() {
				double x = getNumber(stack).doubleValue();
				x *= Math.PI / 180;
				stack.push(Math.sin(x));
			}
		});
		
		functions.put("decfmt", new Function() {
			@Override
			public void apply() {
				String pattern = (String)stack.pop();
				Number n = getNumber(stack);
				DecimalFormat formatter = new DecimalFormat(pattern);
				String result = formatter.format(n);
				stack.push(result);
			}
		});
		
		functions.put("dup", new Function() {
			@Override
			public void apply() {
				Object o = stack.pop();
				stack.push(o);
				stack.push(o);
			}
		});
		
		functions.put("swap", new Function() {
			@Override
			public void apply() {
				Object first = stack.pop();
				Object second = stack.pop();
				stack.push(second);
				stack.push(first);
			}
		});
		
		functions.put("setMimeType", new Function() {
			@Override
			public void apply() {
				String type = (String)stack.pop();
				requestContext.setMimeType(type);
			}
		});
		
		functions.put("paramGet", new Function() {
			@Override
			public void apply() {
				Integer defValue = (Integer)stack.pop();
				String name = (String)stack.pop();
				String value = requestContext.getParameter(name);
				stack.push(value == null ? defValue : value);
			}
		});
		
		functions.put("pparamGet", new Function() {
			@Override
			public void apply() {
				String defValue = (String)stack.pop();
				String name = (String)stack.pop();
				String value = requestContext.getPersistentParameter(name);
				stack.push(value == null ? defValue : value);
			}
		});
		
		functions.put("pparamSet", new Function() {
			@Override
			public void apply() {
				String name = (String)stack.pop();
				String value = getNumber(stack).toString();
				requestContext.setPersistentParameter(name, value);
			}
		});
		
		functions.put("pparamDel", new Function() {
			@Override
			public void apply() {
				String name = (String)stack.pop();
				requestContext.removePersistentParameter(name);
			}
		});
		
		functions.put("tparamGet", new Function() {
			@Override
			public void apply() {
				String defValue = (String)stack.pop();
				String name = (String)stack.pop();
				String value = requestContext.getTemporaryParameter(name);
				stack.push(value == null ? defValue : value);
			}
		});
		
		functions.put("tparamSet", new Function() {
			@Override
			public void apply() {
				String name = (String)stack.pop();
				String value = getNumber(stack).toString();
				requestContext.setTemporaryParameter(name, value);
			}
		});
		
		functions.put("tparamDel", new Function() {
			@Override
			public void apply() {
				String name = (String)stack.pop();
				requestContext.removeTemporaryParameter(name);
			}
		});
	}
	
	/**
	 * This method returns the number, either double or
	 * integer from the top of the stack. If the number is
	 * of type string then it is parsed and the result is
	 * returned.
	 * 
	 * @param stack
	 * 		the stack where the number is
	 * @return
	 * 		the number from the top of the stack.
	 */
	private Number getNumber(Stack<Object> stack) {
		Object o = stack.pop();
		if(o instanceof Integer || o instanceof Double) {
			return (Number)o;
		}
		else if(o instanceof String) {
			String s = (String)o;
			if(s.contains(".")) {
				return Double.parseDouble(s);
			} else {
				return Integer.parseInt(s);
			}
		}
		throw new IllegalArgumentException("The number could not"
				+ "be recognized.");
	}
	
	/**
	 * This is the private class which represents one function.
	 * Each function has number of parameters and an apply method
	 * which applies the function.
	 * 
	 * @author ivan
	 *
	 */
	public abstract static class Function {
		
		/**
		 * The method which applies the function.
		 */
		public abstract void apply();

	}
}
