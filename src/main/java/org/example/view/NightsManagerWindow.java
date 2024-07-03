package org.example.view;

import org.example.entities.*;
import org.example.repositories.NightRepository;
import org.example.services.BedroomService;
import org.example.services.NightService;
import org.example.services.PersonService;
import org.example.services.ReservationService;
import org.example.tablesUtil.NightsTable;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.example.tablesUtil.PersonTable.createPeopleTable;

public class NightsManagerWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable table;
    private BedroomEntity bedroomEntity;
    private DefaultTableModel model;
    private String total;
    private JComboBox<CompanyEntity> comboBox;
    private final Color standardColor = new Color(28, 130, 50);
    private final Color redColor = new Color(128, 0, 0);

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                NightsManagerWindow frame = new NightsManagerWindow();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public NightsManagerWindow() {
        setupFrame();
        initializeComponents(BedroomService.getById(1));
    }
    public NightsManagerWindow(BedroomEntity bedroomEntity) {
        setupFrame();
        initializeComponents(bedroomEntity);
    }
    public NightsManagerWindow(BedroomEntity bedroomEntity, BedroomWindow parent) {
        setupFrame();
        initializeComponents(bedroomEntity);
    }

    private void setupFrame() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(100, 100, 450, 300);

        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
    }

    private void initializeComponents(BedroomEntity bedroom) {
        bedroomEntity = bedroom;
        JPanel topPanel = createTopPanel();
        comboBox = new JComboBox(Components.getAllCompanyNames());
        JButton btnSearch = createButton("Ir", standardColor, Color.WHITE, this::searchAction);
        JButton btnNewPerson = createButton("Recebido!", standardColor, Color.WHITE, this::setPaid);
        JButton btnUpdate = createButton("Pendente", Color.DARK_GRAY, Color.WHITE, this::setPendent);
        JButton btnDelete = createButton("Excluir", redColor, Color.WHITE, this::deleteAction);

        JPanel panel = createTablePanel();
        JPanel panel1 = createStatusPanel(bedroom);

        comboBox.setSelectedIndex(-1);

        layoutComponents(topPanel, comboBox, btnSearch, btnNewPerson, btnUpdate, btnDelete, panel, panel1);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(standardColor);

        JLabel lblTitle = new JLabel("Faturas");
        lblTitle.setForeground(Color.WHITE);
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


    private JButton createButton(String text, Color bgColor, Color fgColor, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.addActionListener(action);
        return button;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel();
        table = new JTable();
        table.setFont(new Font("Arial", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(table);

        MaskFormatter phoneNumberFormatter = formatation("+55 (##) ##### ####");
        MaskFormatter cpfFormatter = formatation("###.###.###-##");

        List<NightEntity> all = NightService.getAll();
        assert all != null;
        model = NightsTable.createTable(all);
        table.setModel(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);
        table.getColumnModel().getColumn(1).setPreferredWidth(170);
        table.getColumnModel().getColumn(2).setPreferredWidth(150);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(160);
        table.getColumnModel().getColumn(5).setPreferredWidth(130);
        table.getColumnModel().getColumn(6).setPreferredWidth(150);
        table.getColumnModel().getColumn(7).setPreferredWidth(205);
        table.getColumnModel().getColumn(8).setPreferredWidth(450);

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

    private JPanel createStatusPanel(BedroomEntity bedroom) {
        JPanel panel1 = new JPanel();

        List<NightEntity> all = NightService.getAll();
        double totalPaid = NightService.calculateTotal(all, false);
        double totalNoPaid = NightService.calculateTotal(all, true);

        JLabel lblRecord = new JLabel("Total de pendentes:");
        lblRecord.setForeground(redColor);
        JLabel lblTotalOfRecords = new JLabel(String.valueOf(totalPaid));
        lblTotalOfRecords.setForeground(redColor);

        JLabel lblRecord2 = new JLabel("Total de Pagos:");
        lblRecord2.setForeground(Color.BLUE);
        JLabel lblTotalOfRecords2 = new JLabel(String.valueOf(totalNoPaid));

        GroupLayout gl_panel1 = new GroupLayout(panel1);
        gl_panel1.setHorizontalGroup(
                gl_panel1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel1.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblRecord)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblTotalOfRecords, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 100, Short.MAX_VALUE)
                                .addComponent(lblRecord2)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblTotalOfRecords2, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );
        gl_panel1.setVerticalGroup(
                gl_panel1.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_panel1.createSequentialGroup()
                                .addContainerGap(13, Short.MAX_VALUE)
                                .addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblRecord)
                                        .addComponent(lblTotalOfRecords)
                                        .addComponent(lblRecord2)
                                        .addComponent(lblTotalOfRecords2))
                                .addContainerGap())
        );
        panel1.setLayout(gl_panel1);

        return panel1;
    }

    private void layoutComponents(JPanel topPanel,  JComboBox<CompanyEntity> comboBox, JButton btnSearch, JButton btnNewPerson, JButton btnUpdate, JButton btnDelete, JPanel panel, JPanel panel1) {
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(panel1, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnSearch)
                                                .addGap(25))
                                        .addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(btnNewPerson)
                                                .addGap(18)
                                                .addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(5)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSearch))
                                .addGap(18)
                                .addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnNewPerson)
                                        .addComponent(btnUpdate)
                                        .addComponent(btnDelete))
                                .addGap(12)
                                .addComponent(panel, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(panel1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
        );
        contentPane.setLayout(gl_contentPane);
    }


    private void updateAction(ActionEvent e) {
        dispose();SwingUtilities.invokeLater(() -> new NightsManagerWindow(bedroomEntity).setVisible(true));
    }


    private void deleteAction(ActionEvent e) {
        final int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            NightService.delete((int) model.getValueAt(selectedRow, 0), this);
            refreshTable(new ArrayList<NightEntity>(), false);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum campo selecionado");
        }
    }

    private void searchAction(ActionEvent e) {
        List<NightEntity> searchedNights;
        if (comboBox.getSelectedItem() != null){
            searchedNights = NightRepository.getByCompany((CompanyEntity) comboBox.getSelectedItem());
            comboBox.setSelectedIndex(0);
            refreshTable(searchedNights, true);
        }
    }

    public void refreshTable(List<NightEntity> all, boolean isSearched) {
        if (!isSearched) all = NightService.getAll();
        refresh(all);
    }


    static MaskFormatter formatation(String format) {
        MaskFormatter shape = null;
        try {
            shape = new MaskFormatter(format);
            shape.setValueContainsLiteralCharacters(false);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return shape;
    }

    public void refresh(List<NightEntity> all) {
        model.setRowCount(0);
        for (NightEntity nightTable : all) {
            Object[] rowData = {
                    nightTable.getId(),
                    nightTable.getCompany().getName().equals("-- sem empresa --") ? nightTable.getClient().getName() : nightTable.getCompany().getName(),
                    nightTable.getBedroom().getId(),
                    nightTable.isPaid() ? "Pago" : "Pendente",
                    nightTable.getProductsValue(),
                    nightTable.getLaundryValue(),
                    nightTable.getBedroom().getValue(),
                    nightTable.getTotal(),
                    nightTable.getObs()
            };
            model.addRow(rowData);
        }
    }

    public void setPaid(ActionEvent e) {
        final int selectedRow = table.getSelectedRow();
        if(selectedRow != -1){
            NightEntity n = NightRepository.findById((int) table.getValueAt(selectedRow, 0));
            n.setPaid(true);
            NightRepository.create(n);
            refreshTable(new ArrayList<NightEntity>(), false);
        }
    }

    public void setPendent(ActionEvent e) {
        final int selectedRow = table.getSelectedRow();
        if(selectedRow != -1){
            NightEntity n = NightRepository.findById((int) table.getValueAt(selectedRow, 0));
            n.setPaid(false);
            NightRepository.create(n);
            refreshTable(new ArrayList<NightEntity>(), false);
        }

    }
}
