import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class ProducerThread implements Runnable {
	BlockingQueue<String> queue;
	String jarPath = Driver.jarPath;
	
	public static final String KILL = "KILL";

	public ProducerThread(BlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		JarInputStream jarInput;
		try {
			jarInput = new JarInputStream(
					new FileInputStream(new File(jarPath)));
			JarEntry current;
			String name;
			int i = 0;
			while ((current = jarInput.getNextJarEntry()) != null) {
				name = current.getName();
				if (name.contains(".class")) {
					queue.put(name);
					System.out.println(name);
					i++;
				}
			}
			jarInput.close();
			Driver.classCount = i;
			queue.put(KILL);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
