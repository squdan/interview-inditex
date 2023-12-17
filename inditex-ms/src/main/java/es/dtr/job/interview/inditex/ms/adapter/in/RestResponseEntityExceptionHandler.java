package es.dtr.job.interview.inditex.ms.adapter.in;

import es.dtr.job.interview.commons.error.ErrorDto;
import es.dtr.job.interview.commons.error.GenericException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleConflict(final RuntimeException e, final WebRequest request) {

        // Generates the returned error information
        ErrorDto errorResponse = null;

        if (GenericException.class.isInstance(e)) {
            errorResponse = ((GenericException) e).getError();
        }

        if (Objects.isNull(errorResponse)) {
            errorResponse = generateErrorResponse(e);
        }

        log.warn("Returning [{}] error response. Error: {}", errorResponse.getHttpCode(), errorResponse);
        return handleExceptionInternal(e, errorResponse, new HttpHeaders(),
                errorResponse.getHttpCode(), request);
    }

    public ErrorDto generateErrorResponse(final Exception e) {
        return generateErrorResponse(e, getHttpStatus(e));
    }

    private ErrorDto generateErrorResponse(final Exception e, final HttpStatus originalHttpStatus) {
        return ErrorDto.builder().origin(e.getStackTrace()[0].getMethodName()).exceptionType(e.getClass())
                .httpCode(originalHttpStatus).message(getExceptionMessage(e)).build();
    }

    private HttpStatus getHttpStatus(final Exception e) {
        HttpStatus result = null;

        // Tries to load the HttpStatus from ResponseStatus annotation
        ResponseStatus responseStatus = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);

        if (Objects.nonNull(responseStatus)) {
            result = responseStatus.code();
        } else if (AuthenticationException.class.isInstance(e)) {
            result = HttpStatus.FORBIDDEN;
        } else {
            result = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return result;
    }

    private String getExceptionMessage(final Exception e) {
        return StringUtils.isNotBlank(e.getMessage()) ? e.getMessage() : "Unknown";
    }
}
