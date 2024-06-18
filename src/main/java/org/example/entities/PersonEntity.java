package org.example.entities;

import org.hibernate.validator.constraints.br.CPF;
import javax.persistence.*;

@Entity
@Table(name = "tb_guests")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    public String name;

    @Column(name = "last_name")
    public String surName;

    @ManyToOne(optional = true)
    @JoinColumn(name = "company_id")
    public CompanyEntity company;

    @OneToOne(optional = true)
    @JoinColumn(name = "bedroom_number", referencedColumnName = "bedroom_number")
    public BedroomEntity bedroom;

    @Column(name = "contact_number")
    public String phoneNumber;

    @CPF
    public String cpf;

    public PersonEntity() {
    }

    public PersonEntity(String name, String surName, String phoneNumber, String cpf) {
        this.name = name;
        this.surName = surName;
        this.phoneNumber = phoneNumber;
        this.cpf = cpf;
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

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public CompanyEntity getCompanyEntity() {
        return company;
    }

    public void setCompanyEntity(CompanyEntity companyEntity) {
        this.company = companyEntity;
    }

    public BedroomEntity getBedroom() {
        return bedroom;
    }

    public void setBedroom(BedroomEntity bedroom) {
        this.bedroom = bedroom;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public CompanyEntity getCompany() {
        return company;
    }
}
