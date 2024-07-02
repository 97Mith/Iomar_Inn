package org.example.view;

import org.example.entities.BedroomEntity;
import org.example.entities.PersonEntity;
import org.example.services.BedroomService;
import org.example.tablesUtil.MainTable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableModel;

public class MainWindow extends JFrame {
    private LocalDateTime checkoutDateTime;
    private static final int SIDE_PANEL_WIDTH = 400;
    private JPanel lateralPanel;
    private JPanel content;
    private boolean visiblePanel = true;
    private JPanel bedroomPane;
    private int bedroomCount = 0;

    public MainWindow() {
        initializeFrame();
        initializeComponents();
        layoutComponents();
        initializeActions();
        addInitialBedrooms();
    }

    private void initializeFrame() {
        setTitle("Painel Lateral Retrátil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void initializeComponents() {
        lateralPanel = createLateralPanel();
        content = createContentPanel();
        getContentPane().add(lateralPanel, BorderLayout.WEST);
        getContentPane().add(content, BorderLayout.CENTER);
    }

    private JPanel createLateralPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0, 128, 192));
        panel.setPreferredSize(new Dimension(SIDE_PANEL_WIDTH, getHeight()));
        return panel;
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        return panel;
    }

    private void layoutComponents() {
        layoutLateralPanel();
        layoutContentPanel();
    }

    private void layoutLateralPanel() {
        JButton btnManageGuests = TableUtils.createMenuButton("Consultar Hóspede", new Color(0, 128, 192), Color.WHITE, this::openPersonManager, 17);
        JButton btnManageCompany = TableUtils.createMenuButton("Gerenciar Empresa", new Color(0, 128, 192), Color.WHITE, this::openCompanyManager, 17);
        JButton btnManageBedrooms = TableUtils.createMenuButton("Reservar Quartos", new Color(0, 128, 192), Color.WHITE, this::openReservation,17);
        JButton btnManageFinances = createButton("Gerenciar Finanças");

        JPanel panelMenu = createMenuPanel();
        JPanel panelInfo = createInfoPanel();

        GroupLayout layout = new GroupLayout(lateralPanel);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.TRAILING)
                        .addComponent(btnManageGuests, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                        .addComponent(btnManageCompany, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                        .addComponent(btnManageBedrooms, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                        .addComponent(btnManageFinances, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                        .addComponent(panelMenu, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
                        .addComponent(panelInfo, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(panelMenu, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(btnManageGuests, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(btnManageCompany, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(btnManageBedrooms, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(btnManageFinances, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addComponent(panelInfo, GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                                .addContainerGap())
        );
        lateralPanel.setLayout(layout);
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
        panel.setBackground(new Color(0, 128, 192));

        JLabel companyNameLabel = new JLabel("Iomar Inn");
        companyNameLabel.setForeground(new Color(255, 255, 255));
        companyNameLabel.setFont(new Font("Segoe UI Black", Font.PLAIN, 34));

        GroupLayout layout = new GroupLayout(panel);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(companyNameLabel, GroupLayout.DEFAULT_SIZE, 410, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(companyNameLabel)
                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel.setLayout(layout);

        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new MatteBorder(1, 1, 1, 1, new Color(0, 0, 0)));
        panel.setBackground(new Color(200, 200, 200));

        JLabel label = new JLabel("Informações");
        label.setFont(new Font("Tahoma", Font.PLAIN, 24));
        panel.add(label);

        return panel;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.PLAIN, 20));
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(0, 128, 192));
        button.setMargin(new Insets(0, 20, 0, 150));
        return button;
    }

    private void layoutContentPanel() {
        JPanel headerPanel = createHeaderPanel();
        bedroomPane = createBedroomPane();

        GroupLayout layout = new GroupLayout(content);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addComponent(headerPanel, GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
                        .addComponent(bedroomPane, GroupLayout.DEFAULT_SIZE, 592, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(headerPanel, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(bedroomPane, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
                                .addContainerGap())
        );
        content.setLayout(layout);
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel();
        panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        panel.setBackground(new Color(255, 255, 255));

        JButton btnShowLateralPane = createWhiteButton("Abrir");
        JButton btnUpdate = createWhiteButton("Atualizar");
        JButton btnOption = createWhiteButton("opção");

        GroupLayout layout = new GroupLayout(panel);
        layout.setHorizontalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(btnShowLateralPane, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(ComponentPlacement.RELATED, 306, Short.MAX_VALUE)
                                .addComponent(btnOption, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                                .addGap(24))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
                                .addComponent(btnShowLateralPane, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE)
                                .addComponent(btnUpdate, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnOption, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
        );
        panel.setLayout(layout);

        btnShowLateralPane.addActionListener(e -> toggleLateralPanel());
        btnUpdate.addActionListener(e -> refreshWindow());

        return panel;
    }

    private JButton createWhiteButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        return button;
    }

    private JPanel createBedroomPane() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3)); // Configuração do layout do bedroomPane
        return panel;
    }

    private void initializeActions() {
        addLateralPanelActions();
    }

    private void addLateralPanelActions() {
        for (Component component : lateralPanel.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                switch (button.getText()) {
                    case "Consultar hospede":
                        button.addActionListener(e -> new PersonManagerWindow().setVisible(true));
                        break;
                    case "Gerenciar empresa":
                        button.addActionListener(e -> new CompanyManagerWindow().setVisible(true));
                        break;
                }
            }
        }
    }

    private void addInitialBedrooms() {
        for (int i = 0; i < BedroomService.getTotal(); i++) {
            addBedroom();
        }
    }

    private void toggleLateralPanel() {
        visiblePanel = !visiblePanel;
        lateralPanel.setVisible(visiblePanel);
    }

    private void refreshWindow() {
        dispose();
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }

    private void addBedroom() {
        JPanel bedroomLabel = new JPanel();
        bedroomLabel.setLayout(new BorderLayout());

        int panelWidth = bedroomPane.getWidth() / 3;
        int panelHeight = bedroomPane.getHeight() / 3;

        Integer num = bedroomCount + 1;

        bedroomLabel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        List<PersonEntity> data = BedroomService.loadAllInBedroom(BedroomService.getById(num));

        if (data == null) {
            data = List.of();
        }

        boolean hasGuests = isSomebodyinBedroom(data);
        Color backgroundColor = determineBackgroundColor(hasGuests);

        JPanel tablePanel = createTablePanel(data, backgroundColor);

        bedroomLabel.add(tablePanel, BorderLayout.CENTER);
        bedroomLabel.add(createRoomLabel(data, num), BorderLayout.NORTH);
        bedroomLabel.add(createButtonPanel(num, backgroundColor), BorderLayout.SOUTH);

        updatePanelColor(bedroomLabel, checkoutDateTime);

        bedroomPane.add(bedroomLabel);
        bedroomPane.revalidate();
        bedroomPane.repaint();
        bedroomCount += 1;
    }

    private Color determineBackgroundColor(boolean hasGuests) {
        if (!hasGuests) {
            return new Color(170, 245, 245);
        } else {
            return new Color(245, 245, 200);
        }
    }

    private JPanel createTablePanel(List<PersonEntity> data, Color backgroundColor) {
        JPanel panel = new JPanel(new BorderLayout());

        DefaultTableModel model = MainTable.createMainTable(data);
        JTable table = new JTable(model);
        table.setFont(table.getFont().deriveFont(table.getFont().getSize()));
        table.setRowHeight(table.getRowHeight() * 2);
        table.setBackground(backgroundColor);
        JScrollPane scrollPane = new JScrollPane(table);

        BedroomEntity bedroom = BedroomService.getById(bedroomCount + 1);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(createCheckoutLabel(bedroom), BorderLayout.SOUTH);

        return panel;
    }

    private JLabel createCheckoutLabel(BedroomEntity bedroom) {
        checkoutDateTime = LocalDateTime.of(2024, 7, 29, 12, 0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedCheckoutDateTime = checkoutDateTime.format(formatter);

        JLabel label = new JLabel(bedroom.getStatus());
        switch (bedroom.getStatus()) {
            case "Disponível" -> label.setForeground(new Color(12, 150, 100));
            case "Ocupado" -> label.setForeground(new Color(122, 0, 0));
            case "Reservado" -> label.setText("Reservado para dia " + formattedCheckoutDateTime + "Empresa");
        }
        System.out.print(bedroom.getId() + " " + bedroom.getStatus());

        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JLabel createRoomLabel(List<PersonEntity> data, Integer num) {
        BedroomEntity bedroomEntity = BedroomService.getById(num);
        if (bedroomEntity == null) {
            // Handle null bedroomEntity case
            bedroomEntity = new BedroomEntity(); // Assuming a default entity
        }

        Integer vacancy = bedroomEntity.getCapacity() - data.size();

        String labelText = "Quarto " + num + "                   " + "Vagas: " + vacancy;
        if (!data.isEmpty()) {
            String companyName = data.get(0).getCompany().getName();
            labelText = "Quarto " + num + "                     " + companyName + "               " + " " + "Vagas: " + vacancy;
        }

        JLabel label = new JLabel(labelText);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JPanel createButtonPanel(Integer num, Color backgroundColor) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 0, 20));
        BedroomEntity be = BedroomService.getById(num);
        assert be != null;
        JButton editButton = TableUtils.createButton("Gerenciar Quarto", Color.WHITE, Color.BLACK, openBedroomWindow(be));
        editButton.setBackground(backgroundColor);

        panel.add(editButton);
        return panel;
    }
    private ActionListener openBedroomWindow(BedroomEntity bedroom) {
        return e -> {
            System.out.print(bedroom.getId());
            new BedroomWindow(bedroom).setVisible(true);
        };
    }

    private void updatePanelColor(JPanel panel, LocalDateTime checkoutDateTime) {
        Color backgroundColor;
        LocalDateTime currentDate = LocalDateTime.now();

        if (currentDate.isAfter(checkoutDateTime)) {
            backgroundColor = new Color(255, 120, 120); // Vermelho claro
        } else if (currentDate.plusDays(1).isAfter(checkoutDateTime)) {
            backgroundColor = new Color(255, 100, 0); // Laranja claro
        } else {
            backgroundColor = new Color(255, 255, 255); // Bege
        }

        panel.setBackground(backgroundColor);
    }

    private void openReservation(ActionEvent e){
        new ReservationManagerWindow().setVisible(true);
    }
    private void openPersonManager(ActionEvent e){
        new PersonManagerWindow().setVisible(true);
    }
    private void openCompanyManager(ActionEvent e){
        new CompanyManagerWindow().setVisible(true);
    }

    public LocalDateTime getCheckoutDateTime() {
        return checkoutDateTime;
    }

    public boolean isSomebodyinBedroom(List<PersonEntity> data) {
        return data != null && !data.isEmpty();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
