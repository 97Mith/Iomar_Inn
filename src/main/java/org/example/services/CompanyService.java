package org.example.services;

import org.example.entities.CompanyEntity;
import org.example.repositories.CompanyRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class CompanyService {
    public static boolean createOrUpdate(CompanyEntity companyEntity){
        try{
            CompanyRepository.update(companyEntity);
            JOptionPane.showMessageDialog(null, "Empresa mantida com sucesso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao manter empresa!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void deleteById(Integer id){
        try{
            CompanyRepository.delete(id);
            JOptionPane.showMessageDialog(null, "Empresa excluída com sucesso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao deletar empresa!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    public static List<CompanyEntity> getAll(){
        try{
            return CompanyRepository.findAll();
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean validateFields(String name, String cr, String cnpj, String si, String pn, String email){
        boolean valid = Util.isNullOrEmpty(name, "campo nome");
        if(valid) valid = Util.minMaxStringSize(3, 35, name, "campo nome");
        if(valid) valid = Util.isNullOrEmpty(cnpj, "CNPJ");
        if(valid) valid = Util.minMaxStringSize(14, 15, cnpj, "CNPJ");
        if(valid && !cr.isEmpty()) valid = Util.minMaxStringSize(5, 60, cr, "campo Razão Social");
        if(valid && !cr.isEmpty()) valid = Util.minMaxStringSize(5, 60, cr, "campo Razão Social");
        if(valid && !si.isEmpty()) valid = Util.minMaxStringSize(7, 12, si, "campo Inscrição Estadual");
        if(valid && !pn.isEmpty()) valid = Util.minMaxStringSize(11, 12, pn, "campo Telefone");
        if(valid && !email.isEmpty()) valid = Util.minMaxStringSize(11, 45, email, "Email");

        return valid;
    }

    private static void deleteJustForAMethod(List<CompanyEntity> companies, Integer id) {
        companies.removeIf(company -> company.getId().equals(id));
    }

    public static DefaultTableModel createCompanyTable(List<CompanyEntity> companyList) {
        String[] columnNames = {
                "ID", "Nome", "Razão Social", "Telefone",
                "CNPJ", "Inscrição Estadual", "Email"
        };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        for (CompanyEntity company : companyList) {
            Object[] rowData = {
                    company.getId(), company.getName(), company.getCorporateReason(), company.getPhoneNumber(),
                    company.getCnpj(), company.getStateInscription(), company.getEmail()
            };

            tableModel.addRow(rowData);
        }
        return tableModel;
    }

    public static boolean isCompanyUnique(String corporateReason, String cnpj) {
        List<CompanyEntity> companies = getAll();

        boolean reasonExists = companies.stream()
                .anyMatch(c -> c.getCorporateReason().equals(corporateReason));

        boolean cnpjExists = companies.stream()
                .anyMatch(c -> c.getCnpj().equals(cnpj));

        if (reasonExists && cnpjExists) {
            JOptionPane.showMessageDialog(null,
                    "A razão social e o CNPJ já existem.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return false;

        } else if (reasonExists) {
            JOptionPane.showMessageDialog(null,
                    "A razão social já existe.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return false;

        } else if (cnpjExists) {
            JOptionPane.showMessageDialog(null,
                    "O CNPJ já existe.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }
}
