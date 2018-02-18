package nursery.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
class CheckinException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CheckinException(final Long childId) {
        super("Child '" + childId + "' is already checked in.");
    }
}
