package org.example.services;

import org.example.entities.BedroomEntity;
import org.example.entities.ProductVO;
import org.example.repositories.ProductRepository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService {

    public static void insertProduct(ProductVO productEntity){
        try{
            ProductRepository.registerProduct(productEntity);
            JOptionPane.showMessageDialog(
                    null, "Item adicionado..",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE
            );
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Erro ao inserir ítem.",
                    "Aviso", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static List<ProductVO> getProductsInRoom(BedroomEntity bedroomNum, boolean isLaundry) {
        List<ProductVO> products = new ArrayList<>();
        try {
            List<ProductVO> foundProducts = ProductRepository.findAll(bedroomNum, isLaundry);
            if (foundProducts != null) {
                products = foundProducts;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }
    public static void removeAll(BedroomEntity bedroom){
        List<ProductVO> pp = getProductsInRoom(bedroom, false);
        List<ProductVO> pl = getProductsInRoom(bedroom, true);
        for(ProductVO p : pp){
            ProductRepository.deleteProduct(p);
        }
        for(ProductVO p : pl){
            ProductRepository.deleteProduct(p);
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

    public static boolean validateFields(String qnt, String description, String obs, String unValue){
        boolean valid = Util.isNullOrEmpty(qnt, "campo Quantidade");
        if(valid) valid = Util.minMaxStringSize(1, 2, qnt, "campo Quantidade");

        if(valid) valid = Util.isNullOrEmpty(description, "campo Descrição");
        if(valid) valid = Util.minMaxStringSize(3, 20, description, "campo Descrição");

        if(valid) valid = Util.isNullOrEmpty(unValue, "Valor Unitário");
        if(valid) valid = Util.minMaxStringSize(1, 4, unValue, "Valor Unitário");

        if(valid && !obs.isEmpty()) valid = Util.minMaxStringSize(3, 60, obs, "campo Observação");
        return valid;
    }

    public static double calculateTotalSubTotal(List<ProductVO> productList) {
        double totalSubTotal = 0.0;

        for (ProductVO product : productList) {
            totalSubTotal += product.getSubTotal();
        }

        return totalSubTotal;
    }
}


