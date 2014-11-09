package data;

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
	private static int count = 0;

	private static final String defaultDir = "C:\\Users\\Ben\\School\\CSC 200\\Honors Project\\InfoParser\\output";

	static {
		classes = new HashMap<String, ClassInfo>();
		interfaces = new HashMap<String, InterfaceInfo>();
	}

	public static int parseDirectory() {
		return parseDirectory(defaultDir);
	}

	public static int parseDirectory(String path) {
		return parseDirectory(new File(path));
	}

	public static int parseDirectory(File dir) {
		count = 0;
		recurseFile(dir);
		return count;
	}

	private static void recurseFile(File root) {
		File[] children = root.listFiles();
		if (children == null)
			return;
		for (File cur : children)
			if (cur.isDirectory())
				recurseFile(cur);
			else if (cur.exists()) {
				count++;
				parseFile(cur);
			}
	}

	public static InfoObject parseFile(File file) {
		File fixed = new File(getFileNamePath(file));
		InfoObject prev = checkForExisting(fixed);
		if (prev != null)
			return prev;
		try {
			Keyword fileType = getFileType(file);
			switch (fileType) {
			case CLASS:
				return parseClass(file);
			case INTERFACE:
				return parseInterface(file);
			default:
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
		String declarationLine = reader.readLine();
		String[] tokens = declarationLine.split(" ");
		for (String s : tokens) {
			Keyword k = Keyword.getEnum(s);
			if (k == Keyword.CLASS || k == Keyword.INTERFACE) {
				reader.close();
				return k;
			}
		}
		reader.close();
		throw new IllegalArgumentException(
				"Unknown File type. Declaration line " + declarationLine);
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
		return f.getAbsolutePath();
	}

	public static HashMap<String, ClassInfo> getClasses() {
		return classes;
	}

	public static HashMap<String, InterfaceInfo> getInterfaces() {
		return interfaces;
	}

	private static <V extends InfoObject> V getValue(HashMap<String, V> map,
			File keyFile) {
		String key = getFileObject(keyFile);
		return map.get(key);
	}

	private static String getFileObject(File fileKey) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileKey));
			reader.readLine(); // compiled from line
			String[] tokens;
			tokens = reader.readLine().split(" ");
			for (String s : tokens)
				if (!Keyword.isKeyword(s)) {
					reader.close();
					return s;
				}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static <V extends InfoObject> V putValue(HashMap<String, V> map,
			File key, V value) {
		String strkey = getFileObject(key);
		return map.put(strkey, value);
	}

	/**
	 * @param in
	 *            the full name of the object, i.e. java.lang.Object
	 */
	public static InfoObject get(String in) {
		// TODO Auto-generated method stub
		InfoObject obj = interfaces.get(in);
		if (obj != null)
			return obj;
		else
			return classes.get(in);
	}
}
