package data;

import java.io.File;
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

	protected void parseLine(String readLine) {
		if (readLine.contains(");") || readLine.contains("("))
			parseMethodLine(readLine);
		else
			parseFieldLine(readLine);
	}

	private void parseFieldLine(String readLine) {
		if (readLine.contains("}"))
			return;
		String[] tokens = constLineParser(readLine);
		ConstantInfo cons = new ConstantInfo();
		int i;
		for (i = 0; i < tokens.length - 2; i++)
			cons.keywords.add(Keyword.getEnum(tokens[i]));
		cons.type = tokens[i++];
		cons.name = tokens[i].replace(";", "");
		constants.add(cons);
	}

	private String[] constLineParser(String line) {
		String[] tokens = {};
		ArrayList<String> list = new ArrayList<String>();
		int start = 0;
		char[] chars = line.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char current = chars[i];
			switch (current) {
			case ' ':
				list.add(line.substring(start, i));
				start = i;
				break;
			case '<':
				// loop through until matching < are found
				int left = 0;
				for (; i < chars.length; i++)
					switch (current = chars[i]) {
					case '<':
						left++;
						break;
					case '>':
						left--;
						if (left == 0) {
							// found the token
							list.add(line.substring(start, i + 1));
							start = i;
						}
						break;
					default:
						break;
					}
				break;
			default:
				break;
			}
		}
		String[] cache = line.split(" ");
		list.add(cache[cache.length - 1]);
		tokens = list.toArray(tokens);
		return tokens;
	}

	@Override
	protected Keyword myType() {
		return Keyword.CLASS;
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
