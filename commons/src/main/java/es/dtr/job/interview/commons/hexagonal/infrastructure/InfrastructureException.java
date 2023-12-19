package es.dtr.job.interview.commons.hexagonal.infrastructure;

import es.dtr.job.interview.commons.error.ErrorDto;
import es.dtr.job.interview.commons.error.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InfrastructureException extends GenericException {

	@Serial
	private static final long serialVersionUID = 8325958853559387708L;

	public InfrastructureException(final Throwable exception, final HttpStatus httpCode) {
		super(exception, exception.getMessage(), httpCode);
	}

	public InfrastructureException(final String msg, final HttpStatus httpCode) {
		super(null, msg, httpCode);
	}

	public InfrastructureException(final ErrorDto error) {
		super(null, error);
	}

	public InfrastructureException(final Throwable exception, final String msg, final HttpStatus httpCode) {
		super(exception, ErrorDto.builder().httpCode(httpCode).message(msg).build());
	}

	public InfrastructureException(final Throwable exception, final ErrorDto error) {
		super(exception, error);
	}
}
