package es.dtr.job.interview.commons.error;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;

import lombok.Builder;
import lombok.Value;

/**
 * This error information class has the default information used by Spring
 * defined at {@link DefaultErrorAttributes}
 */
@Value
@Builder
public class DefaultSpringErrorDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 4110038566194054803L;

	Date timestamp;
	int status;
	String error;
	String message;
	String path;
}
