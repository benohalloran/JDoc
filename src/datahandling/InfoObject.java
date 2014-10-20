package datahandling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parent for file-bound info objects, namely interface and class info objects.
 */
public abstract class InfoObject {
	protected String className; // For interfaces, interface name
	protected String javaFile;
	protected List<Keyword> modifiers;
	protected List<MethodInfo> methods;

	// private ClassInfo parent;
	protected String parent;
	protected List<String> interfaces;

	private static final Keyword[] NON_MODIFIERS = { Keyword.CLASS,
			Keyword.INTERFACE };

	public InfoObject(File file) {
		initializeFeilds();
		modifiers = new ArrayList<Keyword>();
		methods = new ArrayList<MethodInfo>();
		interfaces = new ArrayList<String>();
		try {
			parse(new BufferedReader(new FileReader(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void parse(BufferedReader input) {
		// parse the header lines, which are common to both Class and Interface
		try {
			//TODO parse constructor lines
			parseJavaFile(input.readLine());
			parseClassHeader(input.readLine());
			while (input.ready()) {
				parseLine(input.readLine().trim()); //These are only method and field lines, enum values
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Parsing for content lines. Type-specific
	 */
	protected abstract void parseLine(String readLine);

	protected abstract void initializeFeilds();

	//Get the type represented by this class. This is used so keywords like class and interface aren't listed as modifiers.
	protected abstract Keyword myType();

	private void parseJavaFile(String readLine) {
		String[] tokens = readLine.split("\"");
		javaFile = tokens[1];
	}

	protected boolean addKeyword(List<Keyword> modifiers, String word) {
		try {
			Keyword key = Keyword.getEnum(word.toUpperCase());
			if (key != myType() || modifiers != this.modifiers)
				modifiers.add(key);
			return true;
		} catch (IllegalArgumentException e) {
			return word.equals("class");
		}
	}
	
	// This might/probably have a bug when interfaces extend interfaces.
	private void parseClassHeader(String readLine) {
		boolean seenExtends = false;
		boolean seenImplements = false;
		String[] words = readLine.split(" ");
		for (String word : words) {
			if (!addKeyword(modifiers, word)) {
				if (className == null)
					className = word;
				else if (word.equals("extends"))
					seenExtends = true;
				else if (word.equals("implements"))
					if (seenImplements)
						throw new IllegalStateException(
								"Found implements twice in file");
					else
						seenImplements = true;
				else {
					if (seenExtends)
						parent = word;
					else
						interfaces.add(word);
				}
			}
		}
		if (parent == null)
			parent = "java.lang.Object";
	}

	protected final void parseMethodLine(String Line) {
		String[] tokens = Line.trim().split(" |\\(\\);");
		if (tokens.length <= 2)
			return; // TODO handle constructor prob constructor, will handle
		MethodInfo info = new MethodInfo();
		int index = 0;
		for (; index < tokens.length
				&& addKeyword(info.keyWords, tokens[index]); index++)
			;
		info.retType = tokens[index++];
		info.methodName = tokens[index].split("\\(")[0];
		tokens = Line.trim().split("\\(");
		for (index = 2; index < tokens.length; index++) {
			info.args.add(tokens[index]);
		}
		methods.add(info);
	}

	protected <T> boolean contains(T[] arr, T key) {
		for (T t : arr)
			if (t.equals(key))
				return true;
		return false;
	}

	protected <T> String listToString(List<T> list) {
		return Arrays.toString(list.toArray());
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getJavaFile() {
		return javaFile;
	}

	public void setJavaFile(String javaFile) {
		this.javaFile = javaFile;
	}

	public List<Keyword> getModifiers() {
		return modifiers;
	}

	public void setModifiers(List<Keyword> modifiers) {
		this.modifiers = modifiers;
	}

	public List<MethodInfo> getMethods() {
		return methods;
	}

	public void setMethods(List<MethodInfo> methods) {
		this.methods = methods;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public List<String> getInterfaces() {
		return interfaces;
	}

	public void setInterfaces(List<String> interfaces) {
		this.interfaces = interfaces;
	}
}
