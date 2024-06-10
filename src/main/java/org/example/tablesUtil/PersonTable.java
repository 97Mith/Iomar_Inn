package org.example.tablesUtil;

import org.example.entities.PersonEntity;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class PersonTable {
    public static DefaultTableModel createPeopleTable(List<PersonEntity> people) {
        String[] columnNames = {
                "ID",//1
                "Nome",//2
                "Sobrenome",//3
                "Telefone",//4
                "Quarto Hospedado",//5
                "Empresa",
                "CPF"
        };

        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (PersonEntity peopleTable : people) {
            Object[] rowData = {
                    peopleTable.getId(),//1
                    peopleTable.getName(),//2
                    peopleTable.getSurName(),//3
                    peopleTable.getPhoneNumber(),//4
                    peopleTable.getBedroom(),//5
                    peopleTable.getCompany(),
                    peopleTable.getCpf()
            };

            tableModel.addRow(rowData);
        }
        return tableModel;
    }
}