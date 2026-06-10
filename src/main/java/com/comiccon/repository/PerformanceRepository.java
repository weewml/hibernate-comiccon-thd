package com.comiccon.repository;

import com.comiccon.entity.Member;
import com.comiccon.entity.Performance;
import com.comiccon.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class PerformanceRepository extends GenericRepository<Performance, Integer> {
    public PerformanceRepository() { super(Performance.class); }

    public List<Performance> findAllWithMembers() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT DISTINCT p FROM Performance p LEFT JOIN FETCH p.members ORDER BY p.id",
                    Performance.class).getResultList();
        }
    }

    public Optional<Performance> findByIdWithMembers(int id) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Performance> result = em.createQuery(
                    "SELECT p FROM Performance p LEFT JOIN FETCH p.members WHERE p.id = :id",
                    Performance.class).setParameter("id", id).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public List<Performance> findByNomination(String nomination) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT DISTINCT p FROM Performance p LEFT JOIN FETCH p.members WHERE p.nomination = :nomination",
                        Performance.class).setParameter("nomination", nomination).getResultList();
        }
    }

    public void addMember(int performanceId, int memberId) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            Performance performance = em.find(Performance.class, performanceId);
            Member member = em.find(Member.class, memberId);
            if (performance != null && member != null) { performance.addMember(member); }
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void removeMember(int performanceId, int memberId) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            Performance performance = em.find(Performance.class, performanceId);
            Member member = em.find(Member.class, memberId);
            if (performance != null && member != null) { performance.removeMember(member); }
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
