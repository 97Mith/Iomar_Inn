package org.example.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class TableUtils {

    public static <T> void newEntityAction(Supplier<T> entitySupplier, JFrame parentFrame, Function<T, JFrame> windowSupplier) {
        T entity = entitySupplier.get();
        JFrame newEntityWindow = windowSupplier.apply(entity);
        newEntityWindow.setVisible(true);
    }

    public static void updateAction(JFrame parentFrame, Supplier<JFrame> frameSupplier) {
        parentFrame.dispose();
        SwingUtilities.invokeLater(() -> frameSupplier.get().setVisible(true));
    }

    public static <T> void editEntityAction(JTable table, DefaultTableModel model, Function<Integer, T> entityFinder, JFrame parentFrame, Function<T, JFrame> windowSupplier) {
        final int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Integer entityId = (Integer) model.getValueAt(selectedRow, 0);
            T entity = entityFinder.apply(entityId);
            JFrame editEntityWindow = windowSupplier.apply(entity);
            editEntityWindow.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum campo selecionado");
        }
    }

    public static void deleteEntityAction(JTable table, DefaultTableModel model, java.util.function.Consumer<Integer> entityDeleter, ActionEvent e, Runnable updateAction) {
        final int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            Integer entityId = (Integer) model.getValueAt(selectedRow, 0);
            entityDeleter.accept(entityId);
            updateAction.run();
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum campo selecionado");
        }
    }

    public static <T> void refreshTable(DefaultTableModel model, List<T> entities, Function<T, Object[]> rowMapper) {
        model.setRowCount(0); // Clear existing data
        for (T entity : entities) {
            model.addRow(rowMapper.apply(entity));
        }
    }

    public static JButton createButton(String text, Color bgColor, Color fgColor, ActionListener action) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.addActionListener(action);
        return button;
    }
    public static JButton createMenuButton(String text, Color bgColor, Color fgColor, ActionListener action, int size) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.PLAIN, size)); // Set the font size
        button.addActionListener(action);
        return button;
    }
}
