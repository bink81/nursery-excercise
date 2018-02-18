package nursery.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
class CheckoutException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CheckoutException(Long id) {
        super("Child '" + id + "' is already checked out.");
    }
}
