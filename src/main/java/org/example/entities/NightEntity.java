package org.example.entities;

import javax.persistence.*;

@Entity
@Table(name = "faturas")
public class NightEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "night_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "bedroom_id")
    private BedroomEntity bedroom;

    @OneToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @OneToOne
    @JoinColumn(name = "guest_id")
    private PersonEntity client;

    @Column(name = "situation")
    private boolean isPaid;

    private String obs;

    @Column(name = "products_total")
    private double productsValue;

    @Column(name = "laundry_total")
    private double laundryValue;

    @Column(name = "number_of_nights")
    private Integer nightsNum;

    @Column(name = "nights_value_total")
    private double nightsValue;

    private double total;

    public Integer getId() {
        return id;
    }

    public BedroomEntity getBedroom() {
        return bedroom;
    }

    public void setBedroom(BedroomEntity bedroom) {
        this.bedroom = bedroom;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyEntity company) {
        this.company = company;
    }

    public PersonEntity getClient() {
        return client;
    }

    public void setClient(PersonEntity client) {
        this.client = client;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public double getProductsValue() {
        return productsValue;
    }

    public void setProductsValue(double productsValue) {
        this.productsValue = productsValue;
    }

    public double getLaundryValue() {
        return laundryValue;
    }

    public void setLaundryValue(double laundryValue) {
        this.laundryValue = laundryValue;
    }

    public Integer getNightsNum() {
        return nightsNum;
    }

    public void setNightsNum(Integer nightsNum) {
        this.nightsNum = nightsNum;
    }

    public double getNightsValue() {
        return nightsValue;
    }

    public void setNightsValue(double nightsValue) {
        this.nightsValue = nightsValue;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
