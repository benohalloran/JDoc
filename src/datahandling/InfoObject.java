package datahandling;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parent for file-bound info objects, namely interface and class info objects.
 */
public abstract class InfoObject {
	protected String className; //For interfaces, interface name
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
		parse(file);
	}

	protected void addModifier(Keyword k) {
		if (contains(NON_MODIFIERS, k))
			throw new IllegalArgumentException("Keyword " + k
					+ " is not a valid modifier");
		else
			modifiers.add(k);
	}

	private <T> boolean contains(T[] arr, T key) {
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

	protected abstract void initializeFeilds();

	protected abstract void parse(File file);
}
