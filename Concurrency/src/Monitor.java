public class Monitor {

	BinarySemaphore mutex;
	BinarySemaphore waiting;
	int threadsWaiting;
	
	public Monitor() {
		mutex = new BinarySemaphore();
		waiting = new BinarySemaphore(true);
		threadsWaiting = 0;
	}
	
	public void acquire() {
		mutex.acquire();
	}
	
	public void _wait() {
        threadsWaiting++;
        mutex.release();
        waiting.acquire();
        mutex.acquire();
        threadsWaiting--;
        waiting.release();
	}
	
	public void _notify() {
		threadsWaiting--;
		waiting.release();
	}
	
	public void _notifyAll() {
		for (int i = threadsWaiting; i > 0; i--) {
			_notify();
		}
	}
	
	public void release() {
		mutex.release();
	}
	
}
