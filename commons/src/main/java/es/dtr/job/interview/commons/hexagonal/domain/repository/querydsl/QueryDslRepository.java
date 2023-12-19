package es.dtr.job.interview.commons.hexagonal.domain.repository.querydsl;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslFilter;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public interface QueryDslRepository<T, K extends EntityPathBase<T>>
        extends QuerydslPredicateExecutor<T>, QuerydslBinderCustomizer<K> {

    Class<T> getEntityType();

    default List<T> findAll(final List<QueryDslFilter> filters,
                            final Pageable pageable) {
        List<T> result = null;

        if (CollectionUtils.isNotEmpty(filters)) {
            final QueryDslPredicateBuilder<T> queryDslPredicateBuilder = new QueryDslPredicateBuilder<T>(getEntityType())
                    .with(filters);

            if (Objects.nonNull(getCustomTypesManager())) {
                queryDslPredicateBuilder.withCustomTypes(getCustomTypesManager());
            }

            Iterable<T> queryResult;

            if (Objects.isNull(pageable)) {
                queryResult = this.findAll(queryDslPredicateBuilder.build());
            } else {
                queryResult = this.findAll(queryDslPredicateBuilder.build(), pageable);
            }

            result = StreamSupport.stream(queryResult.spliterator(), false).collect(Collectors.toList());
        }

        return result;
    }

    default QueryDslCustomTypesManager getCustomTypesManager() {
        return null;
    }

    @Override
    default void customize(final QuerydslBindings bindings, final K root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }

}
