package hr.fer.zemris.java.hw07.shell.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw07.shell.Environment;
import hr.fer.zemris.java.hw07.shell.NameBuilder;
import hr.fer.zemris.java.hw07.shell.NameBuilderInfo;
import hr.fer.zemris.java.hw07.shell.builders.NameBuilderInfoImpl;
import hr.fer.zemris.java.hw07.shell.parser.NameBuilderParser;
import hr.fer.zemris.java.hw07.shell.parser.NameBuilderParserException;

/**
 * This class offers static utility methods for
 * filtering, grouping, generating new names of files,
 * moving/renaming of files. 
 * 
 * @author ivan
 *
 */
public class FilesProcessor {

	//==========================Properties====================================
	
	/**
	 * The environment where to write
	 */
	private Environment env;
	
	/**
	 * The mask for choosing the valid files.
	 */
	private String mask;
	
	/**
	 * The naming scheme.
	 */
	private String expression;
	
	/**
	 * The source directory of the files to move.
	 */
	private Path source;
	
	/**
	 * The destination directory to move files to.
	 */
	private Path dest;

	//==========================Constructors==================================
	
	/**
	 * This constructor gets all parameters which are necessary for
	 * moving or renaming the files.
	 * 
	 * @param env
	 * 		the environment where to write.
	 * 
	 * @param mask
	 * 		the mask to choose the files which are to be processed.
	 * 
	 * @param expression
	 * 		the scheme for renaming the files.(i.e. generating
	 * 		new names.)
	 * 
	 * @param source
	 * 		the source directory.
	 * 
	 * @param dest
	 * 		the destination directory.
	 */
	public FilesProcessor(Environment env, String mask, String expression, 
			Path source, Path dest) {
		super();
		this.env = Objects.requireNonNull(env);
		this.mask = Objects.requireNonNull(mask);
		this.expression = expression;
		this.source = source;
		this.dest = dest;
	}
	
	/**
	 * This constructor gets all parameters necessary for filtering
	 * or grouping the files.
	 * 
	 * @param env
	 * 		the environment where to write.
	 * 
	 * @param mask
	 * 		the mask to choose the files which are to be processed.
	 * 
	 * @param source
	 * 		the source directory.
	 * 
	 */
	public FilesProcessor(Environment env, String mask, Path source) {
		this(env, mask, null, source, null);
	}
	
	//=========================Method implementations=========================
	
	/**
	 * This method filters the files from the source directory
	 * using the mask specified in the constructor.
	 * 
	 * @return
	 * 		the List of file names which match the mask.
	 * 
	 * @throws IOException
	 * 		if an error occurs during list the files.
	 */
	public List<String> select() throws IOException {
		Pattern pattern = Pattern.compile(mask, 
						Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		
		return Files.list(source)
				.filter(p -> pattern.matcher(p.getFileName().toString()).matches())
				.map(p -> p.getFileName().toString())
				.collect(Collectors.toList());
	}
	
	/**
	 * This method groups file names by the mask specified in the constructor
	 * and writes out groups for each file to the console.
	 * 
	 * @throws IOException
	 * 		if an error occurs during listing the files.
	 * 
	 * @throws PatternSyntaxException
	 * 		if the mask is invalid.
	 * 
	 */
	public void groupFileNames() throws IOException, PatternSyntaxException{
		List<Path> files = Files.list(source).collect(Collectors.toList());
		Pattern pattern = Pattern.compile(mask, 
				Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		
		for(Path file : files) {
			String name = file.getFileName().toString();
			Matcher m = pattern.matcher(name);
			if(!m.matches()) continue;
			env.write(name + " ");
			int count = m.groupCount();
			if(m.matches()) {
				for(int i = 0; i < count + 1; i++) {
					env.write(i + ": " + m.group(i) + " ");
				}
			}
			//go to the next row
			env.writeln("");
		}
	}
	
	/**
	 * This method generates new names for files which match
	 * the mask and writes to the console: oldName => newName.
	 * The name is generated using the scheme given by the
	 * <code>expression.</code>
	 * 
	 * @throws IOException
	 * 		if listing the files fails.
	 * 
	 * @throws PatternSyntaxException
	 * 		if the mask is invalid.
	 */
	public void writeNames() throws IOException, PatternSyntaxException {
		List<Path> files = Files.list(source).collect(Collectors.toList());
		//if the parsing fails the getNameBuilderParser() will
		//throw an exception
		NameBuilderParser parser = getNameBuilderParser();
		NameBuilder builder = parser.getNameBuilder();
		Pattern pattern = Pattern.compile(mask);
		
		for(Path file : files) {
			execute(file, pattern, builder);
		}
	}

	/**
	 * This method returns a new {@link NameBuilderParser}
	 * object or throws an exception if the parsing failed.
	 * 
	 * @return
	 * 		the new {@link NameBuilderParser} object.
	 * 
	 * @throws NameBuilderParserException
	 * 		if the parsing failed.
	 */
	private NameBuilderParser getNameBuilderParser() throws 
										NameBuilderParserException{
		return new NameBuilderParser(expression);
	}

	/**
	 * This method writes old and new name for the specified
	 * file if the file matches the pattern given by the parameter.
	 * The method returns the new name of the file. If the
	 * file does not match the mask then returns null.
	 * 
	 * @param file
	 * 		the file to generate a new name for.
	 * 
	 * @param pattern
	 * 		pattern to match.
	 * 
	 * @param builder
	 * 		a builder which knows how to build a name
	 * 		for the file.
	 * 
	 * @return
	 * 		a new name of the file.
	 */
	private String execute(Path file, Pattern pattern, NameBuilder builder) {
		Matcher matcher = pattern.matcher(file.getFileName().toString());
		if(!matcher.matches()) return null;
		if(Files.isDirectory(file)) return null;
		NameBuilderInfo info = new NameBuilderInfoImpl(matcher);
		builder.execute(info);
		String newName = info.getStringBuilder().toString();
		env.writeln(file.getFileName().toString() + " => " + newName);
		return newName;
	}
	
	/**
	 * This method writes old and newly generated names to
	 * the console and moves/renames files to the desired location. 
	 * The destination location is the property of this class.
	 * The files which have the name that matches the mask, 
	 * only they should be moved/renamed.
	 * The scheme of renaming is given by the property. 
	 * <code>expression</code>
	 * 
	 * @throws IOException
	 * 		if the listing of the files fails.
	 * 
	 * @throws PatternSyntaxException
	 * 		if the mask is invalid.
	 */
	public void moveFiles() throws IOException, PatternSyntaxException{
		List<Path> files = Files.list(source).collect(Collectors.toList());
		NameBuilderParser parser = getNameBuilderParser();
		NameBuilder builder = parser.getNameBuilder();
		Pattern pattern = Pattern.compile(mask, 
				Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
		
		for(Path file : files) {
			String newName = execute(file, pattern, builder);
			if(newName == null) continue;
			if(source.equals(dest)) {
				Files.move(file, file.resolveSibling(newName));
			} else {
				Files.move(file, Paths.get(dest + "/" + newName), 
						StandardCopyOption.REPLACE_EXISTING);
			}
		}
	}
}
