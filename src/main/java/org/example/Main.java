package org.example;

import org.example.entities.CompanyEntity;
import org.example.view.CompanyManagerWindow;
import org.example.view.NewCompanyWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        NewCompanyWindow newCompanyWindow = new NewCompanyWindow(new CompanyEntity(), new CompanyManagerWindow());

        newCompanyWindow.setVisible(true);
    }
}