public class GeneralSemaphore {
	
	/** Max number of permits that can be taken. */
	protected int maxPermits;
	/** Number of permits that have been taken. */
	protected int permitsLeft;
	/** object to use as monitor to wait/notify on. */
	protected final Object monitor;
	
	public GeneralSemaphore(int permits) {
		this(permits, permits);
	}
	
	/**
	 * Creates a new semaphore.
	 * @param permits the amount of permits allocatable.
	 */
	public GeneralSemaphore(int permits, int initialValue) {
		if (permits <= 0) {
			throw new RuntimeException("Semaphore has to have at least 1 permit.");
		}
		monitor = new Object();
		maxPermits = permits;
		permitsLeft = initialValue;
	}
	
	/**
	 * Allocates a permit to the thread calling this method.
	 */
	public void acquire() {
		synchronized (monitor) {
			// Loop if no permits left.
			while (permitsLeft == 0) {
				try {
					// Wait until someone releases.
					monitor.wait();
				} catch (InterruptedException e) {}
			}
			// Take a permit.
			permitsLeft --;
		}
	}
	
	/**
	 * Releases the permit owned by the thread calling this method, 
	 * if it has one.
	 */
	public void release() {
		synchronized (monitor) {
			// Release permit if there are any to release.
			if (permitsLeft < maxPermits) {
				// Release a permit.
				permitsLeft ++;
				monitor.notifyAll();
			}
		}
	}
	
}
