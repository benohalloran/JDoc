package datahandling;

import java.io.File;

import disassembly.Driver;

public class DataDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		recurseFile(new File(
				"C:\\Users\\Ben\\School\\CSC 200\\Honors Project\\InfoParser\\output"));
	}

	public static void recurseFile(File f) {
		System.out.println(f.exists());
		File[] files = f.listFiles();
		if (files == null)
			return;
		for (File cur : files)
			if (cur.isDirectory())
				recurseFile(cur);
			else if (f.exists())
				parseFile(f);
	}

	public static void parseFile(File f) {
		ClassInfo info = new ClassInfo(f.getAbsolutePath() + "\\" + f.getName()
				+ ".txt");
		System.out.println(info);
	}

}
