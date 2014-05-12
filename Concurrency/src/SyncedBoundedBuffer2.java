public class SyncedBoundedBuffer2 extends BoundedIntBuffer {
	
	GeneralSemaphore fullLock;
	GeneralSemaphore emptyLock;
	BinarySemaphore mutex;

	public SyncedBoundedBuffer2(int size) {
		super(size);
		fullLock = new GeneralSemaphore(size);
		emptyLock = new GeneralSemaphore(size, 0);
		mutex = new BinarySemaphore();
	}
	
	@Override
	public void write(int data) throws Exception {
		fullLock.acquire();
		mutex.acquire();
		super.write(data);
		mutex.release();
		emptyLock.release();
	}
	
	@Override
	public int read() throws Exception {
		emptyLock.acquire();
		mutex.acquire();
		int data;
		data = super.read();
		mutex.release();
		fullLock.release();
		return data;
	}

}
