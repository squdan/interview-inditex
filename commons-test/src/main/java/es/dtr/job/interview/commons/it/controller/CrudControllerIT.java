package es.dtr.job.interview.commons.it.controller;

public interface CrudControllerIT<T, K, ID> extends
        CrudGetControllerIT<T, K, ID>,
        CrudFindControllerIT<T, K, ID>,
        CrudCreateControllerIT<T, K, ID>,
        CrudUpdateControllerIT<T, K, ID>,
        CrudDeleteControllerIT<T, K, ID> {

}
