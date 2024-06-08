package org.example.services;

import org.example.entities.BedroomEntity;
import org.example.entities.CompanyEntity;
import org.example.entities.PersonEntity;
import org.example.repositories.PersonRepository;

import javax.swing.*;
import java.util.List;

public class PersonService {

    public static boolean createOrUpdate(PersonEntity personEntity) {
        try {
            PersonRepository.updatePerson(personEntity);
            JOptionPane.showMessageDialog(null, "Pessoa mantida com sucesso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao manter pessoa!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static void deleteById(Integer id) {
        try {
            boolean deleted = PersonRepository.deletePerson(id);
            if (deleted) {
                JOptionPane.showMessageDialog(null, "Pessoa excluída com sucesso!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Pessoa não encontrada!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao deletar pessoa!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static List<PersonEntity> getAll() {
        try {
            return PersonRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar todas as pessoas!", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static List<PersonEntity> findByName(String name) {
        try {
            return PersonRepository.findByName(name);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar pessoas pelo nome!", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static PersonEntity findById(Integer id) {
        try {
            return PersonRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar pessoa pelo ID!", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static List<PersonEntity> findByCompany(CompanyEntity company) {
        try {
            return PersonRepository.findByCompany(company);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar pessoas pela empresa!", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static List<PersonEntity> findByBedroom(BedroomEntity bedroom) {
        try {
            return PersonRepository.findByBedroom(bedroom);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao buscar pessoas pelo quarto!", "Erro", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    public static boolean insertOrRemoveBedroom(Integer personId, BedroomEntity bedroom) {
        try {
            PersonRepository.insertOrRemoveBedroom(personId, bedroom);
            JOptionPane.showMessageDialog(null, "Quarto atualizado com sucesso para a pessoa!", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao atualizar quarto para a pessoa!", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public static boolean validateFields(String name, String cpf, String sn, String pn){
        boolean valid = Util.isNullOrEmpty(name, "campo nome");
        if(valid) valid = Util.minMaxStringSize(3, 35, name, "campo nome");
        if(valid && !cpf.isEmpty()) valid = Util.minMaxStringSize(11, 12, cpf, "CPF");
        if(valid && !sn.isEmpty()) valid = Util.minMaxStringSize(3, 60, sn, "campo Sobrenome");
        if(valid && !pn.isEmpty()) valid = Util.minMaxStringSize(11, 12, pn, "campo Telefone");

        return valid;
    }
}
