package org.example.entities;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.br.CNPJ;

import javax.persistence.*;

@Entity
@Table(name = "tb_companies")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Integer id;

    @Column(name = "company_name", nullable = false)
    public String name;

    @Column(name = "corporate_reason", nullable = false)
    public String corporateReason;

    @CNPJ
    @Column(nullable = false)
    public String cnpj;

    @Column(name = "state_inscription")
    public String stateInscription;

    @Column(name = "contact_number")
    public String phoneNumber;

    @Email
    public String email;

    public CompanyEntity() {
    }


    public CompanyEntity(String name, String corporateReason, String cnpj, String stateInscription, String phoneNumber, String email) {
        this.name = name;
        this.corporateReason = corporateReason;
        this.cnpj = cnpj;
        this.stateInscription = stateInscription;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCorporateReason() {
        return corporateReason;
    }

    public void setCorporateReason(String corporateReason) {
        this.corporateReason = corporateReason;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getStateInscription() {
        return stateInscription;
    }

    public void setStateInscription(String stateInscription) {
        this.stateInscription = stateInscription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return name;
    }
}
