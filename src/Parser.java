import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class Parser {
	static String jarPath = Driver.jarPath;
	static String outputPath = Driver.outputPath;
	static BlockingDeque<String> queue;

	static ThreadGroup group;
	static Thread[] threads;
	public static boolean terminal = false;

	static {
		queue = new LinkedBlockingDeque<String>();
		group = new ThreadGroup("parsers");
		threads = new Thread[10];
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(group, initWorkerRunable());
			threads[i].start();
		}
	}

	static Runnable initWorkerRunable() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					while (true)
						parse(queue.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
	}

	public static void parseInBackground(final String name) {
		queue.offer(name);
	}

	public static void parse(final String name) {
		try {
			String encapJarPath = "\"" + jarPath + "\"";
			String procName = name.replace(".class", "").replace("/", ".");
			// Start the command
			String[] procArr = { "javap", "-classpath", encapJarPath,
					"-bootclasspath", encapJarPath, procName };

			ProcessBuilder build = new ProcessBuilder(procArr);

			// Split the name up for folders etc.
			String[] tokens = name.split("/|.class|\\$");
			StringBuffer buff = new StringBuffer();
			for (int i = 0; i < tokens.length - 1; i++)
				buff.append(tokens[i] + "\\");
			File dir = new File(outputPath, buff.toString());
			dir.mkdirs();
			File ofile = new File(dir.getAbsolutePath(),
					tokens[tokens.length - 1] + ".txt");
			ofile.createNewFile();
			build.redirectErrorStream(true);
			build.redirectOutput(ofile);
			build.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
