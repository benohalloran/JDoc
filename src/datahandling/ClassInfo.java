package datahandling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassInfo extends InfoObject {
	private List<ConstantInfo> constants;

	public ClassInfo(File file) {
		super(file);
	}

	@Override
	protected void initializeFeilds() {
		constants = new ArrayList<ConstantInfo>();
	}

	protected void parse(File f) {
		try {
			BufferedReader input = new BufferedReader(new FileReader(f));
			parseJavaFile(input.readLine());
			parseClassHeader(input.readLine());
			while (input.ready()) {
				parseLine(input.readLine());
			}
			input.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
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
		tokens = readLine.trim().split("\\(");
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

	private boolean addKeyword(List<Keyword> modifiers, String word) {
		try {
			Keyword key = Keyword.getEnum(word.toUpperCase());
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
}
