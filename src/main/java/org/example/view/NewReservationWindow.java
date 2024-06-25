package org.example.view;

import com.toedter.calendar.JDateChooser;
import org.example.entities.BedroomEntity;
import org.example.entities.ReservationEntity;
import org.example.services.BedroomService;
import org.example.services.ReservationService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class NewReservationWindow extends JFrame {

    private ReservationEntity reservation;
    private ReservationManagerWindow parent;
    private boolean editMode = false;
    BedroomEntity bedroom;
    private JTextField reservationNameField;
    private JDateChooser checkInField;
    private JDateChooser checkOutField;

    public NewReservationWindow(ReservationEntity reservation, BedroomEntity bedroom ,ReservationManagerWindow parent) {
        this.reservation = reservation;
        this.parent = parent;
        this.bedroom = bedroom;

        setTitle("Reserva");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        this.setLocationRelativeTo(null);

        initializeComponents();
        populateFields();
    }

    private void initializeComponents() {
        Font font = new Font("Arial", Font.PLAIN, 15);

        reservationNameField = new JTextField(10);
        reservationNameField.setFont(font);

        checkInField = new JDateChooser();
        checkInField.setFont(font);
        checkInField.setDateFormatString("yyyy-MM-dd");

        checkOutField = new JDateChooser();
        checkOutField.setFont(font);
        checkOutField.setDateFormatString("yyyy-MM-dd");


        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveReservation();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Nome da Reserva:"));
        panel.add(reservationNameField);
        panel.add(new JLabel("Data de Check-In:"));
        panel.add(checkInField);
        panel.add(new JLabel("Data de Check-Out:"));
        panel.add(checkOutField);
        panel.add(saveButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void populateFields() {
        if (reservation != null) {
            reservationNameField.setText(reservation.getReservationName());
            checkInField.setDate(reservation.getCheckIn());
            checkOutField.setDate(reservation.getCheckOut());

        }
    }

    private void saveReservation() {
        if (ReservationService.validateFields(
                reservationNameField.getText(),
                checkInField.getDate(),
                checkOutField.getDate()
        )) {

            reservation.setReservationName(reservationNameField.getText());
            reservation.setCheckIn(checkInField.getDate());
            reservation.setCheckOut(checkOutField.getDate());
            bedroom.setStatus("Reservado");
            reservation.setBedroom(bedroom);

            ReservationService.updateReservation(reservation);
            BedroomService.updateStatus(bedroom, "Reservado p/" + reservationNameField.getText() + " p/ "+ checkInField.getDate());
            dispose();

        } else {
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos corretamente.");
        }
    }
}
