package seedu.address.model.profile.course.module.exceptions;

/**
 * Exception thrown when there is an input error of date and time.
 */
public class DateTimeException extends Exception {
    public DateTimeException (String message) {
        super(message);
    }
}
