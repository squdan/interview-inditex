package es.dtr.job.interview.commons.test.controller;

public interface CrudControllerTest<T, K, ID> extends
        CrudGetControllerTest<T, K, ID>,
        CrudFindControllerTest<T, K, ID>,
        CrudCreateControllerTest<T, K, ID>,
        CrudUpdateControllerTest<T, K, ID>,
        CrudDeleteControllerTest<T, K, ID> {

}
