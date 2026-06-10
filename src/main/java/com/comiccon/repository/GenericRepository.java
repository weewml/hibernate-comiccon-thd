package com.comiccon.repository;

import com.comiccon.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class GenericRepository<T, ID> {

    private final Class<T> entityClass;

    public GenericRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public List<T> findAll() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                    .getResultList();
        }
    }

    public Optional<T> findById(ID id) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return Optional.ofNullable(em.find(entityClass, id));
        }
    }

    public T save(T entity) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            em.persist(entity);
            et.commit();
            return entity;
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public T update(T entity) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            T merged = em.merge(entity);
            et.commit();
            return merged;
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public boolean deleteById(ID id) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
                et.commit();
                return true;
            }
            et.rollback();
            return false;
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public long count() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class)
                    .getSingleResult();
        }
    }

    public boolean existsById(ID id) {return findById(id).isPresent(); }

    public Class<T> getEntityClass() { return entityClass; }
}
