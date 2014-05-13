public class Main2 {
	
	private final static int LOOPS = 2;
	
	class Reader extends Thread {
		private int id;
		private SyncedBoundedIntBuffer2 buffer;
		private int counter;
		
		Reader(int id, SyncedBoundedIntBuffer2 buffer) {
			this.id = id;
			this.buffer = buffer;
			this.counter = 0;
		}
		
		public void run() {
			while (counter != LOOPS) {
				try {
					buffer.read();
					counter ++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	class Writer extends Thread {
		private int id;
		private SyncedBoundedIntBuffer2 buffer;
		private int counter;
		
		Writer(int id, SyncedBoundedIntBuffer2 buffer) {
			this.id = id;
			this.buffer = buffer;
			this.counter = 0;
		}
		
		public void run() {
			while (counter != LOOPS) {
				try {
					buffer.write(id);
					counter ++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public Main2() {
		SyncedBoundedIntBuffer2 buffer = new SyncedBoundedIntBuffer2(2);
		Reader reader1 = new Reader(1, buffer);
		Reader reader2 = new Reader(2, buffer);
		Writer writer1 = new Writer(3, buffer);
		Writer writer2 = new Writer(4, buffer);
		
		writer1.start();
		writer2.start();
		reader1.start();
		reader2.start();
	}
	
	public static void main(String[] args) {
		new Main2();
	}

}
