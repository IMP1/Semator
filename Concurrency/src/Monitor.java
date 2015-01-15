public class Monitor {

	/** Represents ownership of monitor */
	BinarySemaphore mutex;
	/** Queue for waiting threads. */
	BinarySemaphore waiting;
	/** Count of waiting threads. */
	int threadsWaiting;
	
	/**
	 * Creates new monitor.
	 */
	public Monitor() {
		mutex = new BinarySemaphore();
		waiting = new BinarySemaphore(true);
		threadsWaiting = 0;
	}
	
	/**
	 * Attempts to acquire ownership of this monitor.
	 */
	public void acquire() {
		mutex.acquire();
	}
	
	/**
	 * Makes current thread wait, releasing ownership of the monitor.
	 * Then handles post-wait, trying to reclaim ownership of monitor, 
	 * and finally notifying.
	 */
	public void _wait() {
	        threadsWaiting ++;
	        release();
	        waiting.acquire();
	        acquire();
	        _notify();
	}
	
	/**
	 * Notifies a waiting thread.
	 */
	public void _notify() {
		threadsWaiting--;
		waiting.release();
	}
	
	/**
	 * Notifies all waiting threads.
	 */
	public void _notifyAll() {
		for (int i = threadsWaiting; i > 0; i--) {
			_notify();
		}
	}
	
	/**
	 * Releases ownership of this monitor.
	 */
	public void release() {
		mutex.release();
	}
	
}
