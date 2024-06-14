package org.example.repositories;

import org.example.entities.ProductVO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.List;

public class ProductRepository {
    static EntityManagerFactory emf = Persistence.createEntityManagerFactory("unit-jpa");

    public static void registerProduct(ProductVO product){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(product);
            em.getTransaction().commit();
        }catch (Exception e) {
            throw new RuntimeException("Failed to add products", e);
        } finally {
            em.close();
        }
    }

    public static void deleteProduct(ProductVO product){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            ProductVO managedProduct = em.merge(product);
            em.remove(managedProduct);
            em.getTransaction().commit();
        }catch (Exception e) {
            throw new RuntimeException("Failed to delete product", e);
        } finally {
            em.close();
        }
    }

    public static ProductVO findById(Integer num){
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            ProductVO product = em.find(ProductVO.class, num);
            em.getTransaction().commit();
            return product;
        }catch (Exception e) {
            throw new RuntimeException("Failed to find product by id", e);
        } finally {
            em.close();
        }
    }

    public static List<ProductVO> findAll(Integer roomNum, boolean isLaundry){
        EntityManager em = emf.createEntityManager();
        try{
            return em.createQuery(
                            "SELECT p FROM ProductEntity p WHERE p.bedroomNumber = :roomNum AND p.isLaundry = :isLaundry",
                            ProductVO.class)
                    .setParameter("roomNum", roomNum)
                    .setParameter("isLaundry", isLaundry)
                    .getResultList();
        }catch (Exception e) {
            throw new RuntimeException("Failed to find products in bedroom", e);
        } finally {
            em.close();
        }
    }
}
