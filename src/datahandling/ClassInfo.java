package datahandling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import disassembly.Driver;

public class ClassInfo {
	public static final String path = Driver.outputPath;

	private String className;
	private String javaFile;
	private List<Keyword> modifiers;
	private List<MethodInfo> methods;
	private List<ConstantInfo> constants;
	// private ClassInfo parent;
	private String parent;
	private List<String> interfaces;

	public ClassInfo(String filePath) {
		modifiers = new ArrayList<Keyword>();
		methods = new ArrayList<MethodInfo>();
		constants = new ArrayList<ConstantInfo>();
		interfaces = new ArrayList<String>();
		parse(filePath);
	}

	private void parse(String absPath) {
		try {
			BufferedReader input = new BufferedReader(new FileReader(new File(
					absPath)));
			parseJavaFile(input.readLine());
			parseClassHeader(input.readLine());
			while (input.ready()) {
				parseLine(input.readLine());
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseLine(String readLine) {
		if (readLine.contains(");"))
			parseMethodLine(readLine);
		else
			parseFieldLine(readLine);
	}

	private void parseMethodLine(String readLine) {
		String[] tokens = readLine.trim().split(" |\\(\\);");
		if (tokens.length <= 2)
			return; // TODO handle constructor prob constructor, will handle
		MethodInfo info = new MethodInfo();
		int index = 0;
		for (; index < tokens.length
				&& addKeyword(info.keyWords, tokens[index]); index++)
			;
		info.retType = tokens[index++];
		info.methodName = tokens[index].split("\\(")[0];
		tokens = readLine.trim().split(info.methodName + "|\\(|\\);");
		for (index = 2; index < tokens.length; index++) {
			info.args.add(tokens[index]);
		}
		methods.add(info);
	}

	private void parseFieldLine(String readLine) {
		// TODO Auto-generated method stub

	}

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

	private boolean addKeyword(List<Keyword> modifiers, String word) {
		try {
			Keyword key = Keyword.valueOf(word.toUpperCase());
			modifiers.add(key);
			return true;
		} catch (IllegalArgumentException e) {
			return word.equals("class");
		}
	}

	private void parseJavaFile(String readLine) {
		String[] tokens = readLine.split("\"");
		javaFile = tokens[1];
	}

	@Override
	public String toString() {
		return "ClassInfo [className=" + className + ", javaFile=" + javaFile
				+ ", modifiers=" + listToString(modifiers) + ", methods="
				+ listToString(methods) + ", constants="
				+ listToString(constants) + ", parent=" + parent
				+ ", interfaces=" + listToString(interfaces) + "]";
	}

	private <T> String listToString(List<T> list) {
		return Arrays.toString(list.toArray());
	}
}
