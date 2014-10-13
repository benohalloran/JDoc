package datahandling;

import java.io.File;

public class DataDriver {

	public static void main(String[] args) {
		recurseFile(new File(
				"C:\\Users\\Ben\\School\\CSC 200\\Honors Project\\InfoParser\\output"));
	}

	public static void recurseFile(File root) {
		File[] children = root.listFiles();
		if (children == null)
			return;
		for (File cur : children)
			if (cur.isDirectory())
				recurseFile(cur);
			else if (cur.exists())
				parseFile(cur);
	}

	public static void parseFile(File f) {
		if (f.isDirectory())
			return;
		InfoObject parsed = FileInfoFactory.parseFile(f);
		if (parsed != null)
			System.out.println(parsed);
	}

}
