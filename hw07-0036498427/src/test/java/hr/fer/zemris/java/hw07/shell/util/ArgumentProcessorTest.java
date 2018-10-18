package hr.fer.zemris.java.hw07.shell.util;

import static org.junit.Assert.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class ArgumentProcessorTest {

	@Test
	public void singleArgumentTest() {
		String line = "/home/john/info.txt";
		String array[] = ArgumentProcessor.processArguments(line);
		
		assertEquals(1, array.length);
		assertEquals("/home/john/info.txt", array[0]);
		
		line = "/home/john/backupFolder";
		array = ArgumentProcessor.processArguments(line);
		assertEquals(1, array.length);
		assertEquals("/home/john/backupFolder", array[0]);
		
		line = "\"/home/ivan/folder\"";
		array = ArgumentProcessor.processArguments(line);
		
		line = "\"/home\\\"/auto and auto/folder\"";
		array = ArgumentProcessor.processArguments(line);
		assertEquals("/home\"/auto and auto/folder", array[0]);
		
		Path s = Paths.get("nesto2/ime2/ime3/");
		System.out.println(s.toString());
	}

	@Test
	public void multipleArgumentTest() {
		String line = "\"C:/Program Files/Program1/info.txt\" C:/tmp/informacije.txt";
		String array[] = ArgumentProcessor.processArguments(line);
		
		assertEquals("C:/Program Files/Program1/info.txt", array[0]);
		assertEquals("C:/tmp/informacije.txt", array[1]);
		
		line = "\"C:\\Documents and Settings\\Users\\javko\"  ./dir/src";
		array = ArgumentProcessor.processArguments(line);
		
		assertEquals("C:\\Documents and Settings\\Users\\javko", array[0]);
		assertEquals("./dir/src", array[1]);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void multipleArgumentWithExceptionTest() {
		String line = "C:/tmp/informacije.txt \"C:\\fi le\".txt";
		String array[] = ArgumentProcessor.processArguments(line);
	}
	
	@Test
	public void splitInputLineOneArgumentTest() {
		
		String parts[] = ArgumentProcessor.splitInputLine("command 234");
		assertEquals("command", parts[0]);
		assertEquals("234", parts[1]);
		
		parts = ArgumentProcessor.splitInputLine("");
		assertEquals("", parts[0]);
		assertEquals("", parts[1]);
		
		parts = ArgumentProcessor.splitInputLine("       help komanda      	");
		assertEquals("help", parts[0]);
		assertEquals("komanda", parts[1]);
	}
	
	@Test
	public void splitInputMoreArgumentsTest() {
		String parts[] = ArgumentProcessor.splitInputLine("	command foo key value	");
		assertEquals("command", parts[0]);
		assertEquals("foo key value", parts[1]);
		
		parts = ArgumentProcessor.splitInputLine("command  ./number/ \"./src/folder/folder2\"");
		assertEquals("command", parts[0]);
		assertEquals("./number/ \"./src/folder/folder2\"", parts[1]);
	}
	
	@Test
	public void splitInputArgumentsZeroArguments() {
		String parts[] = ArgumentProcessor.splitInputLine("	cat			");
		assertEquals("cat", parts[0]);
		assertEquals("", parts[1]);
		
		parts = ArgumentProcessor.splitInputLine(" commandd	");
		assertEquals("commandd", parts[0]);
		assertEquals("", parts[1]);
	}
}
