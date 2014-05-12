public class BinarySemaphore extends GeneralSemaphore {
	
	/**
	 * Creates a new binary semaphore, with max permits equal to 1.
	 */
	public BinarySemaphore() {
		this(false);
	}
	
	public BinarySemaphore(boolean startLocked) {
		super(1, startLocked ? 0 : 1);
	}
	
}
