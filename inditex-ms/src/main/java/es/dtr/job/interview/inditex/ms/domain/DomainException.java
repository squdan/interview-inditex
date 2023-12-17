package es.dtr.job.interview.inditex.ms.domain;

import es.dtr.job.interview.commons.error.ErrorDto;
import es.dtr.job.interview.commons.error.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class DomainException extends GenericException {

	@Serial
	private static final long serialVersionUID = 8325958843559387708L;

	public DomainException(final Throwable exception, final HttpStatus httpCode) {
		super(exception, exception.getMessage(), httpCode);
	}

	public DomainException(final String msg, final HttpStatus httpCode) {
		super(null, msg, httpCode);
	}

	public DomainException(final ErrorDto error) {
		super(null, error);
	}

	public DomainException(final Throwable exception, final String msg, final HttpStatus httpCode) {
		super(exception, ErrorDto.builder().httpCode(httpCode).message(msg).build());
	}

	public DomainException(final Throwable exception, final ErrorDto error) {
		super(exception, error);
	}
}
