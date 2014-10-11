package disassembly;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class Driver {
	public static final String jarPath = "C:\\Program Files (x86)\\Android\\android-studio\\sdk\\platforms\\android-L\\android.jar";
	public static final String jdkBin = "";
	public static final String outputPath = "output_multi\\";

	static final boolean WRITE_OUT = true;
	static int classCount = 0;

	public static void main(String[] args) throws IOException {
		Runtime.getRuntime().addShutdownHook(new Timer());
		LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<String>();
		Thread producer = new Thread(new ProducerThread(queue));
		producer.start();
		ThreadGroup group = new ThreadGroup("workers");
		for (int i = 0; i < 10; i++) {
			Thread t = new Thread(group, new DisasWorker(queue), "Consumer "
					+ i);
			t.start();
		}
	}

	static class Timer extends Thread {
		final static long start = System.currentTimeMillis();
		public Timer() {
			super(new Runnable() {

				@Override
				public void run() {
					System.out.println("Processed " + classCount + " classes Ran: "
							+ (System.currentTimeMillis() - start) / 1000D);
				}

			});
		}
	}
}
