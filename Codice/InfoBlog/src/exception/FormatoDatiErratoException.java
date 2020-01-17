package exception;

public class FormatoDatiErratoException extends RuntimeException { 

	private static final long serialVersionUID = 1L;

	public FormatoDatiErratoException() {
        super(); 
    }

    public FormatoDatiErratoException(String msg) { 
        super(msg); 
    }
}
