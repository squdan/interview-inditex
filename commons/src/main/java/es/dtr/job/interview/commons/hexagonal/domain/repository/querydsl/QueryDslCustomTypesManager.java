package es.dtr.job.interview.commons.hexagonal.domain.repository.querydsl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimplePath;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslFilter;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Roles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
public class QueryDslCustomTypesManager {

    public boolean isCustomType(final QueryDslFilter filter) {
        return Objects.nonNull(getCustomType(filter));
    }

    public <T> BooleanExpression manageCustomType(final PathBuilder<T> entityPath, final QueryDslFilter filter) {
        BooleanExpression result = null;

        // Select custom value type to execute
        final Class<?> valueType = getCustomType(filter);

        if (Roles.class == valueType) {
            result = manageRoles(entityPath, filter);
        }

        return result;
    }

    private Class<?> getCustomType(final QueryDslFilter filter) {
        Class<?> result = null;

        if (filter.getKey().equals("role") && Roles.isRoleValue(filter.getValue())) {
            result = Roles.class;
        }

        return result;
    }

    private <T> BooleanExpression manageRoles(final PathBuilder<T> entityPath, final QueryDslFilter filter) {
        BooleanExpression result = null;

        // Instance result expression
        final SimplePath<Roles> path = entityPath.getSimple(filter.getKey(), Roles.class);

        // Parse value
        final Roles value = Roles.valueOf(String.valueOf(filter.getValue()));

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
                log.warn("Operación '{}' no soportada para el tipo 'Roles'.", filter.getOperator());
                break;
        }

        return result;
    }

}
