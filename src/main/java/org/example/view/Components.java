package org.example.view;

import org.example.entities.CompanyEntity;
import org.example.services.CompanyService;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class Components {
    public static DefaultComboBoxModel<String> getAllCompanyNames(boolean putTodosInFirstIndexString) {
        List<CompanyEntity> companies = CompanyService.getAll();

        List<String> companyNames = companies.stream()
                .map(CompanyEntity::getName)
                .collect(Collectors.toList());

        if(putTodosInFirstIndexString){
            companyNames.add(0, "   -- todos --");
        } else {
            companyNames.add(0, "");
        }

        return new DefaultComboBoxModel<>(companyNames.toArray(new String[0]));
    }
}
