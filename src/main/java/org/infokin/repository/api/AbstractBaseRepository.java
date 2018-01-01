package org.infokin.repository.api;

import org.hibernate.Session;
import org.infokin.model.core.BaseEntity;
import org.infokin.util.HibernateUtility;

/**
 * Generic implementation of basic repository operations.
 */
public abstract class AbstractBaseRepository<T extends BaseEntity> implements BaseRepository<T> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Session getSession() {
        return HibernateUtility.getSessionFactory().getCurrentSession();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long save(T entity) {
        Session session = getSession();

        session.beginTransaction();

        Long id = (Long) session.save(entity);

        session.getTransaction().commit();

        return id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long update(T entity) {
        return 0L;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(T entity) {
    }
}
