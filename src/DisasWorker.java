import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class DisasWorker implements Runnable {
	BlockingQueue<String> queue;

	String jarPath = Driver.jarPath;
	String outputPath = Driver.outputPath;

	public DisasWorker(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			while (!queue.isEmpty()) {
				String taken = queue.take();
				consume(taken);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void consume(String name) {
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
