package org.example.repositories;

import org.example.entities.CompanyEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class CompanyRepository {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");

    public static void update(CompanyEntity company) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(company);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public static CompanyEntity findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(CompanyEntity.class, id);
        } finally {
            em.close();
        }
    }

    public static List<CompanyEntity> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM CompanyEntity c", CompanyEntity.class).getResultList();
        } finally {
            em.close();
        }
    }

    public static boolean isCnpjExists(String cnpj) {
        EntityManager em = emf.createEntityManager();
        try {
            String query = "SELECT COUNT(c) FROM CompanyEntity c WHERE c.cnpj = :cnpj";
            Long count = em.createQuery(query, Long.class)
                    .setParameter("cnpj", cnpj)
                    .getSingleResult();
            return count > 1;
        } finally {
            em.close();
        }
    }
    public static boolean isCrExists(String corporateReason) {
        EntityManager em = emf.createEntityManager();
        try {
            String query = "SELECT COUNT(c) FROM CompanyEntity c WHERE c.corporateReason = :corporateReason";
            Long count = em.createQuery(query, Long.class)
                    .setParameter("corporateReason", corporateReason)
                    .getSingleResult();
            return count > 1;
        } finally {
            em.close();
        }
    }

    public static List<CompanyEntity> findByName(String name) {
        try {
            EntityManager em = emf.createEntityManager();

            String queryStr = "SELECT c FROM CompanyEntity c WHERE c.name LIKE :name";
            TypedQuery<CompanyEntity> query = em.createQuery(queryStr, CompanyEntity.class);
            query.setParameter("name", "%" + name + "%");

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void delete(Integer companyId) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        CompanyEntity company = em.find(CompanyEntity.class, companyId);
        try{
            if (company != null) {
                em.remove(company);
                em.getTransaction().commit();

            } else {
                em.getTransaction().rollback();
            }
        }finally {
            em.close();
        }
    }
}
