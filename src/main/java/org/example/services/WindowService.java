package org.example.services;

import com.toedter.calendar.JDateChooser;
import org.example.entities.BedroomEntity;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class WindowService {
    private static int getDifferenceInDays(JDateChooser dateCheckIn, JDateChooser dateCheckOut) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

        LocalDate checkInDate = LocalDate.parse(new SimpleDateFormat("d/MM/yyyy").format(dateCheckIn.getDate()), formatter);
        LocalDate checkOutDate = LocalDate.parse(new SimpleDateFormat("d/MM/yyyy").format(dateCheckOut.getDate()), formatter);

        long daysBetween = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

        return (int) daysBetween;
    }

    private static double calculateTotalValue(double dailyRate, int daysDifference, double discountPerDay) {
        // Calcular o valor total sem desconto
        double totalValue = dailyRate * daysDifference;

        // Aplicar o desconto
        double discount = discountPerDay * daysDifference;
        double totalValueWithDiscount = totalValue - discount;

        return totalValueWithDiscount;
    }

    public static void addDateChangeListener(BedroomEntity bedroom, JDateChooser dateCheckIn, JDateChooser dateCheckOut, double dailyRate, JTextField discountPerDay, JFrame frame, JLabel total) {
        dateCheckOut.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if ("date".equals(evt.getPropertyName())) {
                    if (dateCheckIn.getDate() != null && dateCheckOut.getDate() != null) {
                        if (dateCheckOut.getDate().after(dateCheckIn.getDate())) {
                            double discount = getValue(discountPerDay);
                            int difference = getDifferenceInDays(dateCheckIn, dateCheckOut);
                            double totalValue = calculateTotalValue(dailyRate, difference, discount);
                            BedroomService.setDates(bedroom, dateCheckIn.getDate(), dateCheckOut.getDate(), totalValue);
                            total.setText("R$ "+ totalValue);
                            JOptionPane.showMessageDialog(frame, "Total diárias: " + difference + "\nValor total com desconto: " + totalValue);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Check-out deve ser uma data depois do check-in.");
                        }
                    }
                }
            }
        });
    }
    public static void updateLabel(JLabel valueLabel, String text) {
        valueLabel.setText(text);
    }

    public static double getValue(JTextField textField){
        try{
            return Double.parseDouble(textField.getText());
        }catch (Exception e){
            return 0;
        }
    }
}
