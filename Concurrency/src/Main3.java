public class Main3 {
	
	private final static int LOOPS = -1; // infinite looping.

	public Main3() {
		SyncedBoundedIntBuffer3 buffer = new SyncedBoundedIntBuffer3(2);
		Producer writer1 = new Producer(buffer, 1, LOOPS);
		Producer writer2 = new Producer(buffer, 2, LOOPS);
		Consumer reader1 = new Consumer(buffer, LOOPS);
		Consumer reader2 = new Consumer(buffer, LOOPS);
		writer1.start();
		writer2.start();
		reader1.start();
		reader2.start();
	}
	
	public static void main(String[] args) {
		new Main3();
	}

}
