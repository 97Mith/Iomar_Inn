package org.example.repositories;

import org.example.entities.BedroomEntity;
import org.example.entities.CompanyEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class BedroomRepository {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");

    public static void createBedroom(BedroomEntity bedroom){
        EntityManager em = emf.createEntityManager();

        try{
            em.getTransaction().begin();
            em.merge(bedroom);
            em.getTransaction().commit();
        }catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public static BedroomEntity findById(Integer bedroomNumber){
        try{
            EntityManager em = emf.createEntityManager();

            return em.find(BedroomEntity.class, bedroomNumber);

        }catch (Exception e) {
            throw e;
        }
    }
    public static Integer getTotalRooms() {
        EntityManager em = emf.createEntityManager();
        try {
            List<BedroomEntity> b = em.createQuery("SELECT c FROM BedroomEntity c", BedroomEntity.class).getResultList();
            return  b.size();
        } finally {
            em.close();
        }
    }
}
