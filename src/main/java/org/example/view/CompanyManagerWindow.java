package org.example.view;

import org.example.entities.CompanyEntity;
import org.example.repositories.CompanyRepository;
import org.example.services.CompanyService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.text.ParseException;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.text.MaskFormatter;

import static org.example.view.CellRenderer.formatation;

public class CompanyManagerWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtSearch;
    private JTable table;
    private DefaultTableModel model;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CompanyManagerWindow frame = new CompanyManagerWindow();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CompanyManagerWindow() {
        setupFrame();
        initializeComponents();
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

    private void initializeComponents() {
        JPanel topPanel = createTopPanel();
        txtSearch = createSearchTextField();
        JButton btnSearch = createButton("Ir", new Color(128, 0, 255), Color.WHITE, this::searchAction);
        JButton btnNewCompany = createButton("+ Novo Empresa", new Color(128, 0, 255), Color.WHITE, this::newCompanyAction);
        JButton btnUpdate = createButton("Atualizar...", new Color(128, 0, 255), Color.WHITE, this::updateAction);
        JButton btnDelete = createButton("Excluir", new Color(128, 0, 0), Color.WHITE, this::deleteAction);
        JButton btnEdit = createButton("Editar", new Color(128, 0, 255), Color.WHITE, this::editAction);

        JPanel panel = createTablePanel();
        JPanel panel1 = createStatusPanel();

        layoutComponents(topPanel, txtSearch, btnSearch, btnNewCompany, btnUpdate, btnDelete, btnEdit, panel, panel1);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(128, 0, 255));

        JLabel lblTitle = new JLabel("Empresa");
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

    private JTextField createSearchTextField() {
        JTextField txtSearch = new JTextField();
        txtSearch.setFont(new Font("Tahoma", Font.PLAIN, 15));
        txtSearch.setHorizontalAlignment(SwingConstants.CENTER);
        txtSearch.setForeground(Color.GRAY);
        txtSearch.setText("Pesquisar...");
        txtSearch.setColumns(10);
        txtSearch.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtSearch.getText().equals("Pesquisar...")) {
                    txtSearch.setText("");
                    txtSearch.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtSearch.getText().isEmpty()) {
                    txtSearch.setForeground(Color.GRAY);
                    txtSearch.setText("Pesquisar...");
                }
            }
        });

        return txtSearch;
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

        MaskFormatter cnpjFormatter = formatation("##.###.###/####-##");
        MaskFormatter phoneNumberFormatter = formatation("+55 (##) ##### ####");

        List<CompanyEntity> allCompanies = CompanyService.getAll();
        assert allCompanies != null;
        model = CompanyService.createCompanyTable(allCompanies);
        table.setModel(model);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(170);
        table.getColumnModel().getColumn(2).setPreferredWidth(400);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(160);
        table.getColumnModel().getColumn(5).setPreferredWidth(130);
        table.getColumnModel().getColumn(6).setPreferredWidth(430);
        table.getColumnModel().getColumn(3).setCellRenderer(new CellRenderer(phoneNumberFormatter));
        table.getColumnModel().getColumn(4).setCellRenderer(new CellRenderer(cnpjFormatter));

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

    private JPanel createStatusPanel() {
        JPanel panel1 = new JPanel();

        JLabel lblRecord = new JLabel("Total de registros:");
        int total = CompanyService.getAll().size();
        JLabel lblTotalOfRecords = new JLabel(String.valueOf(total));

        JButton btnOk = createButton("Ok", new Color(128, 0, 255), Color.WHITE, e -> dispose());

        GroupLayout gl_panel1 = new GroupLayout(panel1);
        gl_panel1.setHorizontalGroup(
                gl_panel1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel1.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblRecord)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(lblTotalOfRecords, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 266, Short.MAX_VALUE)
                                .addComponent(btnOk)
                                .addContainerGap())
        );
        gl_panel1.setVerticalGroup(
                gl_panel1.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_panel1.createSequentialGroup()
                                .addContainerGap(13, Short.MAX_VALUE)
                                .addGroup(gl_panel1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblRecord)
                                        .addComponent(lblTotalOfRecords)
                                        .addComponent(btnOk))
                                .addContainerGap())
        );
        panel1.setLayout(gl_panel1);

        return panel1;
    }

    private void layoutComponents(JPanel topPanel, JTextField txtSearch, JButton btnSearch, JButton btnNewCompany, JButton btnUpdate, JButton btnDelete, JButton btnEdit, JPanel panel, JPanel panel1) {
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
                                        .addComponent(panel1, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnSearch)
                                                .addGap(25))
                                        .addComponent(topPanel, GroupLayout.DEFAULT_SIZE, 472, Short.MAX_VALUE)
                                        .addGroup(gl_contentPane.createSequentialGroup()
                                                .addComponent(btnNewCompany)
                                                .addGap(18)
                                                .addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                                                .addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(5)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(txtSearch, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnSearch))
                                .addGap(18)
                                .addComponent(topPanel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnNewCompany)
                                        .addComponent(btnUpdate)
                                        .addComponent(btnDelete)
                                        .addComponent(btnEdit))
                                .addGap(12)
                                .addComponent(panel, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(panel1, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
        );
        contentPane.setLayout(gl_contentPane);
    }

    private void newCompanyAction(ActionEvent e) {
        CompanyEntity company = new CompanyEntity();
        NewCompanyWindow newCompanyWindow = new NewCompanyWindow(company, this);
        newCompanyWindow.setVisible(true);
    }

    private void updateAction(ActionEvent e) {
        dispose();
        SwingUtilities.invokeLater(() -> new CompanyManagerWindow().setVisible(true));
    }

    private void editAction(ActionEvent e) {
        final int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Integer companyId = (Integer) model.getValueAt(selectedRow, 0);
            CompanyEntity company = CompanyRepository.findById(companyId);
            new NewCompanyWindow(company, this).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum campo selecionado");
        }
    }

    private void deleteAction(ActionEvent e) {
        final int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            CompanyService.deleteById((int) model.getValueAt(selectedRow, 0));
            updateAction(e);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum campo selecionado");
        }
    }

    private void searchAction(ActionEvent e) {
        String companyPartialName = txtSearch.getText();
        List<CompanyEntity> searchedCompanies = CompanyService.getByName(companyPartialName);
        refreshTable(searchedCompanies, true);

    }


    public void refreshTable(List<CompanyEntity> allCompanies, boolean isSeached) {
        if(!isSeached) allCompanies = CompanyService.getAll();
        model.setRowCount(0); // Limpar dados existentes
        for (CompanyEntity company : allCompanies) {
            Object[] rowData = {
                    company.getId(), company.getName(), company.getCorporateReason(), company.getPhoneNumber(),
                    company.getCnpj(), company.getStateInscription(), company.getEmail()
            };
            model.addRow(rowData);
        }
    }

}
