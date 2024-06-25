package org.example.repositories;

import org.example.entities.ReservationEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ReservationRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");


    public static void updateReservation(ReservationEntity reservation){
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(reservation);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Failed to update reservation", e);
        } finally {
            em.close();
        }
    }

    public static boolean deleteReserve(Integer reservationId) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            ReservationEntity reserve = em.find(ReservationEntity.class, reservationId);
            if (reserve != null) {
                em.remove(reserve);
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

    public static List<ReservationEntity> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("SELECT c FROM ReservationEntity c", ReservationEntity.class).getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Failed to find all reservations", e);
        } finally {
            em.close();
        }
    }
}
