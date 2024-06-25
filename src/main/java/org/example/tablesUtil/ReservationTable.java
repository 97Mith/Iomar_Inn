package org.example.tablesUtil;

import org.example.entities.CompanyEntity;
import org.example.entities.ReservationEntity;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ReservationTable {
    public static DefaultTableModel createReservationTable(List<ReservationEntity> reservationList) {
        String[] columnNames = {
                "ID", "Nome da reserva",
                "Quarto", "Check In",
                "CheckOut"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (ReservationEntity reserve : reservationList) {
            Object[] rowData = {
                    reserve.getId(), reserve.getReservationName(),
                    reserve.getBedroom().getId(), reserve.getCheckIn(),
                    reserve.getCheckOut()
            };

            tableModel.addRow(rowData);
        }
        return tableModel;
    }
}
