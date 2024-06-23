package org.example.view;

import javax.swing.*;
import java.awt.*;

public class AddItemsWindow extends JFrame{
    public AddItemsWindow(){
        JTextField quantidadeField = new JTextField(5);
        JTextField descricaoField = new JTextField(30);
        JTextField observacaoField = new JTextField(20);
        JTextField valorUnitarioField = new JTextField(10);

        String[] requerentes = {"Requerente 1", "Requerente 2", "Requerente 3"};
        JComboBox<String> requerenteComboBox = new JComboBox<>(requerentes);

        JButton okButton = new JButton("OK");

        // Set layout
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
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddItemsWindow());
    }

}