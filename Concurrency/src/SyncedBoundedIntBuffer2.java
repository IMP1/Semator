public class SyncedBoundedIntBuffer2 extends BoundedIntBuffer {
	
	/** Amount of permits for producers is equal to the amount of space left. */
	GeneralSemaphore fullLock;
	/** Amount of permits for consumers is equal to the amount of space taken. */
	GeneralSemaphore emptyLock;
	/** Whether the buffer is being accessed. */
	BinarySemaphore mutex;

	public SyncedBoundedIntBuffer2(int size) {
		super(size);
		fullLock = new GeneralSemaphore(size);
		emptyLock = new GeneralSemaphore(size, 0); // starts empty because nothing to read yet.
		mutex = new BinarySemaphore();
	}
	
	@Override
	public void write(int data) throws Exception {
		fullLock.acquire(); // take one of the producer permits.
		mutex.acquire();
		super.write(data);
		mutex.release();
		emptyLock.release(); // release one of the consumer permits.
	}
	
	@Override
	public int read() throws Exception {
		emptyLock.acquire(); // take one of the consumer permits.
		mutex.acquire();
		int data = super.read();
		mutex.release();
		fullLock.release(); // release one of the producer permits.
		return data;
	}

}
