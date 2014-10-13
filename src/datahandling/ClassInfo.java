package datahandling;

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
		if (readLine.contains(");"))
			parseMethodLine(readLine);
		else
			parseFieldLine(readLine);
	}

	private void parseFieldLine(String readLine) {
		// TODO Auto-generated method stub

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
