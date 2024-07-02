package org.example.repositories;

import org.example.entities.BedroomEntity;
import org.example.entities.CompanyEntity;
import org.example.entities.PersonEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.List;

public class PersonRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");

    public static void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }

    public static void updatePerson(PersonEntity guest) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(guest);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to update person", e);
        } finally {
            em.close();
        }
    }
    public static boolean isCpfExists(String cpf, boolean isEditMode) {
        EntityManager em = emf.createEntityManager();
        try {
            String query = "SELECT COUNT(c) FROM PersonEntity c WHERE c.cpf = :cpf";
            Long count = em.createQuery(query, Long.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
            if(isEditMode) return count > 1;
            return count > 0;
        } finally {
            em.close();
        }
    }

    public static boolean deletePerson(Integer personId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            PersonEntity person = em.find(PersonEntity.class, personId);
            if (person != null) {
                em.remove(person);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to delete person", e);
        } finally {
            em.close();
        }
    }

    public static void insertOrRemoveBedroom(PersonEntity person, BedroomEntity bedroom) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            person.setBedroom(bedroom);
            em.merge(person);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to insert or remove bedroom", e);
        } finally {
            em.close();
        }
    }

    public static PersonEntity findById(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(PersonEntity.class, id);
        } catch (Exception e) {
            throw new RuntimeException("Failed to find person by id", e);
        } finally {
            em.close();
        }
    }

    public static List<PersonEntity> getbyName(String name) {
        EntityManager em = emf.createEntityManager();
        try {
            String queryStr = "SELECT c FROM PersonEntity c WHERE c.name LIKE :name";
            TypedQuery<PersonEntity> query = em.createQuery(queryStr, PersonEntity.class);
            query.setParameter("name", "%" + name + "%");
            return query.getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find person by name", e);
        } finally {
            em.close();
        }
    }

    public static List<PersonEntity> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM PersonEntity c", PersonEntity.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all persons", e);
        } finally {
            em.close();
        }
    }

    public static List<PersonEntity> getByCompany(CompanyEntity company) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM PersonEntity p WHERE p.company = :company", PersonEntity.class)
                    .setParameter("company", company)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find persons by company", e);
        } finally {
            em.close();
        }
    }

    public static List<PersonEntity> findByBedroom(BedroomEntity bedroom) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT p FROM PersonEntity p WHERE p.bedroom = :bedroom", PersonEntity.class)
                    .setParameter("bedroom", bedroom)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find persons by bedroom", e);
        } finally {
            em.close();
        }
    }
}
