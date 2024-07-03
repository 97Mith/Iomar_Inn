package org.example.view;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JDateChooser;
import org.example.entities.*;
import org.example.repositories.BedroomRepository;
import org.example.repositories.PersonRepository;
import org.example.services.*;
import org.example.tablesUtil.PersonTable;
import org.example.tablesUtil.ProductTable;

import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import static org.example.view.CellRenderer.formatation;

public class BedroomWindow extends JFrame {
    private JDateChooser dateCheckIn;
    private JDateChooser dateCheckOut;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panel;
    private JTable tableProducts;
    private JTable tableLaundry;
    private JTextField textFieldDiscount;
    private JPanel panel_1;
    private JTable tableGuests;
    private Color blueColor = new Color(0, 128, 192);
    private Color redColor = new Color(128, 0, 0);
    private String nightValue;
    private BedroomEntity bedroom;
    private DefaultTableModel guestsModel;
    private DefaultTableModel productModel;
    private DefaultTableModel laundryModel;
    private String lValue;
    private String pValue;
    JLabel lblTotalP;
    JLabel lblTotalL;
    List<ProductVO> listProd;
    List<ProductVO> listLaun;
    List<PersonEntity> people;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                BedroomWindow frame = new BedroomWindow(BedroomService.getById(1));
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public BedroomWindow(BedroomEntity bedroomEntity) {
        this.bedroom = bedroomEntity;
        initializeWindow();
        initializeContentPane();
        initializeTopPanel();
        initializeMainPanels(bedroomEntity);
    }

    private void initializeWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initializeContentPane() {
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
    }

