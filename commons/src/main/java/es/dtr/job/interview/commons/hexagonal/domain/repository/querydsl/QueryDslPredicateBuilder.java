package es.dtr.job.interview.commons.hexagonal.domain.repository.querydsl;

import com.querydsl.core.types.dsl.*;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslFilter;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslOperators;
import es.dtr.job.interview.commons.util.DateTimeUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Validated
@RequiredArgsConstructor
public class QueryDslPredicateBuilder<T> {

    // Data
    private final Class<T> entityType;
    private final List<QueryDslFilter> queryDslFilters = new ArrayList<>();

    // Optional configuration
    private QueryDslCustomTypesManager customTypes;

    public QueryDslPredicateBuilder<T> with(@NotBlank final String key, @NotBlank final String operator,
                                            @NotNull final Object value) {
        return with(QueryDslFilter.from(key, operator, value));
    }

    public QueryDslPredicateBuilder<T> with(@NotBlank final String key, @NotNull final QueryDslOperators operator,
                                            @NotNull final Object value) {
        return with(new QueryDslFilter(key, operator, value));
    }

    public QueryDslPredicateBuilder<T> with(@NotNull final QueryDslFilter filter) {
        queryDslFilters.add(filter);
        return this;
    }

    public QueryDslPredicateBuilder<T> with(@NotNull final List<QueryDslFilter> filters) {
        queryDslFilters.addAll(filters);
        return this;
    }

    public QueryDslPredicateBuilder<T> withCustomTypes(@NotNull final QueryDslCustomTypesManager customTypes) {
        this.customTypes = customTypes;
        return this;
    }

    public BooleanExpression build() {
        BooleanExpression result = null;

        if (CollectionUtils.isNotEmpty(queryDslFilters)) {
            // Process QueryDsl filters to convert into Predicates
            final List<BooleanExpression> predicates = queryDslFilters.stream()
                    .map(filter -> (new PredicateGenerator(entityType, filter)).getPredicate()).filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (CollectionUtils.isNotEmpty(predicates)) {
                result = Expressions.asBoolean(true).isTrue();

                for (BooleanExpression predicate : predicates) {
                    result = result.and(predicate);
                }
            } else {
                log.error("Se han recibido filtros de QueryDsl, pero no se han procesado a Predicados.");
            }
        }

        return result;
    }

    @AllArgsConstructor
    private class PredicateGenerator {

        private Class<T> entityType;
        private QueryDslFilter filter;

