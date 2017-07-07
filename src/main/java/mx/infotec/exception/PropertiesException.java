package mx.infotec.exception;

public class PropertiesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PropertiesException() {
        super();
    }

    public PropertiesException(String s) {
        super(s);
    }

    public PropertiesException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PropertiesException(Throwable throwable) {
        super(throwable);
    }
	
}
