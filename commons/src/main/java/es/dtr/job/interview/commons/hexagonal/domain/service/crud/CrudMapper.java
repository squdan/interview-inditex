package es.dtr.job.interview.commons.hexagonal.domain.service.crud;

import java.util.List;

/**
 * Interface to define methods to mapping between Entities and DTOs.
 *
 * @param <T> Entity.
 * @param <K> DTO.
 */
public interface CrudMapper<T, K> {

    K entityToDto(T source);

    List<K> entityToDto(List<T> source);

    T dtoToEntity(K source);

    List<T> dtoToEntity(List<K> source);
}