    private void initializeTopPanel() {
        panel = new JPanel();
        panel.setBackground(blueColor);

        JLabel lbl = new JLabel("Quarto");
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Tahoma", Font.PLAIN, 20));

        JLabel brNumberLabel = new JLabel(String.valueOf(bedroom.getId()));
        brNumberLabel.setForeground(Color.WHITE);
        brNumberLabel.setFont(new Font("Verdana", Font.PLAIN, 20));

        JLabel lblCompany = new JLabel("Empresa");
        lblCompany.setForeground(Color.WHITE);
        lblCompany.setFont(new Font("Tahoma", Font.PLAIN, 20));

        List<PersonEntity> people = PersonService.getByBedroom(bedroom);
        if (people != null && !people.isEmpty()) lblCompany.setText(people.get(0).getCompany().getName());

        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(21)
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panel.createSequentialGroup()
                                                .addGap(73)
                                                .addComponent(brNumberLabel, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
                                        .addComponent(lbl, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 198, Short.MAX_VALUE)
                                .addComponent(lblCompany, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(23)
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(brNumberLabel, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblCompany))
                                        .addComponent(lbl)))
        );
        panel.setLayout(gl_panel);
    }

    private void initializeMainPanels(BedroomEntity bedroomEntity) {
        JPanel panelCheckInOut = createCheckInOutPanel(bedroomEntity);
        JPanel panelProducts = createProductsPanel();
        JPanel panelLaundry = createLaundryPanel();

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addComponent(panel, GroupLayout.DEFAULT_SIZE, 872, Short.MAX_VALUE) // Use panel here
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(panelCheckInOut, GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(panelProducts, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                                .addGap(10)
                                .addComponent(panelLaundry, GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                                .addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
                gl_contentPane.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addGap(20)
                                .addComponent(panel, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE) // Use panel here
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
                                        .addComponent(panelCheckInOut, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                                        .addComponent(panelProducts, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                                        .addComponent(panelLaundry, GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))
                                .addContainerGap())
        );

        contentPane.setLayout(gl_contentPane);
    }

    private JPanel createCheckInOutPanel(BedroomEntity bedroomEntity) {
        people = PersonService.getByBedroom(bedroomEntity);
        assert people != null;
        guestsModel = PersonTable.createPeopleRoomTable(people);
        JPanel panelCheckInOut = new JPanel();


        JLabel lblCheckIn = new JLabel("Check In");
        JLabel lblCheckOut = new JLabel("Check Out");
        JLabel lblNewLabel = new JLabel("Hospedes");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));

        JDateChooser dateCheckIn = new JDateChooser();
        dateCheckIn.setDateFormatString("d '/' MM '/' y");
        dateCheckIn.setDate(bedroom.getCheckInDate());

        JDateChooser dateCheckOut = new JDateChooser();
        dateCheckOut.setDateFormatString("d '/' MM '/' y");
        dateCheckOut.setDate(bedroom.getCheckOutDate());

        this.dateCheckIn = dateCheckIn;
        this.dateCheckOut = dateCheckOut;

        textFieldDiscount = new JTextField();
        textFieldDiscount.setColumns(10);

        JButton btnDoneStay = TableUtils.createButton("Encerrar estada", blueColor, Color.WHITE, this::finish);

        nightValue = String.valueOf(bedroomEntity.getValue());



        JPanel panel_2_1 = createTotalPanel("Total:", String.valueOf(bedroom.getTotalOfStaying()), "Valor diária                R$", nightValue);

        JButton btnAdd = TableUtils.createButton("+ Adicionar", blueColor, Color.WHITE, this::addGuestAction);

        JButton btnRemoveGuest = TableUtils.createButton("- Remover", redColor, Color.WHITE, this::removeGuest);


        JLabel lblRs = new JLabel("R$");
        JLabel lblDesconto_1 = new JLabel("Desconto");

        JScrollPane scrollPane = new JScrollPane();
        GroupLayout gl_panelCheckInOut = new GroupLayout(panelCheckInOut);
        gl_panelCheckInOut.setHorizontalGroup(
                gl_panelCheckInOut.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelCheckInOut.createSequentialGroup()
                                .addGap(127)
                                .addComponent(lblNewLabel))
                        .addGroup(gl_panelCheckInOut.createSequentialGroup()
                                .addGap(10)
                                .addComponent(btnAdd, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                                .addGap(6)
                                .addComponent(btnRemoveGuest, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                .addGap(10))
                        .addGroup(Alignment.TRAILING, gl_panelCheckInOut.createSequentialGroup()
                                .addGap(34)
                                .addComponent(lblCheckIn, GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                                .addGap(91)
                                .addComponent(lblCheckOut, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                .addGap(30))
                        .addGroup(Alignment.TRAILING, gl_panelCheckInOut.createSequentialGroup()
                                .addGap(10)
                                .addComponent(dateCheckIn, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                .addGap(15)
                                .addComponent(dateCheckOut, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                .addGap(10))
                        .addGroup(gl_panelCheckInOut.createSequentialGroup()
                                .addGap(10)
                                .addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                                .addGap(10))
                        .addGroup(Alignment.TRAILING, gl_panelCheckInOut.createSequentialGroup()
                                .addGap(204)
                                .addComponent(lblDesconto_1, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
                                .addGap(10))
                        .addGroup(Alignment.TRAILING, gl_panelCheckInOut.createSequentialGroup()
                                .addGap(175)
                                .addComponent(lblRs, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                                .addGap(4)
                                .addComponent(textFieldDiscount, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
                                .addGap(10))
                        .addGroup(gl_panelCheckInOut.createSequentialGroup()
                                .addGap(85)
                                .addComponent(btnDoneStay, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                .addGap(87))
                        .addGroup(gl_panelCheckInOut.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                                .addContainerGap())
        );
        gl_panelCheckInOut.setVerticalGroup(
                gl_panelCheckInOut.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelCheckInOut.createSequentialGroup()
                                .addGap(10)
                                .addComponent(lblNewLabel)
                                .addGap(11)
                                .addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 82, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(gl_panelCheckInOut.createParallelGroup(Alignment.LEADING)
                                        .addComponent(btnAdd)
                                        .addComponent(btnRemoveGuest))
                                .addGap(6)
                                .addGroup(gl_panelCheckInOut.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblCheckIn)
                                        .addComponent(lblCheckOut))
                                .addGap(6)
                                .addGroup(gl_panelCheckInOut.createParallelGroup(Alignment.LEADING)
                                        .addComponent(dateCheckIn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(dateCheckOut, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(14)
                                .addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                                .addGap(6)
                                .addComponent(lblDesconto_1)
                                .addGap(6)
                                .addGroup(gl_panelCheckInOut.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panelCheckInOut.createSequentialGroup()
                                                .addGap(3)
                                                .addComponent(lblRs))
                                        .addComponent(textFieldDiscount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addComponent(btnDoneStay)
                                .addGap(10))
        );
        MaskFormatter cpfFormatter = formatation("###.###.###-##");
        MaskFormatter phoneNumFormatter = formatation("(##)##### ####");

        tableGuests = new JTable();
        tableGuests.setModel(guestsModel);
        tableGuests.setFont(new Font("Arial", Font.BOLD, 12));
        tableGuests.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableGuests.getColumnModel().getColumn(3).setCellRenderer(new CellRenderer(phoneNumFormatter));
        tableGuests.getColumnModel().getColumn(4).setCellRenderer(new CellRenderer(cpfFormatter));

        scrollPane.setViewportView(tableGuests);
        panelCheckInOut.setLayout(gl_panelCheckInOut);
        return panelCheckInOut;
    }

    private JPanel createProductsPanel() {
        JPanel panelProducts = new JPanel();

        JLabel lblConsumo = new JLabel("Consumo");
        lblConsumo.setFont(new Font("Tahoma", Font.PLAIN, 16));

        listProd = ProductService.getProductsInRoom(bedroom, false);

        JScrollPane scrollPaneProducts = new JScrollPane();
        productModel = ProductTable.createProductTable(listProd);
        tableProducts = new JTable(productModel);
        tableProducts.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableProducts.getColumnModel().getColumn(1).setPreferredWidth(25);
        tableProducts.getColumnModel().getColumn(5).setPreferredWidth(20);
        tableProducts.getColumnModel().getColumn(6).setPreferredWidth(20);

        scrollPaneProducts.setViewportView(tableProducts);

        JButton btnAddProduct = TableUtils.createButton("+ Produto", blueColor, Color.WHITE, this::addProduct);

        JButton btnRemoveProduct = TableUtils.createButton("- Remover", redColor, Color.WHITE, this::removeProduct);
        pValue = String.valueOf(ProductService.calculateTotalSubTotal(listProd));
        JPanel panelTotal = createTotalPanel("Total:  R$", pValue);

        GroupLayout gl_panelProducts = new GroupLayout(panelProducts);
        gl_panelProducts.setHorizontalGroup(
                gl_panelProducts.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelProducts.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panelProducts.createParallelGroup(Alignment.LEADING)
                                        .addComponent(scrollPaneProducts, GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE)
                                        .addGroup(gl_panelProducts.createSequentialGroup()
                                                .addComponent(btnAddProduct, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                                .addGap(48)
                                                .addComponent(btnRemoveProduct, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE))
                                        .addComponent(panelTotal, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
                                .addContainerGap())
                        .addGroup(gl_panelProducts.createSequentialGroup()
                                .addGap(110)
                                .addComponent(lblConsumo, GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                                .addGap(94))
        );
        gl_panelProducts.setVerticalGroup(
                gl_panelProducts.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelProducts.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblConsumo, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrollPaneProducts, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                .addGap(69)
                                .addComponent(panelTotal, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                                .addGap(26)
                                .addGroup(gl_panelProducts.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnAddProduct)
                                        .addComponent(btnRemoveProduct))
                                .addGap(43))
        );

        panelProducts.setLayout(gl_panelProducts);
        return panelProducts;
    }

    private JPanel createLaundryPanel() {
        JPanel panelLaundry = new JPanel();

        JLabel lblLavanderia = new JLabel("Lavanderia");
        lblLavanderia.setFont(new Font("Tahoma", Font.PLAIN, 16));

        JScrollPane scrollPaneLaundry = new JScrollPane();
        listLaun = ProductService.getProductsInRoom(bedroom, true);
        laundryModel = ProductTable.createProductTable(listLaun);
        tableLaundry = new JTable(laundryModel);
        tableLaundry.getColumnModel().getColumn(0).setPreferredWidth(20);
        tableLaundry.getColumnModel().getColumn(1).setPreferredWidth(20);
        tableLaundry.getColumnModel().getColumn(5).setPreferredWidth(20);
        tableLaundry.getColumnModel().getColumn(6).setPreferredWidth(20);

        scrollPaneLaundry.setViewportView(tableLaundry);

        JButton btnAddCloath = TableUtils.createButton("+ Roupa", blueColor, Color.WHITE, this::addLaundry);

        JButton btnRemoveCloath = TableUtils.createButton("- Remover", redColor, Color.WHITE, this::removeCloath);

        lValue = String.valueOf(ProductService.calculateTotalSubTotal(listLaun));
        JPanel panelTotal = createTotalPanel2("Total:  R$", lValue);

        GroupLayout gl_panelLaundry = new GroupLayout(panelLaundry);
        gl_panelLaundry.setHorizontalGroup(
                gl_panelLaundry.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelLaundry.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panelLaundry.createParallelGroup(Alignment.LEADING)
                                        .addComponent(scrollPaneLaundry, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                                        .addComponent(panelTotal, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                                        .addGroup(gl_panelLaundry.createSequentialGroup()
                                                .addComponent(btnAddCloath, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)
                                                .addGap(18)
                                                .addComponent(btnRemoveCloath, GroupLayout.DEFAULT_SIZE, 113, Short.MAX_VALUE)))
                                .addContainerGap())
                        .addGroup(gl_panelLaundry.createSequentialGroup()
                                .addGap(100)
                                .addComponent(lblLavanderia, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(64, Short.MAX_VALUE))
        );
        gl_panelLaundry.setVerticalGroup(
                gl_panelLaundry.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panelLaundry.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblLavanderia, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
                                .addGap(6)
                                .addComponent(scrollPaneLaundry, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                                .addGap(65)
                                .addComponent(panelTotal, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
                                .addGap(29)
                                .addGroup(gl_panelLaundry.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(btnAddCloath)
                                        .addComponent(btnRemoveCloath))
                                .addGap(44))
        );

        panelLaundry.setLayout(gl_panelLaundry);
        return panelLaundry;
    }

    private JPanel createTotalPanel(String totalLabelText, String totalValueText) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(215, 179, 11));

        JLabel lblText = new JLabel(totalLabelText);
        lblText.setFont(new Font("Tahoma", Font.PLAIN, 22));

        lblTotalP = new JLabel(totalValueText);
        lblTotalP.setFont(new Font("Tahoma", Font.PLAIN, 22));

        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(30)
                                .addComponent(lblText)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(lblTotalP, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                                .addGap(20))
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblTotalP, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblText))
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        panel.setLayout(gl_panel);
        return panel;
    }
    private JPanel createTotalPanel2(String totalLabelText, String totalValueText) {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(215, 179, 11));

        JLabel lblText = new JLabel(totalLabelText);
        lblText.setFont(new Font("Tahoma", Font.PLAIN, 22));

        lblTotalL = new JLabel(totalValueText);
        lblTotalL.setFont(new Font("Tahoma", Font.PLAIN, 22));

        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addGap(30)
                                .addComponent(lblText)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                                .addComponent(lblTotalL, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
                                .addGap(20))
        );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblTotalL, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblText))
                                .addContainerGap(12, Short.MAX_VALUE))
        );

        panel.setLayout(gl_panel);
        return panel;
    }

    private JPanel createTotalPanel(String totalLabelText, String totalValueText, String nightValueText, String nightValue) {
        panel_1 = new JPanel();
        panel_1.setBackground(new Color(215, 179, 11));

        JLabel lblTotal = new JLabel(totalLabelText);
        lblTotal.setFont(new Font("Tahoma", Font.PLAIN, 18));

        JLabel lblValue = new JLabel(totalValueText);
        lblValue.setFont(new Font("Tahoma", Font.PLAIN, 15));


        WindowService.addDateChangeListener(bedroom, dateCheckIn, dateCheckOut, bedroom.getValue(), textFieldDiscount, this, lblValue);

        JLabel lblNightValue = new JLabel(nightValueText);
        JLabel lblNightV = new JLabel(nightValue);

        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
                gl_panel_1.createParallelGroup(Alignment.TRAILING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addGap(31)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(lblTotal)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                                                .addComponent(lblValue, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 56, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblNightValue, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(lblNightV, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
        );
        gl_panel_1.setVerticalGroup(
                gl_panel_1.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblNightV)
                                        .addComponent(lblNightValue))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(lblTotal)
                                        .addComponent(lblValue, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panel_1.setLayout(gl_panel_1);
        return panel_1;
    }

    private void addGuestAction(ActionEvent e) {
        if (people.size() > bedroom.capacity - 1){
            JOptionPane.showMessageDialog(null, "Quarto lotado!");
            return;
        }
        new PersonManagerWindow(bedroom, this).setVisible(true);
    }

    private void addProduct(ActionEvent e) {
        new AddItemsWindow(bedroom, false, this);
    }

    private  void addLaundry(ActionEvent e) {
        new AddItemsWindow(bedroom, true, this);
    }

    private void removeGuest(ActionEvent e){
        final int selectedRow = tableGuests.getSelectedRow();
        if (selectedRow != -1) {
            PersonEntity p = PersonService.getById((int) guestsModel.getValueAt(selectedRow, 0));
            PersonService.insertOrRemoveBedroom(p, null);

            List<PersonEntity> listIsEmpty = BedroomService.loadAllInBedroom(bedroom);
            if(listIsEmpty.isEmpty()) BedroomService.updateStatus(bedroom, "Disponível");
            refreshGuests();

        } else {
            JOptionPane.showMessageDialog(null, "Nenhum campo selecionado");
        }
    }
    private void removeCloath(ActionEvent e) {
        final int selectedRow = tableLaundry.getSelectedRow();
        if (selectedRow != -1){
            ProductVO p = ProductService.getByRegister((int) laundryModel.getValueAt(selectedRow, 0));
            ProductService.delete(p);
            refreshTable(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum campo selecionado");
        }
    }
    private void removeProduct(ActionEvent e) {
        final int selectedRow = tableProducts.getSelectedRow();
        if (selectedRow != -1){
            ProductVO p = ProductService.getByRegister((int) productModel.getValueAt(selectedRow, 0));
            ProductService.delete(p);
            refreshTable(false);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum campo selecionado");
        }
    }

    public void finish(ActionEvent e) {
        try{
            NightEntity nights = new NightEntity();
            PersonEntity person = people.get(0);
            nights.setBedroom(bedroom);
            nights.setCompany(person.getCompany());
            nights.setClient(person);
            nights.setPaid(false);
            nights.setProductsValue(ProductService.calculateTotalSubTotal(listProd));
            nights.setLaundryValue(ProductService.calculateTotalSubTotal(listLaun));
            nights.setNightsValue(bedroom.getTotalOfStaying());
            nights.setTotal(ProductService.calculateTotalSubTotal(listProd) + ProductService.calculateTotalSubTotal(listLaun) + bedroom.getTotalOfStaying());

            bedroom.setCheckInDate(null);
            bedroom.setCheckOutDate(null);
            bedroom.setTotalOfStaying(0);
            for(PersonEntity personList : people){
                PersonRepository.insertOrRemoveBedroom(personList, null);
            }
            BedroomService.updateStatus(bedroom,"Disponível");
            ProductService.removeAll(bedroom);
            BedroomRepository.createBedroom(bedroom);
            NightService.updateReservation(nights);
            dispose();
        } catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Devem ter pessoas no quarto!", "Aviso", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void refreshGuests(){
        people = PersonService.getByBedroom(bedroom);
        guestsModel.setRowCount(0);
        for(PersonEntity person : people) {
            Object[] rowData = {
                    person.getId(),
                    person.getName(), person.getSurName(),
                    person.getPhoneNumber(), person.getCpf()
            };
            guestsModel.addRow(rowData);
        }
    }

    public void refreshTable(boolean isLaundry) {
        if(isLaundry){
            listLaun = ProductService.getProductsInRoom(bedroom, true);
            laundryModel.setRowCount(0);
            for (ProductVO productTable : listLaun) {
                Object[] rowData = {
                        productTable.getRegisterNum(),
                        productTable.getQnt(), productTable.getDescription(),
                        productTable.getObs(), productTable.getGuestId().getName(),
                        productTable.getUnValue(), productTable.getSubTotal()
                };
                laundryModel.addRow(rowData);
            }
            lValue = String.valueOf(ProductService.calculateTotalSubTotal(listLaun));
            lblTotalL.setText(lValue);

        }else {
            listProd = ProductService.getProductsInRoom(bedroom, false);
            productModel.setRowCount(0);
            for (ProductVO productTable : listProd) {
                Object[] rowData = {
                        productTable.getRegisterNum(), productTable.getQnt(),
                        productTable.getDescription(), productTable.getObs(),
                        productTable.getGuestId().getName(), productTable.getUnValue(),
                        productTable.getSubTotal()
                };
                productModel.addRow(rowData);
            }
            pValue = String.valueOf(ProductService.calculateTotalSubTotal(listProd));
            lblTotalP.setText(pValue);
        }
    }

}
