package org.example.tablesUtil;

import org.example.entities.ProductVO;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductTable {
    public static DefaultTableModel createProductTable(List<ProductVO> product) {
        String[] columnNames = {
                "Qnt",
                "Descrição",
                "Observações",
                "Req",
                "V.Un",
                "S.Total"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (ProductVO productTable : product) {
            Object[] rowData = {
                    productTable.getQnt(),
                    productTable.getDescription(),
                    productTable.getObs(),
                    productTable.getGuestId().getName(),
                    productTable.getUnValue(),
                    productTable.getSubTotal()
            };

            tableModel.addRow(rowData);
        }
        return tableModel;
    }
}
