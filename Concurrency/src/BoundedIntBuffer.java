public class BoundedIntBuffer {

	/** data contained within buffer */
    protected final int[] contents;
    /** position to read from */
    protected int readHead;
    /** position to write to */
    protected int writeHead;
    /** amount of data currently in buffer */
    protected int count;
    
    /**
     * Creates a new bounded buffer with cap on number of items.
     * @param size the size to cap at. 
     */
    public BoundedIntBuffer(int size) {
        contents = new int[size];
        readHead = 0;
        writeHead = 0;
        count = 0; // count only used for isEmpty and isFull
    }
    
    /**
     * @return whether the buffer is full.
     */
    public boolean isFull() {
    	return count == contents.length; 
    }
    
    /**
     * @return whether the buffer is empty.
     */
    public boolean isEmpty() {
    	return count == 0; 
    }
    
    /**
     * Writes data to the buffer if it is not full.
     * @param data the data to write to the buffer.
     * @throws Exception if buffer is full, throws exception. This should be handled by subclasses.
     */
    public void write(int data) throws Exception {
        if (isFull()) {
            throw new Exception("Buffer Overflow!");
        } else {
        	count ++;
        	contents[writeHead] = data;
            writeHead = (writeHead + 1) % contents.length;
        }
    }
    
    /**
     * Reads data to the buffer if it is not empty.
     * @return the data read
     * @throws Exception if buffer is empty, throws exception. This should be handled by subclasses.
     */
    public int read() throws Exception {
        if (isEmpty()) {
        	throw new Exception("Buffer Underflow!");
        } else {
        	count --;
        	int item = contents[readHead];
            readHead = (readHead + 1) % contents.length;
            return item;
        }
    }
    
    @Override
    public String toString() {
    	StringBuilder s = new StringBuilder();
    	s.append("BoundedBuffer size=");
    	s.append(contents.length);
    	s.append(" contents = [");
    	for (int i = 0; i < contents.length - 1; i ++) {
    		s.append(contents[i]);
    		s.append(", ");
    	}
    	s.append(contents[contents.length - 1]);
    	s.append("]");
    	return s.toString();
    }

}
