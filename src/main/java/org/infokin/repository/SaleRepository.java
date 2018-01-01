package org.infokin.repository;

import org.hibernate.Session;
import org.infokin.model.Sale;
import org.infokin.repository.api.AbstractBaseRepository;

import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Repository for {@link Sale} entity.
 */
public class SaleRepository extends AbstractBaseRepository<Sale> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Sale get(Long id) {
        Session session = getSession();

        session.beginTransaction();

        TypedQuery<Sale> query = session.createQuery("FROM Sale WHERE id = :id", Sale.class);
        query.setParameter("id", id);

        Sale result = query.getSingleResult();

        session.getTransaction().commit();

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Sale> getAll() {
        Session session = getSession();

        session.beginTransaction();

        TypedQuery<Sale> query = session.createQuery("FROM Sale", Sale.class);
        List<Sale> result = query.getResultList();

        session.getTransaction().commit();

        return result;
    }
}
