package tk.xdevcloud.medicalcore.exceptions;

public class NotFoundException extends Exception{

	private static final long serialVersionUID = 6860183980550772371L;
    /**
     * 
     * @param errorMessage 
     */
	public NotFoundException(String errorMessage) {
		
		super(errorMessage);
	}

}
