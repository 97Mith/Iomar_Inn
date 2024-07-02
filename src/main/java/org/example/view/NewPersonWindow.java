package org.example.view;

import org.example.entities.BedroomEntity;
import org.example.entities.CompanyEntity;
import org.example.entities.PersonEntity;
import org.example.services.CompanyService;
import org.example.services.PersonService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewPersonWindow extends JFrame {
    private PersonEntity person;
    private PersonManagerWindow parent;
    private JTextField nameField;
    private JTextField surNameField;
    private JComboBox<CompanyEntity> companiesComboBox;
    private BedroomEntity bedroom;
    private JTextField phoneNumberField;
    private JTextField cpfField;
    private boolean isEditMode;

    public NewPersonWindow(PersonEntity person, PersonManagerWindow parent) {
        this.person = person;
        this.parent = parent;
        this.isEditMode = parent.isEditMode();
        System.out.print(this.isEditMode);

        setTitle("Hospede");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        this.setLocationRelativeTo(null);

        initializeComponents();
        populateFields();
    }

    public static void main(String[] args) {
        NewPersonWindow newPersonWindow = new NewPersonWindow(new PersonEntity(), new PersonManagerWindow());
        newPersonWindow.setVisible(true);
    }

    private void initializeComponents() {
        Font font = new Font("Arial", Font.PLAIN, 15);

        nameField = new JTextField();
        nameField.setFont(font);

        surNameField = new JTextField();
        surNameField.setFont(font);

        phoneNumberField = new JTextField();
        phoneNumberField.setFont(font);

        cpfField = new JTextField();
        cpfField.setFont(font);

        companiesComboBox = new JComboBox(Components.getAllCompanyNames());
        companiesComboBox.setSelectedIndex(-1);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { savePerson(); }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));
        panel.add(new JLabel("Nome ou apelido:"));
        panel.add(nameField);
        panel.add(new JLabel("Sobrenome ou apelido:"));
        panel.add(surNameField);
        panel.add(new JLabel("CPF:"));
        panel.add(cpfField);
        panel.add(new JLabel("Telefone:"));
        panel.add(phoneNumberField);
        panel.add(new JLabel("Empresa:"));
        panel.add(companiesComboBox);
        panel.add(saveButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void populateFields() {
        if (person != null) {
            nameField.setText(person.getName());
            surNameField.setText(person.getSurName());
            cpfField.setText(person.getCpf());
            phoneNumberField.setText(person.getPhoneNumber());

            companiesComboBox.setModel(new DefaultComboBoxModel<>(CompanyService.getAll().toArray(new CompanyEntity[0])));
            companiesComboBox.setSelectedItem(-1);
        }
    }

    private void savePerson() {
        if (PersonService.validateFields(nameField.getText(), cpfField.getText(),
                surNameField.getText(), phoneNumberField.getText(), isEditMode)) {

            person.setName(nameField.getText());
            person.setSurName(surNameField.getText());
            person.setCpf(cpfField.getText());
            person.setPhoneNumber(phoneNumberField.getText());
            if(companiesComboBox != null) person.setCompanyEntity((CompanyEntity) companiesComboBox.getSelectedItem());

            PersonService.createOrUpdate(person);
            parent.refreshTable(new ArrayList<>(), false);
            dispose();

        }
    }
}
