package exception;

public class LunghezzaException extends RuntimeException { 

	private static final long serialVersionUID = 1L;

	public LunghezzaException() {
        super(); 
    }

    public LunghezzaException(String msg) { 
        super(msg); 
    }
}
