package es.dtr.job.interview.commons.api.querydsl;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QueryDslFilter {

	public static final String NORMAL_OPERATOR = "%s%s%s";
	public static final String FUNCTION_OPERATOR = "%s(%s : %s)";
	public static final String FUNCTION_OPERATOR_SINGLE_ARGUMENT = "%s(%s)";

	/**
	 * Entity field name
	 */
	private String key;

	/**
	 * Operator to apply (=, !=, >, etc)
	 */
	private QueryDslOperators operator;

	/**
	 * Value to use in the filter
	 */
	private Object value;

	public static QueryDslFilter from(final String key, final String operator, final Object value) {
		QueryDslFilter result = null;

		if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(operator)) {
			final Optional<QueryDslOperators> maySupportedOperator = QueryDslOperators.from(operator);

			if (maySupportedOperator.isPresent()) {
				result = new QueryDslFilter(key, maySupportedOperator.get(), value);
			}
		} else {
			log.error("No se pudo generar el QueryDslFilter con key='{}', operator='{}'.", key, operator);
		}

		return result;
	}

	@Override
	public String toString() {
		String result = "";

		if (Objects.nonNull(this.operator)) {
			if (this.operator.isFunction()) {
				if (Objects.nonNull(this.key) && Objects.nonNull(this.value)) {
					result = String.format(FUNCTION_OPERATOR, this.operator.getOperator(), this.key, this.value);
				} else if (Objects.nonNull(this.key) && Objects.isNull(this.value)) {
					result = String.format(FUNCTION_OPERATOR_SINGLE_ARGUMENT, this.operator.getOperator(), this.key);
				} else if (Objects.isNull(this.key) && Objects.nonNull(this.value)) {
					result = String.format(FUNCTION_OPERATOR_SINGLE_ARGUMENT, this.operator.getOperator(), this.value);
				} else {
					log.error(
							"Se ha definido un filtro con un operador de función '{}', pero no se han configurado argumnentos",
							this.operator);
				}
			} else {
				result = String.format(NORMAL_OPERATOR, this.key, this.operator.getOperator(), this.value);
			}
		} else {
			log.error("Se ha definido un filtro sin operador.");
		}

		return result;
	}

}
