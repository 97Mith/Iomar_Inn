package org.example.services;

import org.example.entities.BedroomEntity;
import org.example.entities.NightEntity;
import org.example.entities.ReservationEntity;
import org.example.repositories.NightRepository;
import org.example.repositories.ReservationRepository;
import org.example.view.NightsManagerWindow;
import org.example.view.ReservationManagerWindow;

import javax.swing.*;
import java.util.List;

public class NightService {
    public static boolean updateReservation(NightEntity nightEntity) {
        try {
            NightRepository.create(nightEntity);
            JOptionPane.showMessageDialog(
                    null, "Reserva criada!.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Erro!",
                    "Aviso", JOptionPane.ERROR_MESSAGE
            );
            return  false;
        }
    }

    public static boolean delete(Integer reservationId, NightsManagerWindow parent) {
        try{
            ReservationRepository.deleteReserve(reservationId);
            JOptionPane.showMessageDialog(
                    null, "Fatura deletada!.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE
            );
            parent.refreshTable();

            return true;
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Erro ao criar ou editar fatura.",
                    "Aviso", JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public static List<NightEntity> getAll(){
        try{
            return NightRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Erro ao carregar faturas.",
                    "Aviso", JOptionPane.ERROR_MESSAGE
            );
            return null;
        }
    }
}
