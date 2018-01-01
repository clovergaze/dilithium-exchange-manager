package org.infokin.repository.api;

import org.hibernate.Session;
import org.infokin.model.core.BaseEntity;

import java.util.List;

/**
 * Generic repository interface.
 */
public interface BaseRepository<T extends BaseEntity> {

    /**
     * Get a database session.
     *
     * @return A database session.
     */
    Session getSession();

    /**
     * Persist an entity.
     *
     * @param entity An instance of {@link BaseEntity}
     * @return The index of the newly created entity.
     */
    Long save(T entity);

    /**
     * Update an existing entity.
     *
     * @param entity An instance of {@link BaseEntity}
     * @return The index of the updated entity.
     */
    Long update(T entity);

    /**
     * Delete an entity.
     *
     * @param entity An instance of type {@link BaseEntity}
     */
    void delete(T entity);

    /**
     * Returns an entity by its identifier.
     *
     * @param id The entities identifier.
     * @return An instance of entity.
     */
    T get(Long id);

    /**
     * Returns all entities of given type.
     *
     * @return A list of entities.
     */
    List<T> getAll();
}
