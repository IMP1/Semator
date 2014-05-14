public class BinarySemaphore extends GeneralSemaphore {
	
	/**
	 * Creates a new binary semaphore, not starting locked.
	 */
	public BinarySemaphore() {
		this(false);
	}
	
	/**
	 * Creates a new binary semaphore, with the option of intiailising it
	 * with 0 permits.
	 * @param startLocked whether to start locked or not
	 * @see GeneralSemaphore#GeneralSemaphore(int, int)
	 */
	public BinarySemaphore(boolean startLocked) {
		super(1, startLocked ? 0 : 1);
	}
	
}
