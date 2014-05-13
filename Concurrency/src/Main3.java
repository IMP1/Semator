public class Main3 {
	
	private final double LOOPS = 2;
	
	class Reader extends Thread {
		private int id;
		private BoundedIntBuffer buffer;
		private int counter;
		
		Reader(int id, BoundedIntBuffer buffer) {
			this.id = id;
			this.buffer = buffer;
			this.counter = 0;
		}
		
		public void run() {
			while (counter != LOOPS) {
				try {
					System.out.println(buffer.read());
					counter ++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	class Writer extends Thread {
		private int id;
		private BoundedIntBuffer buffer;
		private int counter;
		
		Writer(int id, BoundedIntBuffer buffer) {
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

	public Main3() {
		SyncedBoundedIntBuffer3 buffer = new SyncedBoundedIntBuffer3(2);
		Writer writer1 = new Writer(1, buffer);
		Writer writer2 = new Writer(2, buffer);
		Reader reader1 = new Reader(3, buffer);
		Reader reader2 = new Reader(4, buffer);
		writer1.start();
		writer2.start();
		reader1.start();
		reader2.start();
	}
	
	public static void main(String[] args) {
		new Main3();
	}

}
