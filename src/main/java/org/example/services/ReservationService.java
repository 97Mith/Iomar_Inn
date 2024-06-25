package org.example.services;

import org.example.entities.BedroomEntity;
import org.example.entities.ReservationEntity;
import org.example.repositories.ReservationRepository;
import org.example.view.ReservationManagerWindow;

import javax.swing.*;
import java.util.Date;
import java.util.List;

public class ReservationService {

    public static boolean updateReservation(ReservationEntity reservation) {
        try {
            ReservationRepository.updateReservation(reservation);
            JOptionPane.showMessageDialog(
                    null, "Reserva criada!.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Erro ao criar ou editar ítem.",
                    "Aviso", JOptionPane.ERROR_MESSAGE
            );
            return  false;
        }
    }

    public static boolean deleteReserve(Integer reservationId, ReservationManagerWindow parent, BedroomEntity bedroom) {
        try{
            ReservationRepository.deleteReserve(reservationId);
            JOptionPane.showMessageDialog(
                    null, "Reserva deletada!.",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE
            );
            BedroomService.updateStatus(bedroom, "Disponível");
            parent.refreshTable();

            return true;
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Erro ao criar ou editar reserva.",
                    "Aviso", JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
    }

    public static List<ReservationEntity> getAll(){
        try{
            return ReservationRepository.findAll();
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                    null, "Erro ao carregar reservas.",
                    "Aviso", JOptionPane.ERROR_MESSAGE
            );
            return null;
        }
    }
    public static boolean validateFields(String reservationName, Date checkIn, Date checkOut) {
        if (!Util.minMaxStringSize(3, 20, reservationName, "Campo Nome da Reserva")) {
            return false;
        }
        if (checkIn == null) {
            return false;
        }
        Date today = new Date();
        if (checkIn.before(today)) {
            return false;
        }
        if (checkOut != null && checkOut.before(checkIn)) {
            return false;
        }
        return true;
    }
}
