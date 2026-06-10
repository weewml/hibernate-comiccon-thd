package com.comiccon.repository;

import com.comiccon.entity.Artist;
import com.comiccon.entity.Company;
import com.comiccon.util.HibernateUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

import java.util.List;
import java.util.Optional;

public class CompanyRepository extends GenericRepository<Company, Integer> {

    public CompanyRepository() { super(Company.class); }

    public List<Company> findAllWithArtists() {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            return em.createQuery(
                    "SELECT DISTINCT c FROM Company c LEFT JOIN FETCH c.artists ORDER BY c.id",
                    Company.class).getResultList();
        }
    }

    public Optional<Company> findByIdWithArtists(int id) {
        try (EntityManager em = HibernateUtil.createEntityManager()) {
            List<Company> result = em.createQuery(
                    "SELECT c FROM Company c LEFT JOIN FETCH c.artists WHERE c.id = :id",
                    Company.class).setParameter("id", id).getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        }
    }

    public void addArtist(int companyId, int artistId) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            Company company = em.find(Company.class, companyId);
            Artist artist = em.find(Artist.class, artistId);
            if (company != null && artist != null) { company.addArtist(artist); }
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void removeArtist(int companyId, int artistId) {
        EntityManager em = HibernateUtil.createEntityManager();
        EntityTransaction et = em.getTransaction();
        try {
            et.begin();
            Company company = em.find(Company.class, companyId);
            Artist artist = em.find(Artist.class, artistId);
            if (company != null && artist != null) {company.removeArtist(artist);}
            et.commit();
        } catch (Exception e) {
            if (et.isActive()) et.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
