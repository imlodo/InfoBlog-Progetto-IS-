package exception;

public class DatiEsistentiException extends RuntimeException { 

	private static final long serialVersionUID = 1L;

	public DatiEsistentiException() {
        super(); 
    }

    public DatiEsistentiException(String msg) { 
        super(msg); 
    }
}
