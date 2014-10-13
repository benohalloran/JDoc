package datahandling;

import java.io.File;

public class InterfaceInfo extends InfoObject {

	public InterfaceInfo(File file) {
		super(file);
	}

	@Override
	protected void initializeFeilds() {
		// Interface no additional fields to specify, do nothing
	}

	@Override
	protected void parseLine(String readLine) {
		parseMethodLine(readLine);
	}

	@Override
	public String toString() {
		return "Interface [className=" + className + ", javaFile=" + javaFile
				+ ", modifiers=" + listToString(modifiers) + ", methods="
				+ listToString(methods) + ", parent=" + parent
				+ ", interfaces=" + listToString(interfaces) + "]";
	}
}
