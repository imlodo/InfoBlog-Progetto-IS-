package exception;

public class ArticoloNonPresenteException extends RuntimeException { 

	private static final long serialVersionUID = 1L;

	public ArticoloNonPresenteException() {
        super(); 
    }

    public ArticoloNonPresenteException(String msg) { 
        super(msg); 
    }
}
