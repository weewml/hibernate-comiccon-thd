package com.comiccon.repository;

import com.comiccon.entity.Mark;
import com.comiccon.util.HibernateUtil;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

public class MarkRepository extends GenericRepository<Mark, Integer>{

    public MarkRepository() { super(Mark.class); }

    public List<Mark> findAllWithDetails() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Mark m JOIN FETCH m.performance JOIN FETCH m.criterion " +
                            "JOIN FETCH m.jury ORDER BY m.id", Mark.class).getResultList();
        }
    }

    public Optional<Mark> findByIdWithDetails(int id) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Mark> result = em.createQuery(
                    "FROM Mark m JOIN FETCH m.performance JOIN FETCH m.criterion JOIN FETCH m.jury " +
                            "WHERE m.id = :id", Mark.class).setParameter("id", id).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public List<Mark> findByPerformanceIdWithDetails(int performanceId) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "FROM Mark m JOIN FETCH m.performance JOIN FETCH m.criterion JOIN FETCH m.jury " +
                            "WHERE m.performance.id = :performanceId", Mark.class)
                    .setParameter("performanceId", performanceId).getResultList();
        }
    }
}
