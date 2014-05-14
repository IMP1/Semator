
public class Consumer extends Thread {
	/** Buffer to read from */
	private BoundedIntBuffer buffer;
	/** Counter of times we have read. */
	private int counter;
	/** Times we are to read. */
	private final int loops;
	
	/**
	 * Creates a new consumer that will read from a bounded buffer.
	 * @param buffer the buffer to read from.
	 * @param loops the amount of times to read, or -1 for infinite looping.
	 */
	Consumer(BoundedIntBuffer buffer, int loops) {
		this.buffer = buffer;
		this.counter = 0;
		this.loops = loops;
	}
	
	public void run() {
		while (loops == -1 || counter != loops) {
			try {
				System.out.println(buffer.read());
				counter ++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
