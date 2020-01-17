package exception;

public class DataPassataException extends RuntimeException { 

	private static final long serialVersionUID = 1L;

	public DataPassataException() {
        super(); 
    }

    public DataPassataException(String msg) { 
        super(msg); 
    }
}
