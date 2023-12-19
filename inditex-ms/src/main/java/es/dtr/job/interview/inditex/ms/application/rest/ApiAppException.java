package es.dtr.job.interview.inditex.ms.application.rest;

import es.dtr.job.interview.commons.error.ErrorDto;
import es.dtr.job.interview.commons.hexagonal.application.rest.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiAppException extends ApplicationException {

    @Serial
    private static final long serialVersionUID = -966223099222051736L;

    public ApiAppException(final Throwable exception, final HttpStatus httpCode) {
        super(exception, exception.getMessage(), httpCode);
    }

    public ApiAppException(final String msg, final HttpStatus httpCode) {
        super(null, msg, httpCode);
    }

    public ApiAppException(final ErrorDto error) {
        super(null, error);
    }

    public ApiAppException(final Throwable exception, final String msg, final HttpStatus httpCode) {
        super(exception, ErrorDto.builder().httpCode(httpCode).message(msg).build());
    }

    public ApiAppException(final Throwable exception, final ErrorDto error) {
        super(exception, error);
    }
}
