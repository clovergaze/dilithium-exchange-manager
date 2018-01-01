package org.infokin.repository;

import org.hibernate.Session;
import org.infokin.model.Purchase;
import org.infokin.repository.api.AbstractBaseRepository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Repository for {@link Purchase} entity.
 */
public class PurchaseRepository extends AbstractBaseRepository<Purchase> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Purchase get(Long id) {
        Session session = getSession();

        session.beginTransaction();

        TypedQuery<Purchase> query = session.createQuery("FROM Purchase WHERE id = :id", Purchase.class);
        query.setParameter("id", id);

        Purchase result = query.getSingleResult();

        session.getTransaction().commit();

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Purchase> getAll() {
        Session session = getSession();

        session.beginTransaction();

        TypedQuery<Purchase> query = session.createQuery("FROM Purchase", Purchase.class);
        List<Purchase> result = query.getResultList();

        session.getTransaction().commit();

        return result;
    }
}
