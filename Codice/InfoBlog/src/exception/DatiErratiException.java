package exception;

public class DatiErratiException extends RuntimeException { 

	private static final long serialVersionUID = 1L;

	public DatiErratiException() {
        super(); 
    }

    public DatiErratiException(String msg) { 
        super(msg); 
    }
}
