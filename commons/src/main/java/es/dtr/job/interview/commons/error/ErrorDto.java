package es.dtr.job.interview.commons.error;

import java.io.Serial;
import java.io.Serializable;

import es.dtr.job.interview.commons.util.CommonUtils;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.springframework.http.HttpStatus;

@Value
@Builder
@Jacksonized
public class ErrorDto implements Serializable {

	@Serial
	private static final long serialVersionUID = 1424911199995215164L;

	String origin;

	HttpStatus httpCode;

	String message;

	Class<? extends Exception> exceptionType;

	@Override
	public String toString() {
		return CommonUtils.objectToStringJson(this, false);
	}
}
