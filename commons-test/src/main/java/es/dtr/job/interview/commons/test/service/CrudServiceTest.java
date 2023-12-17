package es.dtr.job.interview.commons.test.service;

public interface CrudServiceTest<T, K, ID> extends
        CrudGetServiceTest<T, K, ID>,
        CrudCreateServiceTest<T, K, ID>,
        CrudUpdateServiceTest<T, K, ID>,
        CrudDeleteServiceTest<T, K, ID> {

}
