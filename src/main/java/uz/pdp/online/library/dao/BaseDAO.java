package uz.pdp.online.library.dao;

import jakarta.persistence.*;
import lombok.NonNull;
import uz.pdp.online.library.entity.Auditable;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.function.Function;

public abstract class BaseDAO<T extends Auditable, ID extends Serializable> {
    protected static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("library");

    private final Class<T> persistenceClass;

    @SuppressWarnings("unchecked")
    protected BaseDAO() {
        this.persistenceClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
    }

    public T save(T entity) {
        return executeTransaction(em -> {
            em.persist(entity);
            return entity;
        });
    }

    public boolean update(T entity) {
        return executeTransaction(em -> {
            em.merge(entity);
            return true;
        });
    }

    public T findById(@NonNull ID id) {
        return executeQuery(em -> em.find(persistenceClass, id));
    }

    public List<T> findAll() {
        return executeQuery(em -> em.createQuery("from " + persistenceClass.getSimpleName(), persistenceClass).getResultList());
    }

    public boolean deleteById(@NonNull ID id) {
        return executeTransaction(em -> {
            T entity = em.find(persistenceClass, id);
            if (entity != null) {
                em.remove(entity);
                return true;
            }
            return false;
        });
    }

    /** JPQL SELECT Query (dynamic) */
    public List<T> findByQuery(String jpql, Object... params) {
        return executeQuery(em -> {
            TypedQuery<T> query = em.createQuery(jpql, persistenceClass);
            setQueryParameters(query, params);
            return query.getResultList();
        });
    }

    /** Native SQL SELECT Query */
    @SuppressWarnings("unchecked")
    public List<Object[]> findByNativeQuery(String sql, Object... params) {
        return executeQuery(em -> {
            Query query = em.createNativeQuery(sql);
            setQueryParameters(query, params);
            return query.getResultList();
        });
    }

    /** UPDATE / DELETE Query */
    public int executeUpdateQuery(String jpql, Object... params) {
        return executeTransaction(em -> {
            Query query = em.createQuery(jpql);
            setQueryParameters(query, params);
            return query.executeUpdate();
        });
    }

    /** Transactional Execution */
    private <R> R executeTransaction(Function<EntityManager, R> action) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            R result = action.apply(em);
            tx.commit();
            return result;
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /** Query Execution (SELECT) */
    private <R> R executeQuery(Function<EntityManager, R> action) {
        EntityManager em = emf.createEntityManager();
        try {
            return action.apply(em);
        } finally {
            em.close();
        }
    }

    /** Add parameter */
    private void setQueryParameters(Query query, Object... params) {
        for (int i = 0; i < params.length; i++) {
            query.setParameter(i + 1, params[i]); // 1-based index
        }
    }
}
