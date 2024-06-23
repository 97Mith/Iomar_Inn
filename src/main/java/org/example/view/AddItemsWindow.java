package org.example.view;

import org.example.entities.BedroomEntity;
import org.example.entities.PersonEntity;
import org.example.services.BedroomService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AddItemsWindow extends JFrame{
    public AddItemsWindow(BedroomEntity bedroom, boolean isLaundry){
        JTextField quantidadeField = new JTextField(5);
        JTextField descricaoField = new JTextField(30);
        JTextField observacaoField = new JTextField(20);
        JTextField valorUnitarioField = new JTextField(10);

        JComboBox<PersonEntity> requerenteComboBox = new JComboBox<>(Components.getGuests(bedroom));

        JButton okButton = new JButton("OK");

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Quantidade:"), gbc);

        gbc.gridx = 1;
        add(quantidadeField, gbc);

        gbc.gridx = 2;
        add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 3;
        add(descricaoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Valor Unitário: R$"), gbc);

        gbc.gridx = 1;
        add(valorUnitarioField, gbc);

        gbc.gridx = 2;
        add(new JLabel("Observação:"), gbc);

        gbc.gridx = 3;
        add(observacaoField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        add(new JLabel("Requerente:"), gbc);

        gbc.gridy = 3;
        add(requerenteComboBox, gbc);

        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        add(okButton, gbc);

        setTitle("Adicionar ítem");
        setSize(800, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        BedroomEntity b = BedroomService.getById(1);
        SwingUtilities.invokeLater(() -> new AddItemsWindow(b, false));
    }

}