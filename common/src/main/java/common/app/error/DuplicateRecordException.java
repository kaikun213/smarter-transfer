package common.app.error;

/**
 * This exception captures a try to add a duplicate entry restricted to unique constraints
 *
 * @author kaikun
 */
@SuppressWarnings("serial")
public class DuplicateRecordException extends RuntimeException {
	
	public DuplicateRecordException(String description){
		super(description);
	}

}
