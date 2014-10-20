package data;

import java.io.File;

public class DataDriver {
	static int filesCount = 0;

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Timer());
		recurseFile(new File(
				"C:\\Users\\Ben\\School\\CSC 200\\Honors Project\\InfoParser\\output"));
		if (false)
			for (String s : FileInfoFactory.getClasses().keySet()) {
				System.out.println(s);
			}
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
		if (parsed != null) {
			// System.out.println(parsed);
			filesCount++;
		}
	}

	static class Timer extends Thread {
		public Timer() {
			super(new Runnable() {
				final long start = System.currentTimeMillis();

				@Override
				public void run() {
					System.out.println("Processed " + filesCount
							+ " files in Ran: "
							+ (System.currentTimeMillis() - start) / 1000D
							+ " seconds");
					System.out.println("Classes: "
							+ FileInfoFactory.getClasses().size());
					System.out.println("Interfaces: "
							+ FileInfoFactory.getInterfaces().size());
				}

			});
		}
	}
}
