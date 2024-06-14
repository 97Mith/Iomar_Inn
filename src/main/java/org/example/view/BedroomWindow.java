package org.example.view;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JDateChooser;
import org.example.entities.BedroomEntity;
import org.example.entities.ProductVO;
import org.example.entities.PersonEntity;
import org.example.repositories.CompanyRepository;
import org.example.services.BedroomService;
import org.example.services.ProductService;
import org.example.services.PersonService;
import org.example.tablesUtil.PersonTable;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class BedroomWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tableGuests;
    private JTable tableProducts;
    private JTable tableLaundry;
    private long[] daysDifference = {0};
    private double discount = 0;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                BedroomWindow frame = new BedroomWindow(1);
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public BedroomWindow(Integer bedroomNumber) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        initializeContentPane();
        initializeComponents(bedroomNumber);
    }

    private void initializeContentPane() {
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
    }

    private void initializeComponents(Integer bedroomNumber) {
        BedroomEntity bedroomEntity = BedroomService.getById(bedroomNumber);
        Integer capacity = bedroomEntity.getCapacity();
        double bedroomValue = bedroomEntity.getValue();

        JPanel panelHeader = createHeaderPanel(bedroomNumber);
        JPanel panelMain = createMainPanel(bedroomNumber, capacity, bedroomValue);
        JPanel panelFooter = createFooterPanel();

        GroupLayout layout = new GroupLayout(contentPane);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(panelHeader, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(panelMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(panelFooter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(panelHeader, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(panelMain, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(panelFooter, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        contentPane.setLayout(layout);
    }

    private JPanel createHeaderPanel(Integer bedroomNumber) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 128, 255));

        JLabel lblBedroom = new JLabel("Quarto");
        lblBedroom.setForeground(Color.WHITE);
        lblBedroom.setFont(new Font("Tahoma", Font.PLAIN, 20));

        JLabel lblBedroomNumber = new JLabel(String.valueOf(bedroomNumber));
        lblBedroomNumber.setForeground(Color.WHITE);
        lblBedroomNumber.setFont(new Font("Verdana", Font.PLAIN, 20));

        JLabel lblCompany = new JLabel("Sem empresa");
        List<PersonEntity> guests = BedroomService.loadAllInBedroom(BedroomService.getById(bedroomNumber));
        if (!guests.isEmpty()) {
            String companyName = guests.get(0).getCompany().getName();
            lblCompany.setText(companyName);
        }
        lblCompany.setForeground(Color.WHITE);
        lblCompany.setFont(new Font("Tahoma", Font.PLAIN, 20));

        GroupLayout layout = new GroupLayout(panel);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(21)
                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(73)
                                                .addComponent(lblBedroomNumber, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lblBedroom, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                                .addComponent(lblCompany, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(23)
                                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblBedroom)
                                        .addComponent(lblBedroomNumber, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblCompany))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel.setLayout(layout);
        return panel;
    }

    private JPanel createMainPanel(Integer bedroomNumber, Integer capacity, double bedroomValue) {
        JPanel panel = new JPanel();

        JDateChooser dateCheckIn = new JDateChooser();
        dateCheckIn.setDateFormatString("d '/' MM '/' y");

        JDateChooser dateCheckOut = new JDateChooser();
        dateCheckOut.setDateFormatString("d '/' MM '/' y");
        dateCheckOut.addPropertyChangeListener("date", createDateChangeListener(bedroomValue));

        JScrollPane scrollPaneGuests = new JScrollPane();
        tableGuests = new JTable();
        List<PersonEntity> guests = BedroomService.loadAllInBedroom(BedroomService.getById(bedroomNumber));
        DefaultTableModel modelGuests = PersonTable.createPeopleTable(guests);
        tableGuests.setModel(modelGuests);
        formatTableStandard(tableGuests);
        scrollPaneGuests.setViewportView(tableGuests);

        JButton btnAddGuest = createAddGuestButton(bedroomNumber, capacity);
        JButton btnRemoveGuest = createRemoveGuestButton();

        JLabel lblCheckIn = new JLabel("Check In");
        JLabel lblCheckOut = new JLabel("Check Out");
        JLabel lblGuests = new JLabel("Hospedes");
        lblGuests.setFont(new Font("Tahoma", Font.PLAIN, 16));

        GroupLayout layout = new GroupLayout(panel);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(scrollPaneGuests, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(dateCheckIn, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(dateCheckOut, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnAddGuest, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnRemoveGuest, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(34)
                                                .addComponent(lblCheckIn, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                                                .addComponent(lblCheckOut, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                                                .addGap(30))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(127)
                                                .addComponent(lblGuests)
                                                .addContainerGap(88, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblGuests)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(scrollPaneGuests, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnAddGuest)
                                        .addComponent(btnRemoveGuest))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblCheckIn)
                                        .addComponent(lblCheckOut))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(dateCheckIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dateCheckOut, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel.setLayout(layout);
        return panel;
    }

    private JPanel createFooterPanel() {
        JPanel panel = new JPanel();

        JButton btnDoneStay = new JButton("Encerrar Estada");
        btnDoneStay.setForeground(Color.WHITE);
        btnDoneStay.setBackground(new Color(0, 128, 192));

        JLabel lblTotal = new JLabel("Total:  R$");
        lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 22));

        JLabel lblStayValue = new JLabel("0,00");
        lblStayValue.setFont(new Font("Tahoma", Font.PLAIN, 22));

        GroupLayout layout = new GroupLayout(panel);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(btnDoneStay, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(lblTotal)
                                                .addPreferredGap(ComponentPlacement.RELATED, 98, Short.MAX_VALUE)
                                                .addComponent(lblStayValue)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblTotal)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblStayValue)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(btnDoneStay, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                                .addContainerGap())
        );
        panel.setLayout(layout);
        return panel;
    }

    private JButton createAddGuestButton(Integer bedroomNumber, Integer capacity) {
        JButton btnAddGuest = new JButton("+  Adicionar");
        btnAddGuest.setForeground(Color.WHITE);
        btnAddGuest.setBackground(new Color(0, 128, 192));
        btnAddGuest.addActionListener(e -> {
            List<PersonEntity> guests = BedroomService.loadAllInBedroom(BedroomService.getById(bedroomNumber));
            if (guests.size() > capacity - 1) {
                JOptionPane.showMessageDialog(null, "O quarto está lotado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                new PersonManagerWindow().setVisible(true);
            }
        });
        return btnAddGuest;
    }


    private JButton createRemoveGuestButton() {
        JButton btnRemoveGuest = new JButton("-  Remover");
        btnRemoveGuest.setForeground(Color.WHITE);
        btnRemoveGuest.setBackground(new Color(128, 0, 0));
        btnRemoveGuest.addActionListener(e -> {
            final int selectedRow = tableGuests.getSelectedRow();
            if (selectedRow != -1) {
                DefaultTableModel modelGuests = (DefaultTableModel) tableGuests.getModel();
                PersonService.deleteById((int) modelGuests.getValueAt(selectedRow, 0));
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum campo selecionado");
            }
        });
        return btnRemoveGuest;
    }


    private PropertyChangeListener createDateChangeListener(double bedroomValue) {
        return evt -> {
            // Add code to handle date change
        };
    }

    public static void formatTableStandard(JTable table) {
        table.getColumnModel().getColumn(0).setPreferredWidth(50); // ID
        table.getColumnModel().getColumn(1).setPreferredWidth(230); // Nome
        table.getColumnModel().getColumn(2).setPreferredWidth(230); // Sobrenome
        table.getColumnModel().getColumn(3).setPreferredWidth(230); // Telefone
        table.getColumnModel().getColumn(4).setPreferredWidth(0); // Quarto
        table.getColumnModel().getColumn(5).setPreferredWidth(298); // Empresa
        table.getColumnModel().getColumn(6).setPreferredWidth(170); // Cpf
    }
}
