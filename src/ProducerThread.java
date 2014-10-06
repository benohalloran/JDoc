import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ProducerThread implements Runnable {
	BlockingQueue<String> queue;
	String jarPath = Driver.jarPath;
	
	public static final String KILL = "KILL";

	public ProducerThread(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		ZipInputStream zipInput;
		try {
			zipInput = new ZipInputStream(
					new FileInputStream(new File(jarPath)));
			ZipEntry current;
			String name;
			int i = 0;
			while ((current = zipInput.getNextEntry()) != null) {
				name = current.getName();
				if (name.contains(".class")) {
					queue.put(name);
					System.out.println(name);
					i++;
				}
			}
			zipInput.close();
			Driver.classCount = i;
			//queue.put(KILL);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
