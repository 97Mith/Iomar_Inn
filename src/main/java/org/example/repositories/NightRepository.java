package org.example.repositories;

import org.example.entities.BedroomEntity;
import org.example.entities.NightEntity;
import org.example.entities.ReservationEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class NightRepository {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");
    public static void create(NightEntity nightEntity){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(nightEntity);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
    public static boolean delete(Integer id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            NightEntity night = em.find(NightEntity.class, id);
            if (night != null) {
                em.remove(night);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to delete reserve", e);
        } finally {
            em.close();
        }
    }

    public static List<NightEntity> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM NightEntity c", NightEntity.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all nights", e);
        } finally {
            em.close();
        }
    }
}
