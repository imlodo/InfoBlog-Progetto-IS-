package exception;

public class AutoreNonPresenteException extends RuntimeException { 

	private static final long serialVersionUID = 1L;

	public AutoreNonPresenteException() {
        super(); 
    }

    public AutoreNonPresenteException(String msg) { 
        super(msg); 
    }
}
