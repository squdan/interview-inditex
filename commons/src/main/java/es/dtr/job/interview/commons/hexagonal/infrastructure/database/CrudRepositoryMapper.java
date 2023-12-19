package es.dtr.job.interview.commons.hexagonal.infrastructure.database;

import java.util.List;

/**
 * Interface to define methods to mapping between Entities and DTOs.
 *
 * @param <T> Domain Entity.
 * @param <K> Infrastructure Entity.
 */
public interface CrudRepositoryMapper<T, K> {

    // Source: Entity Domain
    K entityDomainToEntityInfra(T source);

    List<K> entityDomainToEntityInfra(List<T> source);

    // Source: Entity Infra
    T entityInfraToEntityDomain(K source);

    List<T> entityInfraToEntityDomain(List<K> source);
}
