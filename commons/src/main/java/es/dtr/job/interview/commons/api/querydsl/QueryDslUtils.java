package es.dtr.job.interview.commons.api.querydsl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
public class QueryDslUtils {

    // Configuration
    private static final String ALLOWED_CHARACTERS_KEYS = "[A-Za-z0-9._-]";
    private static final String ALLOWED_CHARACTERS_VALUES = "[A-Za-z0-9.:_-]";
    private static final String ALLOWED_FUNCTION_SEPARATOR_CHARACTERS = "[,:;]";

    private final String REGEX_QUERY_DSL_OPERATORS;
    private final String REGEX_QUERY_DSL_FUNCTION_OPERATORS;

    private final Pattern PATTERN_QUERY_DSL_OPERATORS;
    private final Pattern PATTERN_QUERY_DSL_FUNCTION_OPERATORS;

    public QueryDslUtils() {
        final StringBuilder regexQueryDslOperatorsSimpleBuilder = new StringBuilder();
        final StringBuilder regexQueryDslOperatorsMultipleBuilder = new StringBuilder();
        final StringBuilder regexQueryDslFunctionOperatorsBuilder = new StringBuilder();

        Stream.of(QueryDslOperators.values()).forEach(o -> {
            // Normal operators
            if (!o.isFunction()) {
                // Split simple operators (=, >...) from multiple operators (>=, <=...)
                if (o.getOperator().length() == 1) {
                    if (!regexQueryDslOperatorsSimpleBuilder.isEmpty()) {
                        regexQueryDslOperatorsSimpleBuilder.append("|");
                    }

                    regexQueryDslOperatorsSimpleBuilder.append(o.getOperator());
                } else {
                    if (!regexQueryDslOperatorsMultipleBuilder.isEmpty()) {
                        regexQueryDslOperatorsMultipleBuilder.append("|");
                    }

                    regexQueryDslOperatorsMultipleBuilder.append(o.getOperator());
                }
            }

            // Function operators
            else {
                if (!regexQueryDslFunctionOperatorsBuilder.isEmpty()) {
                    regexQueryDslFunctionOperatorsBuilder.append("|");
                }

                regexQueryDslFunctionOperatorsBuilder.append(o.getOperator());
            }
        });

        // Prepare regex expressions
        this.REGEX_QUERY_DSL_OPERATORS = String.format("(%s+)(%s|%s)(%s+)", ALLOWED_CHARACTERS_KEYS,
                regexQueryDslOperatorsMultipleBuilder, regexQueryDslOperatorsSimpleBuilder,
                ALLOWED_CHARACTERS_VALUES);
        this.REGEX_QUERY_DSL_FUNCTION_OPERATORS = String.format(
                "(%s)\\((%s+) *%s? *(%s*)\\)",
                regexQueryDslFunctionOperatorsBuilder,
                ALLOWED_CHARACTERS_KEYS, ALLOWED_FUNCTION_SEPARATOR_CHARACTERS, ALLOWED_CHARACTERS_VALUES);

        // Prepare patterns
        this.PATTERN_QUERY_DSL_OPERATORS = Pattern.compile(REGEX_QUERY_DSL_OPERATORS);
        this.PATTERN_QUERY_DSL_FUNCTION_OPERATORS = Pattern.compile(REGEX_QUERY_DSL_FUNCTION_OPERATORS);
    }

    public List<QueryDslFilter> prepareFilters(final List<String> filters) {
        List<QueryDslFilter> result = null;

        if (CollectionUtils.isNotEmpty(filters)) {
            result = new ArrayList<>();
            result.addAll(getFiltersFromOperatorsSearch(filters));
            result.addAll(getFiltersFromFunctionOperatorsSearch(filters));
        }

        return result;
    }

    private List<QueryDslFilter> getFiltersFromOperatorsSearch(final List<String> filters) {
        List<QueryDslFilter> result = null;

        if (CollectionUtils.isNotEmpty(filters)) {
            final List<QueryDslFilter> resultBuilder = new ArrayList<>();
            filters.forEach(f -> {
                final Matcher matcher = PATTERN_QUERY_DSL_OPERATORS.matcher(f);

                if (matcher.find()) {
                    resultBuilder.add(QueryDslFilter.from(matcher.group(1), matcher.group(2), matcher.group(3)));
                }
            });
            result = resultBuilder.stream().filter(rb -> Objects.nonNull(rb)).collect(Collectors.toList());
        }

        return result;
    }

    private List<QueryDslFilter> getFiltersFromFunctionOperatorsSearch(final List<String> filters) {
        List<QueryDslFilter> result = null;

        if (CollectionUtils.isNotEmpty(filters)) {
            final List<QueryDslFilter> resultBuilder = new ArrayList<>();
            filters.forEach(f -> {
                final Matcher matcher = PATTERN_QUERY_DSL_FUNCTION_OPERATORS.matcher(f);

                if (matcher.find()) {
                    resultBuilder.add(QueryDslFilter.from(matcher.group(2), matcher.group(1), matcher.group(3)));
                }
            });
            result = resultBuilder.stream().filter(rb -> Objects.nonNull(rb)).collect(Collectors.toList());
        }

        return result;
    }

}
