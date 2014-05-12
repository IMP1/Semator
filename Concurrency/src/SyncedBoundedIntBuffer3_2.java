
public class SyncedBoundedIntBuffer3_2 extends BoundedIntBuffer {

	Monitor monitor = new Monitor();
	int threadsWaiting = 0;
	
	public SyncedBoundedIntBuffer3_2(int size) {
		super(size);
	}
	
	@Override
	public void write(int data) throws Exception {
		monitor.acquire();
        while (isFull()) {
            monitor._wait();
        }
		super.write(data);
		monitor._notifyAll();
		monitor.release();
	}
	
	@Override
	public int read() throws Exception {
		monitor.acquire();
		
        while (isFull()) {
            monitor._wait();
        }
		
		int data = super.read();
		
		monitor._notifyAll();
		
		monitor.release();
		return data;
	}

}
