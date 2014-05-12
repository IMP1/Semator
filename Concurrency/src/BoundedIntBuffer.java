public class BoundedIntBuffer {

    protected final int[] contents;
    protected int readHead, writeHead, count;
    
    public BoundedIntBuffer(int size) {
        contents = new int[size];
        // readHead and emptyHead both = -1 means empty
        readHead = 0;
        writeHead = 0;
        count = 0; // count only used for isEmpty and isFull
    }
    
    public boolean isFull() {
    	return count == contents.length; 
    }
    
    public boolean isEmpty() {
    	return count == 0; 
    }
    
    public void write(int data) throws Exception {
        if (isFull()) {
            throw new Exception("Buffer Overflow!");
        } else {
        	count ++;
        	contents[writeHead] = data;
            writeHead = (writeHead + 1) % contents.length;
        }
    }
    
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
