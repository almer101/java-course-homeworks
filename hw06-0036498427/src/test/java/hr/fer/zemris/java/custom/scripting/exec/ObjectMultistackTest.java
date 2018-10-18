package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ObjectMultistackTest {
	
	@Test
	public void pushPopPeekTest() {
		ValueWrapper carModel = new ValueWrapper(Integer.valueOf(305));
		ValueWrapper temperature = new ValueWrapper(Double.valueOf(20.5));
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push("carModel", carModel);
		multistack.push("temperature", temperature);
		
		multistack.peek("temperature").add("1.0");
		multistack.push("carModel", new ValueWrapper("105"));
		
		multistack.push("carModel", new ValueWrapper("110"));
		multistack.push("temperature", new ValueWrapper(Double.valueOf(21.0)));
		
		multistack.peek("carModel").add(null);
		assertTrue(multistack.peek("carModel").getValue().equals(110));
		assertTrue(multistack.pop("temperature").getValue().equals(21.0));
		assertTrue(multistack.peek("temperature").getValue().equals(21.5));
		
	}
	
	@Test
	public void isEmptyTest() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push("variableName", new ValueWrapper("x"));
		multistack.push("variableName", new ValueWrapper("y"));
		
		multistack.push("variableValue", new ValueWrapper(4.5));
		multistack.push("variableValue", new ValueWrapper(5.5));
		
		assertTrue(multistack.isEmpty("variable"));
		assertFalse(multistack.isEmpty("variableName"));
		assertFalse(multistack.isEmpty("variableValue"));
		
		multistack.pop("variableName");
		multistack.pop("variableName");
		assertTrue(multistack.isEmpty("variableName"));
		
		multistack.pop("variableValue");
		assertFalse(multistack.isEmpty("variableValue"));
	}
	
	@Test (expected = RuntimeException.class)
	public void pushPopPeekEmptyStackExceptionTest() {
		ObjectMultistack multistack = new ObjectMultistack();
		
		multistack.push("value", new ValueWrapper(5.6));
		multistack.push("name", new ValueWrapper("John"));
		
		multistack.peek("value").multiply(Double.valueOf(5));
		multistack.peek("value").getValue().equals(28.0);
		assertTrue(multistack.pop("name").getValue().equals("John"));
		
		multistack.pop("value");
		multistack.peek("value"); //throws an exception
	}
}