        public BooleanExpression getPredicate() {
            BooleanExpression result = null;
            PathBuilder<T> entityPath = new PathBuilder<T>(entityType, getEntityName());

            // Manage dates
            if (isDate()) {
                // Instance result expression
                final DatePath<Instant> path = entityPath.getDate(filter.getKey(), Instant.class);

                // Parse value
                final Instant value = DateTimeUtils.StringDateUtils.toInstantUtc(filter.getValue().toString());

                // Process operator
                switch (filter.getOperator()) {
                    case IS_NULL_FUNCTION:
                        result = path.isNull();
                        break;
                    case NON_NULL_FUNCTION:
                        result = path.isNotNull();
                        break;
                    case EQUALS:
                    case EQUALS_FUNCTION:
                    case EQUALS_FUNCTION_EQ:
                        result = path.eq(value);
                        break;
                    case NOT_EQUALS:
                    case NON_EQUALS_FUNCTION:
                    case NON_EQUALS_FUNCTION_NE:
                        result = path.ne(value);
                        break;
                    case GREATER_THAN:
                    case GREATER_THAN_FUNCTION_GT:
                        result = path.gt(value);
                        break;
                    case GREATER_THAN_OR_EQUALS:
                    case GREATER_THAN_OR_EQUALS_FUNCTION_GTE:
                        result = path.goe(value);
                        break;
                    case LOWER_THAN:
                    case LOWER_THAN_FUNCTION_LT:
                        result = path.lt(value);
                        break;
                    case LOWER_THAN_OR_EQUALS:
                    case LOWER_THAN_OR_EQUALS_FUNCTION_LTE:
                        result = path.lt(value);
                        break;
                    default:
                        log.warn("Operación '{}' no soportada para el tipo numérico.", filter.getOperator());
                        break;
                }
            }

            // Manage numbers
            else if (isInteger() || isDecimal()) {
                // Instance result expression
                final NumberPath<Double> path = entityPath.getNumber(filter.getKey(), Double.class);

                // Parse value
                final Double value = Double.parseDouble(filter.getValue().toString());

                // Process operator
                switch (filter.getOperator()) {
                    case IS_NULL_FUNCTION:
                        result = path.isNull();
                        break;
                    case NON_NULL_FUNCTION:
                        result = path.isNotNull();
                        break;
                    case EQUALS:
                    case EQUALS_FUNCTION:
                    case EQUALS_FUNCTION_EQ:
                        result = path.eq(value);
                        break;
                    case NOT_EQUALS:
                    case NON_EQUALS_FUNCTION:
                    case NON_EQUALS_FUNCTION_NE:
                        result = path.ne(value);
                        break;
                    case GREATER_THAN:
                    case GREATER_THAN_FUNCTION_GT:
                        result = path.gt(value);
                        break;
                    case GREATER_THAN_OR_EQUALS:
                    case GREATER_THAN_OR_EQUALS_FUNCTION_GTE:
                        result = path.goe(value);
                        break;
                    case LOWER_THAN:
                    case LOWER_THAN_FUNCTION_LT:
                        result = path.lt(value);
                        break;
                    case LOWER_THAN_OR_EQUALS:
                    case LOWER_THAN_OR_EQUALS_FUNCTION_LTE:
                        result = path.lt(value);
                        break;
                    default:
                        log.warn("Operación '{}' no soportada para el tipo numérico.", filter.getOperator());
                        break;
                }
            }

            // Manage UUIDs
            else if (isUUID()) {
                // Instance result expression
                final SimplePath<UUID> path = entityPath.getSimple(filter.getKey(), UUID.class);

                // Parse value
                final UUID value = UUID.fromString(String.valueOf(filter.getValue()));

                // Process operator
                switch (filter.getOperator()) {
                    case IS_NULL_FUNCTION:
                        result = path.isNull();
                        break;
                    case NON_NULL_FUNCTION:
                        result = path.isNotNull();
                        break;
                    case EQUALS:
                    case EQUALS_FUNCTION:
                    case EQUALS_FUNCTION_EQ:
                        result = path.eq(value);
                        break;
                    case NOT_EQUALS:
                    case NON_EQUALS_FUNCTION:
                    case NON_EQUALS_FUNCTION_NE:
                        result = path.ne(value);
                        break;
                    default:
                        log.warn("Operación '{}' no soportada para el tipo String.", filter.getOperator());
                        break;
                }
            }

            // Manage custom Roles enum
            else if (Objects.nonNull(customTypes) && customTypes.isCustomType(filter)) {
                result = customTypes.manageCustomType(entityPath, filter);
            }

            // Manage texts
            else {
                // Instance result expression
                final StringPath path = entityPath.getString(filter.getKey());

                // Parse value
                final String value = String.valueOf(filter.getValue());

                // Process operator
                switch (filter.getOperator()) {
                    case IS_NULL_FUNCTION:
                        result = path.isNull();
                        break;
                    case NON_NULL_FUNCTION:
                        result = path.isNotNull();
                        break;
                    case EQUALS:
                    case EQUALS_FUNCTION:
                    case EQUALS_FUNCTION_EQ:
                        result = path.eq(value);
                        break;
                    case NOT_EQUALS:
                    case NON_EQUALS_FUNCTION:
                    case NON_EQUALS_FUNCTION_NE:
                        result = path.ne(value);
                        break;
                    case STARTS_WITH_FUNCTION:
                    case STARTS_WITH_FUNCTION_SW:
                        path.startsWithIgnoreCase(value);
                        break;
                    case ENDS_WITH_FUNCTION:
                    case ENDS_WITH_FUNCTION_EW:
                        result = path.endsWithIgnoreCase(value);
                        break;
                    case CONTAIN_FUNCTION:
                    case CONTAIN_FUNCTION_C:
                    case CONTAINS_FUNCTION_LIKE:
                        result = path.containsIgnoreCase(value);
                        break;
                    default:
                        log.warn("Operación '{}' no soportada para el tipo String.", filter.getOperator());
                        break;
                }
            }

            return result;
        }

        private String getEntityName() {
            String result = entityType.getSimpleName();

            // Parse first letter to lower case
            char c[] = result.toCharArray();
            c[0] = Character.toLowerCase(c[0]);
            result = new String(c);

            return result;
        }

        private boolean isUUID() {
            boolean result;

            try {
                final UUID uuid = UUID.fromString(filter.getValue().toString());

                if (Objects.nonNull(uuid)) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (final IllegalArgumentException e) {
                result = false;
            }

            return result;
        }

        private boolean isDate() {
            boolean result;

            final Instant date = DateTimeUtils.StringDateUtils.toInstantUtc(filter.getValue().toString());

            if (Objects.nonNull(date)) {
                result = true;
            } else {
                result = false;
            }

            return result;
        }

        private boolean isInteger() {
            boolean result;

            try {
                Integer.parseInt(filter.getValue().toString());
                result = true;
            } catch (final NumberFormatException e) {
                result = false;
            }

            return result;
        }

        private boolean isDecimal() {
            boolean result;

            if (isInteger()) {
                result = false;
            } else {
                try {
                    Double.parseDouble(filter.getValue().toString());
                    result = true;
                } catch (final NumberFormatException e) {
                    result = false;
                }
            }

            return result;
        }
    }
}
