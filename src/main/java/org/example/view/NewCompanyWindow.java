package org.example.view;

import org.example.entities.CompanyEntity;
import org.example.services.CompanyService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewCompanyWindow extends JFrame {

    private CompanyEntity company;
    private CompanyManagerWindow parent;

    private JTextField nameField;
    private JTextField corporateReasonField;
    private JTextField cnpjField;
    private JTextField stateInscriptionField;
    private JTextField phoneNumberField;
    private JTextField emailField;

    public NewCompanyWindow(CompanyEntity company, CompanyManagerWindow parent) {
        this.company = company;
        this.parent = parent;

        setTitle("Editar Empresa");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initializeComponents();
        populateFields();
    }

    private void initializeComponents() {
        nameField = new JTextField(20);
        corporateReasonField = new JTextField(20);
        cnpjField = new JTextField(20);
        stateInscriptionField = new JTextField(20);
        phoneNumberField = new JTextField(20);
        emailField = new JTextField(20);

        JButton saveButton = new JButton("Salvar");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCompany();
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));
        panel.add(new JLabel("Nome:"));
        panel.add(nameField);
        panel.add(new JLabel("Razão Social:"));
        panel.add(corporateReasonField);
        panel.add(new JLabel("CNPJ:"));
        panel.add(cnpjField);
        panel.add(new JLabel("Inscrição Estadual:"));
        panel.add(stateInscriptionField);
        panel.add(new JLabel("Telefone:"));
        panel.add(phoneNumberField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(saveButton);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    private void populateFields() {
        if (company != null) {
            nameField.setText(company.getName());
            corporateReasonField.setText(company.getCorporateReason());
            cnpjField.setText(company.getCnpj());
            stateInscriptionField.setText(company.getStateInscription());
            phoneNumberField.setText(company.getPhoneNumber());
            emailField.setText(company.getEmail());
        }
    }

    private void saveCompany() {
        if (CompanyService.validateFields(nameField.getText(), corporateReasonField.getText(),
                cnpjField.getText(), stateInscriptionField.getText(),
                phoneNumberField.getText(), emailField.getText())) {

            company.setName(nameField.getText());
            company.setCorporateReason(corporateReasonField.getText());
            company.setCnpj(cnpjField.getText());
            company.setStateInscription(stateInscriptionField.getText());
            company.setPhoneNumber(phoneNumberField.getText());
            company.setEmail(emailField.getText());

            if (CompanyService.createOrUpdate(company)) {
                parent.refreshTable();
                dispose();
            }
        }
    }
}
