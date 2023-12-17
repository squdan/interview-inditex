package es.dtr.job.interview.commons.error;

import es.dtr.job.interview.commons.util.CommonUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@Getter
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class GenericException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -4756522208248326280L;

    /* Custom Information */
    private final ErrorDto error;

    public GenericException(final Throwable exception, final HttpStatus httpCode) {
        this(exception, exception.getMessage(), httpCode);
    }

    public GenericException(final String msg, final HttpStatus httpCode) {
        this(null, msg, httpCode);
    }

    public GenericException(final ErrorDto error) {
        this(null, error);
    }

    public GenericException(final Throwable exception, final String msg, final HttpStatus httpCode) {
        this(exception, ErrorDto.builder().httpCode(httpCode).message(msg).build());
    }

    public GenericException(final Throwable exception, final ErrorDto error) {
        super(error.getMessage(), exception);
        this.error = error;
    }

    @Override
    public String toString() {
        return CommonUtils.objectToStringJson(this, false);
    }
}
