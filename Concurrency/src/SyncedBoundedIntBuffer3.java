public class SyncedBoundedIntBuffer3 extends BoundedIntBuffer {

	private final Monitor monitor;

	public SyncedBoundedIntBuffer3(int size) {
		super(size);
		monitor = new Monitor();
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
		while (isEmpty()) {
			monitor._wait();
		}
		int data = super.read();
		monitor._notifyAll();
		monitor.release();
		return data;
	}

}
