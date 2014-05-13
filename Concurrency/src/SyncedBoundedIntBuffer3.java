public class SyncedBoundedIntBuffer3 extends BoundedIntBuffer {

	Monitor monitor = new Monitor();
	int threadsWaiting = 0;
	
	public SyncedBoundedIntBuffer3(int size) {
		super(size);
	}
	
	@Override
	public void write(int data) throws Exception {
		monitor.acquire();
//		boolean acquired = false;
//        while (isFull()) {
//            // Equivalent of wait()
//            if (acquired) {
//                monitor.threadsWaiting --;
//                monitor.waiting.release();
//            }
//            monitor.threadsWaiting ++;
//            monitor.mutex.release();
//            monitor.waiting.acquire();
//            monitor.mutex.acquire();
//            acquired = true;
//        }
		while(isFull()) {
			monitor._wait();
		}
		super.write(data);
		monitor._notifyAll();
		monitor.release();
	}
	
	@Override
	public int read() throws Exception {
		monitor.acquire();
		
        while (isEmpty()) {
            monitor._wait();
        }
		
		int data = super.read();
		
		monitor._notifyAll();
		
		monitor.release();
		return data;
	}

}
