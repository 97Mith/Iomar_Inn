package org.example.services;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class Util {
    public static boolean isNullOrEmpty(String valueToValidate, String fieldName){
        if(valueToValidate == null || valueToValidate.trim().isEmpty()){
            JOptionPane.showMessageDialog(
                    null, "O "+ fieldName +" não pode ficar em branco",
                    "Aviso", JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        return true;
    }

    public static boolean minMaxStringSize(int minimum, int maximum, String valueToValidate, String field){
        if(valueToValidate.length() < minimum || valueToValidate.length() > maximum){
            JOptionPane.showMessageDialog(
                    null,
                    "O "+ field +" deve ter entre " + minimum + " e "+ maximum +" caracteres",
                    "Aviso", JOptionPane.ERROR_MESSAGE
            );
            return false;
        }
        return  true;
    }

    // Classe para filtrar entrada para aceitar apenas números
    public static class NumberOnlyFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) return;
            if (string.matches("[0-9]+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text == null) return;
            if (text.matches("[0-9]+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
