package es.dtr.job.interview.commons;

import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslFilter;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslOperators;

import java.time.Instant;
import java.util.List;

public interface TestData {

    String TEST_STRING = "test";
    String TEST_EMAIL = "test@gmail.com";
    String TEST_PHONE = "+34666777888";
    Instant NOW = Instant.now();

    List<QueryDslFilter> TEST_FILTERS_LIST = List.of(
            QueryDslFilter.builder().key("id").operator(QueryDslOperators.EQUALS).value(1).build(),
            QueryDslFilter.builder().key("name").operator(QueryDslOperators.EQUALS).value(TEST_STRING).build()
    );
    List<String> TEST_FILTERS_LIST_STRING = TEST_FILTERS_LIST.stream().map(QueryDslFilter::toString).toList();
    String TEST_FILTERS_STRING = String.join(", ", TEST_FILTERS_LIST_STRING);
}
