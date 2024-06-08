package org.example.tablesUtil;

import org.example.entities.PersonEntity;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PersonTable {
    public static DefaultTableModel createPeopleTable(List<PersonEntity> people) {
        String[] columnNames = {
                "ID",
                "Nome",
                "Sobrenome",
                "Telefone",
                "Quarto hospedado",
                "Empresa",
                "CPF"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (PersonEntity peopleTable : people) {
            Object[] rowData = {
                    peopleTable.getId(),
                    peopleTable.getName(),
                    peopleTable.getSurName(),
                    peopleTable.getPhoneNumber(),
                    peopleTable.getBedroom(),
                    peopleTable.getCompany(),
                    peopleTable.getCpf()
            };

            tableModel.addRow(rowData);
        }
        return tableModel;
    }
}
