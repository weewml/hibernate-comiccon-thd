package com.comiccon.repository;

import com.comiccon.entity.Fest;
import com.comiccon.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

public class FestRepository extends GenericRepository<Fest, Integer> {
    public FestRepository() { super(Fest.class); }

    public List<Fest> findAllWithDetails() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT f FROM Fest f LEFT JOIN FETCH f.performance " +
                            "LEFT JOIN FETCH f.company LEFT JOIN FETCH f.volunteer " +
                            "ORDER BY f.date", Fest.class).getResultList();
        }
    }

    public Optional<Fest> findByIdWithDetails(int id) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Fest> result = em.createQuery(
                    "SELECT f FROM Fest f LEFT JOIN FETCH f.performance " +
                            "LEFT JOIN FETCH f.company LEFT JOIN FETCH f.volunteer " +
                            "WHERE f.id = :id", Fest.class).setParameter("id", id).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public List<Fest> findByDate(OffsetDateTime from, OffsetDateTime to) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT f FROM Fest f LEFT JOIN FETCH f.performance " +
                            "LEFT JOIN FETCH f.company LEFT JOIN FETCH f.volunteer " +
                            "WHERE f.date BETWEEN :from AND :to ORDER BY f.date", Fest.class)
                    .setParameter("from", from).setParameter("to", to).getResultList();
        }
    }
}
