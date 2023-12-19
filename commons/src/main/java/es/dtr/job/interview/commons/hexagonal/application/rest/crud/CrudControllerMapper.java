package es.dtr.job.interview.commons.hexagonal.application.rest.crud;

import java.util.List;

/**
 * Interface to define methods to mapping between Entities and DTOs.
 *
 * @param <T> DTO.
 * @param <K> Domain Entity.
 */
public interface CrudControllerMapper<T, K> {

    // Source: DTO Entity
    K dtoToDomainEntity(T source);

    List<K> dtoToDomainEntity(List<T> source);

    // Source: Domain Entity
    T domainEntityToDto(K source);

    List<T> domainEntityToDto(List<K> source);
}
