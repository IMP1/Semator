
public class Producer extends Thread {
	
	/** Buffer to write to. */
	private BoundedIntBuffer buffer;
	/** Number to write to buffer. */
	private int num;
	/** Counter of times we have written. */
	private int counter;
	/** Times we are to write. */
	private final int loops;
	
	/**
	 * Creates a new Producer that will write to a bounded buffer.
	 * @param buffer the buffer to write to.
	 * @param num the number to write to the buffer.
	 * @param loops the amount of times to write, or -1 for infinite looping.
	 */
	Producer(BoundedIntBuffer buffer, int num, int loops) {
		this.num = num;
		this.buffer = buffer;
		this.counter = 0;
		this.loops = loops;
	}
	
	public void run() {
		while (loops == -1 || counter < loops) {
			try {
				buffer.write(num);
				counter ++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
}
