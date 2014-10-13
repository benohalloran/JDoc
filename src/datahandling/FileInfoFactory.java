package datahandling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Maintain a list of the different File types such that each file is parsed
 * exactly once.
 */
public class FileInfoFactory {
	private static HashMap<String, ClassInfo> classes;
	private static HashMap<String, InterfaceInfo> interfaces;

	static {
		classes = new HashMap<String, ClassInfo>();
		interfaces = new HashMap<String, InterfaceInfo>();
	}

	public static InfoObject parseFile(File file) {
		File fixed = new File(getFileNamePath(file));
		InfoObject prev = checkForExisting(fixed);
		if (prev != null)
			return prev;
		try {
			Keyword fileType = getFileType(file);
			if (fileType == Keyword.CLASS)
				return parseClass(file);
			else if (fileType == Keyword.INTERFACE)
				return parseInterface(file);
			else {
				System.err.println("Unknown file type: " + fileType);
				return null;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace(); // Should never hit this.
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static ClassInfo parseClass(File f) {
		ClassInfo info = new ClassInfo(f);
		putValue(classes, f, info);
		return info;
	}

	private static InterfaceInfo parseInterface(File f) {
		InterfaceInfo info = new InterfaceInfo(f);
		putValue(interfaces, f, info);
		return info;
	}

	private static Keyword getFileType(File f) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		reader.readLine(); // thrown out, states compiled from line
		String classInterfaceLine = reader.readLine();
		String[] tokens = classInterfaceLine.split(" ");
		Keyword ret = null;
		for (String s : tokens) {
			Keyword k = Keyword.getEnum(s);
			if (k == Keyword.CLASS || k == Keyword.INTERFACE) {
				ret = k;
				break;
			}
		}
		reader.close();
		return ret;
	}

	private static <V extends InfoObject> V getValue(HashMap<String, V> map,
			File key) {
		return map.get(getFileNamePath(key));
	}

	private static <V extends InfoObject> V putValue(HashMap<String, V> map,
			File key, V value) {
		return map.put(getFileNamePath(key), value);
	}

	private static InfoObject checkForExisting(File f) {
		InfoObject prev = getValue(classes, f);
		if (prev != null)
			return prev;
		return getValue(interfaces, f);
	}

	private static boolean haveParsed(File f) {
		return checkForExisting(f) != null;
	}

	private static String getFileNamePath(File f) {
		return f.getAbsolutePath() + "\\" + f.getName() + ".txt";
	}

	public static HashMap<String, ClassInfo> getClasses() {
		return classes;
	}

	public static HashMap<String, InterfaceInfo> getInterfaces() {
		return interfaces;
	}
}
