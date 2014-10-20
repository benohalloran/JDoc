package data;

import java.util.ArrayList;
import java.util.List;

public class MethodInfo {
	public ClassInfo declared;
	public ClassInfo implemented;
	public String methodName;
	public String header;
	public List<Keyword> keyWords;
	public List<String> args;
	public String retType;

	public MethodInfo() {
		keyWords = new ArrayList<Keyword>();
		args = new ArrayList<String>();
	}

	@Override
	public String toString() {
		return "MethodInfo [methodName=" + methodName + ", keyWords="
				+ keyWords + ", args=" + args + ", retType=" + retType + "]";
	}

}
