package org.example.view;

import org.example.entities.BedroomEntity;
import org.example.entities.PersonEntity;
import org.example.entities.ReservationEntity;
import org.example.services.BedroomService;
import org.example.services.CompanyService;
import org.example.services.PersonService;
import org.example.services.ReservationService;
import org.example.tablesUtil.ReservationTable;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class ReservationManagerWindow extends JFrame {

    DefaultTableModel model;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private Color standardColor = new Color(64, 0, 64);
    private Color redColor = new Color(128, 0, 0);

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ReservationManagerWindow frame = new ReservationManagerWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public ReservationManagerWindow() {
        setupFrame();
        initializeComponents();
    }

    private void setupFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
    }

    private void initializeComponents() {
        JPanel topPanel = setupTopPanel();
        JPanel panel = setupTablePanel();
        JPanel panel_1 = setupStatusPanel();
        JPanel buttonPanel = setupButtonPanel();

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                        .addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                        .addComponent(buttonPanel, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(44)
                                .addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(buttonPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(12)
                                .addComponent(panel, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
        );

        contentPane.setLayout(gl_contentPane);
    }

    private JPanel setupTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(64, 0, 64));
        JLabel lblTitle = new JLabel("Reservas");
        lblTitle.setForeground(new Color(255, 255, 255));
        lblTitle.setFont(new Font("Verdana", Font.PLAIN, 23));

        GroupLayout gl_topPanel = new GroupLayout(topPanel);
        gl_topPanel.setHorizontalGroup(
                gl_topPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_topPanel.createSequentialGroup()
                                .addGap(18)
                                .addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(231, Short.MAX_VALUE))
        );
        gl_topPanel.setVerticalGroup(
                gl_topPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(Alignment.TRAILING, gl_topPanel.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblTitle)
                                .addContainerGap())
        );
        topPanel.setLayout(gl_topPanel);
        return topPanel;
    }

    private JPanel setupButtonPanel() {
        JButton btnNewReserve = TableUtils.createButton("+ Nova Reserva", standardColor, Color.WHITE, this::newReserve);

        JButton btnPrint = new JButton("Atualizar...");
        btnPrint.setForeground(Color.WHITE);
        btnPrint.setBackground(new Color(64, 0, 64));

        JButton btnDelete = TableUtils.createButton("Excluir", redColor, Color.WHITE, this::deleteReserve);


        JPanel buttonPanel = new JPanel();
        GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
        gl_buttonPanel.setHorizontalGroup(
                gl_buttonPanel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_buttonPanel.createSequentialGroup()
                                .addComponent(btnNewReserve)
                                .addGap(18)
                                .addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
        );
        gl_buttonPanel.setVerticalGroup(
                gl_buttonPanel.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnNewReserve)
                        .addComponent(btnPrint)
                        .addComponent(btnDelete)
        );
        buttonPanel.setLayout(gl_buttonPanel);
        return buttonPanel;
    }

    private JPanel setupTablePanel() {
        JPanel panel = new JPanel();
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        List<ReservationEntity> reservations = ReservationService.getAll();
        this.model = ReservationTable.createReservationTable(reservations);

        table.setModel(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.getColumnModel().getColumn(0).setPreferredWidth(20);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(20);
        table.getColumnModel().getColumn(3).setPreferredWidth(250);
        table.getColumnModel().getColumn(4).setPreferredWidth(350);

        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(10)
                                .addComponent(scrollPane)
                                .addGap(10))
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(5)
                                .addComponent(scrollPane))
        );
        panel.setLayout(gl_panel);
        return panel;
    }

    private JPanel setupStatusPanel() {
        JPanel panel_1 = new JPanel();
        JLabel lblRecord = new JLabel("Total de registros:");
        JLabel lblTotalOfRecords = new JLabel(String.valueOf(ReservationService.getAll().size()));

        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblRecord)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblTotalOfRecords, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(321, Short.MAX_VALUE))
        );
        gl_panel_1.setVerticalGroup(
                gl_panel_1.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblRecord)
                                        .addComponent(lblTotalOfRecords))
                                .addContainerGap())
        );
        panel_1.setLayout(gl_panel_1);
        return panel_1;
    }
    private void newReserve(ActionEvent e){
        new NewReservationWindow(new ReservationEntity(), this).setVisible(true);
    }
    private void deleteReserve(ActionEvent e){
        final int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            BedroomEntity b = BedroomService.getById((Integer) model.getValueAt(selectedRow, 2));
            ReservationService.deleteReserve((Integer) model.getValueAt(selectedRow, 0), this, b);

        } else {
            JOptionPane.showMessageDialog(null, "Nenhum campo selecionado");
        }
    }

    public void refreshTable() {
        List<ReservationEntity> allReserves = ReservationService.getAll();
        model.setRowCount(0);
        for (ReservationEntity reservationEntity : allReserves) {
            Object[] rowData = {
                    reservationEntity.getId(), reservationEntity.getReservationName(),
                    reservationEntity.getBedroom().getId(),
                    reservationEntity.getCheckIn(), reservationEntity.getCheckOut()
            };
            model.addRow(rowData);
        }
    }
}
