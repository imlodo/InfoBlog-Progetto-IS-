package exception;

public class FormatoAllegatiNonCorrettoException extends RuntimeException { 

	private static final long serialVersionUID = 1L;

	public FormatoAllegatiNonCorrettoException() {
        super(); 
    }

    public FormatoAllegatiNonCorrettoException(String msg) { 
        super(msg); 
    }
}
