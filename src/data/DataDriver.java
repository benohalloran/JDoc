package data;

public class DataDriver {
	static int filesCount = 0;

	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Timer());
		filesCount = FileInfoFactory.parseDirectory();
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
