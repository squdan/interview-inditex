package es.dtr.job.interview.commons.hexagonal.infrastructure.database;

public interface MergeableEntity<T> {

    /**
     * Updates current entity with received changes.
     *
     * @param changes: entity changes.
     */
    T merge(final T changes);

}
