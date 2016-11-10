package common.app.error;

/**
 * This exception captures a missing record.
 *
 * @author kaikun
 */
@SuppressWarnings("serial")
public class RecordNotFoundException extends RuntimeException {

    public RecordNotFoundException(String description) {
        super(description);
    }
}
