package org.example.tablesUtil;

import org.example.entities.CompanyEntity;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CompanyTable {
    public static DefaultTableModel createCompanyTable(List<CompanyEntity> companyList) {
        String[] columnNames = {
                "ID", "Nome", "Razão Social", "Telefone",
                "CNPJ", "Inscrição Estadual", "Email"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (CompanyEntity company : companyList) {
            Object[] rowData = {
                    company.getId(), company.getName(), company.getCorporateReason(), company.getPhoneNumber(),
                    company.getCnpj(), company.getStateInscription(), company.getEmail()
            };

            tableModel.addRow(rowData);
        }
        return tableModel;
    }
}
