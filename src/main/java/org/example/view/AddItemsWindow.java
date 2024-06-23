package org.example.view;

import org.example.entities.BedroomEntity;
import org.example.entities.PersonEntity;
import org.example.entities.ProductVO;
import org.example.services.BedroomService;
import org.example.services.PersonService;
import org.example.services.ProductService;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class AddItemsWindow extends JFrame {
    private JTextField quantidadeField = new JTextField(5);
    private JTextField descricaoField = new JTextField(30);
    private JTextField observacaoField = new JTextField(20);
    private JTextField valorUnitarioField = new JTextField(10);
    private JComboBox<PersonEntity> requerenteComboBox = new JComboBox<>();
    private BedroomEntity bedroom;
    private boolean isL;

    public AddItemsWindow(BedroomEntity bedroom, boolean isLaundry) {
        this.bedroom = bedroom;
        this.isL = isLaundry;

        // Apply numeric filter to quantidadeField and valorUnitarioField
        ((AbstractDocument) quantidadeField.getDocument()).setDocumentFilter(new NumericDocumentFilter());
        ((AbstractDocument) valorUnitarioField.getDocument()).setDocumentFilter(new NumericDocumentFilter());

        requerenteComboBox = new JComboBox<>(Components.getGuests(bedroom));

        JButton okButton = TableUtils.createButton("Ok", Color.WHITE, Color.BLACK, this::addProduct);

        // Set layout
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Row 1: Quantidade | Descrição
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Quantidade:"), gbc);

        gbc.gridx = 1;
        add(quantidadeField, gbc);

        gbc.gridx = 2;
        add(new JLabel("Descrição:"), gbc);

        gbc.gridx = 3;
        add(descricaoField, gbc);

        // Row 2: Valor Unitário | Observação
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Valor Unitário: R$"), gbc);

        gbc.gridx = 1;
        add(valorUnitarioField, gbc);

        gbc.gridx = 2;
        add(new JLabel("Observação:"), gbc);

        gbc.gridx = 3;
        add(observacaoField, gbc);

        // Row 3: Requerente
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 4;
        add(new JLabel("Requerente:"), gbc);

        gbc.gridy = 3;
        add(requerenteComboBox, gbc);

        // Row 4: OK Button
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        add(okButton, gbc);

        // Frame properties
        setTitle("Adicionar ítem");
        setSize(800, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addProduct(ActionEvent actionEvent) {
        if(!ProductService.validateFields(quantidadeField.getText(), descricaoField.getText(), observacaoField.getText(), valorUnitarioField.getText())){
            return;
        };


        ProductVO p = new ProductVO();
        p.setQnt(Integer.parseInt(quantidadeField.getText()));
        p.setDescription(descricaoField.getText());
        p.setName(descricaoField.getText());
        p.setUnValue(Double.parseDouble(valorUnitarioField.getText()));
        p.setObs(observacaoField.getText());
        p.setBedroomNumber(bedroom);

        List<PersonEntity> guests = PersonService.getByBedroom(bedroom);
        if( guests != null || !guests.isEmpty()){
            PersonEntity person = guests.get(0);
            p.setGuestId(person);
            p.setCompanyId(person.getCompany());
            p.setGuestId((PersonEntity) requerenteComboBox.getSelectedItem());
        }
        double sTotal = p.getQnt() * p.getUnValue();
        p.setSubTotal(sTotal);
        p.setLaundry(isL);

        ProductService.insertProduct(p);
        dispose();
    }


    public static void main(String[] args) {
        BedroomEntity b = BedroomService.getById(1);
        SwingUtilities.invokeLater(() -> new AddItemsWindow(b, false));
    }
}

class NumericDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (isNumeric(string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (isNumeric(text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isNumeric(String text) {
        return text != null && text.matches("\\d*");
    }
}
