package es.dtr.job.interview.inditex.ms.adapter.in;

import es.dtr.job.interview.commons.error.ErrorDto;
import es.dtr.job.interview.commons.error.GenericException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiException extends GenericException {

    @Serial
    private static final long serialVersionUID = -966223099222051736L;

    public ApiException(final Throwable exception, final HttpStatus httpCode) {
        super(exception, exception.getMessage(), httpCode);
    }

    public ApiException(final String msg, final HttpStatus httpCode) {
        super(null, msg, httpCode);
    }

    public ApiException(final ErrorDto error) {
        super(null, error);
    }

    public ApiException(final Throwable exception, final String msg, final HttpStatus httpCode) {
        super(exception, ErrorDto.builder().httpCode(httpCode).message(msg).build());
    }

    public ApiException(final Throwable exception, final ErrorDto error) {
        super(exception, error);
    }
}
