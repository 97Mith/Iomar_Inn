package org.example.tablesUtil;

import org.example.entities.NightEntity;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class NightsTable {
    public static DefaultTableModel createTable(List<NightEntity> nights) {
        String[] columnNames = {
                "ID",          // 1
                "Responsável",        // 2
                "Num Acomodação",   // 3
                "Situação",    // 4
                "Total em produtos", // 5
                "Total em Lavanderia",
                "Total em Diárias",
                "Total Geral",
                "Observações"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (NightEntity nightTable : nights) {
            Object[] rowData = {
                    nightTable.getId(),
                    nightTable.getCompany().getName().equals("-- sem empresa --") ? nightTable.getClient().getName() : nightTable.getCompany().getName(),
                    nightTable.getBedroom().getId(),
                    nightTable.isPaid() ? "Pago" : "Pendente",
                    nightTable.getProductsValue(),
                    nightTable.getLaundryValue(),
                    nightTable.getBedroom().getValue(),
                    nightTable.getTotal(),
                    nightTable.getObs()

            };
            tableModel.addRow(rowData);
        }
        return tableModel;
    }
}
