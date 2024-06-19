package org.example.services;

import org.example.entities.CompanyEntity;
import org.example.repositories.CompanyRepository;

import javax.swing.*;
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
    public static List<CompanyEntity> getAll2(){
        try{
            List<CompanyEntity> all = CompanyRepository.findAll();
            if (!all.isEmpty()){all.remove(0);}
            return all;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean validateFields(String name, String cr, String cnpj, String si, String pn, String email){
        boolean valid = Util.isNullOrEmpty(name, "campo nome");
        if(valid) valid = Util.minMaxStringSize(3, 35, name, "campo nome");

        if(valid) valid = Util.isNullOrEmpty(cr, "campo Razão social");
        if(valid) valid = Util.minMaxStringSize(5, 60, cr, "campo Razão Social");
        if(valid) valid = isCrExists(cr);

        if(valid) valid = Util.isNullOrEmpty(cnpj, "CNPJ");
        if(valid) valid = Util.minMaxStringSize(14, 15, cnpj, "CNPJ");
        if(valid) valid = isCnpjExists(cnpj);

        if(valid && !si.isEmpty()) valid = Util.minMaxStringSize(7, 12, si, "campo Inscrição Estadual");

        if(valid && !pn.isEmpty()) valid = Util.minMaxStringSize(11, 12, pn, "campo Telefone");

        if(valid && !email.isEmpty()) valid = Util.minMaxStringSize(11, 45, email, "Email");

        return valid;
    }

    private static void deleteJustForAMethod(List<CompanyEntity> companies, Integer id) {
        companies.removeIf(company -> company.getId().equals(id));
    }

    public static void createEmptyCompany(){
        CompanyRepository.update(new CompanyEntity(0,"Sem empresa"," - "," - "));
    }

    public static List<CompanyEntity> getByName(String name){
        List<CompanyEntity> companiesSearched = CompanyRepository.findByName(name);
        assert companiesSearched != null;
        if(companiesSearched.isEmpty()){
            JOptionPane.showMessageDialog(null,
                    "Nenhuma empresa encontrada",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            companiesSearched = getAll();
        }
        return companiesSearched;
    }
    public static boolean isCnpjExists(String cnpj){
        try{
            if(CompanyRepository.isCnpjExists(cnpj)){
                JOptionPane.showMessageDialog(null,
                        "O CNPJ já existe.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }else {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
    public static boolean isCrExists(String cr){
        try{
            if(CompanyRepository.isCrExists(cr)){
                JOptionPane.showMessageDialog(null,
                        "A Razão Social já existe.",
                        "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }else {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erro.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
}
