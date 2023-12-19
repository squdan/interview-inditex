package es.dtr.job.interview.commons.hexagonal.infrastructure.database;

import es.dtr.job.interview.commons.error.ErrorDto;
import es.dtr.job.interview.commons.hexagonal.infrastructure.InfrastructureException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class RepositoryInfraException extends InfrastructureException {

    @Serial
    private static final long serialVersionUID = -966243099222051736L;

    public RepositoryInfraException(final Throwable exception, final HttpStatus httpCode) {
        super(exception, exception.getMessage(), httpCode);
    }

    public RepositoryInfraException(final String msg, final HttpStatus httpCode) {
        super(null, msg, httpCode);
    }

    public RepositoryInfraException(final ErrorDto error) {
        super(null, error);
    }

    public RepositoryInfraException(final Throwable exception, final String msg, final HttpStatus httpCode) {
        super(exception, ErrorDto.builder().httpCode(httpCode).message(msg).build());
    }

    public RepositoryInfraException(final Throwable exception, final ErrorDto error) {
        super(exception, error);
    }
}
