package egen.project.rrs.configuration;

public class ExceptionHandler extends Exception {
	
	private static final long serialVersionUID = 1L;

	public ExceptionHandler(String msg){
		super(msg);
	}
	
	public ExceptionHandler(String msg, Throwable cause){
		super(msg,cause);
	}
}
