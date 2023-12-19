package es.dtr.job.interview.commons.test.service;

public interface CrudServiceTest<T, ID> extends
        CrudGetServiceTest<T, ID>,
        CrudFindServiceTest<T, ID>,
        CrudCreateServiceTest<T, ID>,
        CrudUpdateServiceTest<T, ID>,
        CrudDeleteServiceTest<T, ID> {

}
