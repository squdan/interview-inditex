package es.dtr.job.interview.commons.hexagonal.domain.repository;

import es.dtr.job.interview.commons.error.ErrorDto;
import es.dtr.job.interview.commons.hexagonal.domain.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class RepositoryDomainException extends DomainException {

	@Serial
	private static final long serialVersionUID = 8325958843559387708L;

	public RepositoryDomainException(final Throwable exception, final HttpStatus httpCode) {
		super(exception, exception.getMessage(), httpCode);
	}

	public RepositoryDomainException(final String msg, final HttpStatus httpCode) {
		super(null, msg, httpCode);
	}

	public RepositoryDomainException(final ErrorDto error) {
		super(null, error);
	}

	public RepositoryDomainException(final Throwable exception, final String msg, final HttpStatus httpCode) {
		super(exception, ErrorDto.builder().httpCode(httpCode).message(msg).build());
	}

	public RepositoryDomainException(final Throwable exception, final ErrorDto error) {
		super(exception, error);
	}
}
