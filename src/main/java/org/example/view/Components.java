package org.example.view;

import org.example.entities.BedroomEntity;
import org.example.entities.CompanyEntity;
import org.example.entities.PersonEntity;
import org.example.services.CompanyService;
import org.example.services.PersonService;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class Components {
    public static DefaultComboBoxModel<CompanyEntity> getAllCompanyNames() {
        List<CompanyEntity> companies = CompanyService.getAll();
        return new DefaultComboBoxModel<>(companies.toArray(new CompanyEntity[0]));
    }

    public static DefaultComboBoxModel<PersonEntity> getGuests(BedroomEntity bedroom) {
        List<PersonEntity> guests = PersonService.getByBedroom(bedroom);
        return new DefaultComboBoxModel<>(guests.toArray(new PersonEntity[0]));
    }
}
