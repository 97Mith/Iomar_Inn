package org.example.services;

import org.example.entities.ProductVO;
import org.example.repositories.ProductRepository;

import javax.swing.*;
import java.util.List;

public class ProductService {

    public static void insertProduct(ProductVO productEntity){
        try{
            ProductRepository.registerProduct(productEntity);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Erro ao inserir ítem.",
                    "Aviso", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static List<ProductVO> getProductsInRoom(Integer bedroomNum, boolean isLaundry){
        try{
            return ProductRepository.findAll(bedroomNum, isLaundry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static ProductVO getByRegister(Integer register){
        try{
            return ProductRepository.findById(register);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Erro ao buscar ítem.",
                    "Aviso", JOptionPane.ERROR_MESSAGE
            );
            return null;
        }
    }

    public static void delete(ProductVO product){
        try{
            ProductRepository.deleteProduct(product);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Erro ao excluir ítem.",
                    "Aviso", JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
