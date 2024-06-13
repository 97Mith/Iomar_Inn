package org.example.services;

import org.example.entities.BedroomEntity;
import org.example.entities.PersonEntity;
import org.example.repositories.BedroomRepository;
import org.example.repositories.PersonRepository;

import javax.swing.*;
import java.util.List;

public class BedroomService {
    private static  boolean newBedroom(BedroomEntity bedroom){
        try{
            BedroomRepository.createBedroom(bedroom);
            JOptionPane.showMessageDialog(null,
                    "Quarto criado com sucesso",
                    "Aviso",
                    JOptionPane.INFORMATION_MESSAGE);
            return true;
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,
                    "Erro ao criar novo quarto!",
                    "Aviso",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private static void standardCreate(){
        for(int i = 1; i <= 3; i++){
            newBedroom(new BedroomEntity(4, true, "Disponível", 150));
        }
        for(int i = 1; i < 3; i++){
            newBedroom(new BedroomEntity(4, false, "Disponível", 150));
        }
        newBedroom(new BedroomEntity(2, false, "Disponível", 90));
    }

    public static List<PersonEntity> loadAllInBedroom(BedroomEntity bedroomNumber){
        try{
            List<PersonEntity> guests = PersonRepository.findByBedroom(bedroomNumber);
            return guests;
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Erro ao carregar pessoas.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE
            );
            return null;
        }
    }

    public static BedroomEntity getById(Integer id){
        try{
            return BedroomRepository.findById(id);
        }catch (Exception e){
            JOptionPane.showMessageDialog(
                    null, "Erro ao carregar quartos.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE
            );
            e.printStackTrace();
            return null;
        }
    }
}
