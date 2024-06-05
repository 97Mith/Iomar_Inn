package org.example.view;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class CellRenderer extends JLabel implements TableCellRenderer {
    private final MaskFormatter formatter;

    public CellRenderer(MaskFormatter formatter) {
        this.formatter = formatter;
        setOpaque(true);
    }
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
    ) {
        if (value != null) {
            try {
                setText(formatter.valueToString(value));
            } catch (ParseException e) {
                setText(value.toString());
            }
        }

        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        return this;
    }
}