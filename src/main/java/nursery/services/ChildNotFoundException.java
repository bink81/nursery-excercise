package nursery.services;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
class ChildNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ChildNotFoundException(Long childId) {
		super("could not find child '" + childId + "'.");
	}
}
