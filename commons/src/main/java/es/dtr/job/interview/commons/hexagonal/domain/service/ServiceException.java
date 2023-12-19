package es.dtr.job.interview.commons.hexagonal.domain.service;

import es.dtr.job.interview.commons.error.ErrorDto;
import es.dtr.job.interview.commons.hexagonal.domain.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceException extends DomainException {

    @Serial
    private static final long serialVersionUID = 1167225725106562483L;

    public ServiceException(final Throwable exception, final HttpStatus httpCode) {
        super(exception, exception.getMessage(), httpCode);
    }

    public ServiceException(final String msg, final HttpStatus httpCode) {
        super(null, msg, httpCode);
    }

    public ServiceException(final ErrorDto error) {
        super(null, error);
    }

    public ServiceException(final Throwable exception, final String msg, final HttpStatus httpCode) {
        super(exception, ErrorDto.builder().httpCode(httpCode).message(msg).build());
    }

    public ServiceException(final Throwable exception, final ErrorDto error) {
        super(exception, error);
    }
}
