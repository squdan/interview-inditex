package es.dtr.job.interview.commons.util;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CommonUtils {

	// Dependencies
	public static ObjectMapper mapper;

	private static void initMapper() {
		// Initializes and configures Jackson mapper if necessary
		if (Objects.isNull(mapper)) {
			mapper = new ObjectMapper();
			mapper.findAndRegisterModules();
		}
	}

	/**
	 * Returns class as string JSON indicating whether or not it should be
	 * prettified
	 *
	 * @param object       object to build as JSON
	 * @param prettifyJson whether or not JSON should be prettified
	 *
	 * @return (String) JSON
	 */
	public static String objectToStringJson(final Object object, final boolean prettifyJson) {
		String result = StringUtils.EMPTY;

		try {
			// Initializes Jackson mapper if necessary
			CommonUtils.initMapper();

			if (object != null) {
				if (prettifyJson) {
					result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
				} else {
					result = mapper.writeValueAsString(object);
				}
			}
		} catch (JsonProcessingException e) {
			log.warn("Error serializing class [{}].", object.getClass().getName());
			result = StringUtils.EMPTY;
		}

		return result;
	}
}
