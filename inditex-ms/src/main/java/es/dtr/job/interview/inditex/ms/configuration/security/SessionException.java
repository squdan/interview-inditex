package es.dtr.job.interview.inditex.ms.configuration.security;

import es.dtr.job.interview.commons.error.ErrorDto;
import es.dtr.job.interview.commons.error.GenericException;
import es.dtr.job.interview.inditex.ms.domain.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class SessionException extends GenericException {

    @Serial
    private static final long serialVersionUID = 1167225725106502483L;

    public SessionException(final Throwable exception, final HttpStatus httpCode) {
        super(exception, exception.getMessage(), httpCode);
    }

    public SessionException(final String msg, final HttpStatus httpCode) {
        super(null, msg, httpCode);
    }

    public SessionException(final ErrorDto error) {
        super(null, error);
    }

    public SessionException(final Throwable exception, final String msg, final HttpStatus httpCode) {
        super(exception, ErrorDto.builder().httpCode(httpCode).message(msg).build());
    }

    public SessionException(final Throwable exception, final ErrorDto error) {
        super(exception, error);
    }
}
