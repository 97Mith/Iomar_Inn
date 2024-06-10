package org.example.view;

import org.example.entities.CompanyEntity;
import org.example.services.CompanyService;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class Components {
    public static DefaultComboBoxModel<CompanyEntity> getAllCompanyNames() {
        List<CompanyEntity> companies = CompanyService.getAll();
        assert companies != null;
        companies.add(0, new CompanyEntity());

        return new DefaultComboBoxModel<>(companies.toArray(new CompanyEntity[0]));
    }
}
