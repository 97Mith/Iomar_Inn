package org.example.entities;

import javax.persistence.*;

@Entity
@Table(name = "products_registration")
public class ProductVO {
    @Id
    @Column(name = "resgister")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer registerNum;

    @Column(name = "quantity")
    private Integer qnt = 0;

    private String description;

    @Column(name = "unitary_value", nullable = false)
    private double unValue;

    @Column(name = "sub_total")
    private double subTotal;

    @Column(name = "bedroom")
    private Integer bedroomNumber;

    @OneToOne(optional = true)
    @JoinColumn(name = "company_id")
    private Integer companyId;

    @OneToOne(optional = true)
    @JoinColumn(name = "customer")
    private PersonEntity guestId;

    @Column(name = "observation")
    private String obs;

    @Column(name = "is_a_laundry_service")
    private boolean isLaundry = false;

    public ProductVO() {
    }

    public ProductVO(Integer qnt, String description, double unValue, double subTotal, Integer bedroomNumber, Integer companyId, PersonEntity guestId, String obs, boolean isLaundry) {
        this.qnt = qnt;
        this.description = description;
        this.unValue = unValue;
        this.subTotal = subTotal;
        this.bedroomNumber = bedroomNumber;
        this.companyId = companyId;
        this.guestId = guestId;
        this.obs = obs;
        this.isLaundry = isLaundry;
    }

    public Integer getQnt() {
        return qnt;
    }

    public void setQnt(Integer qnt) {
        this.qnt = qnt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnValue() {
        return unValue;
    }

    public void setUnValue(double unValue) {
        this.unValue = unValue;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getBedroomNumber() {
        return bedroomNumber;
    }

    public void setBedroomNumber(Integer bedroomNumber) {
        this.bedroomNumber = bedroomNumber;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public PersonEntity getGuestId() {
        return guestId;
    }

    public void setGuestId(PersonEntity guestId) {
        this.guestId = guestId;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public boolean isLaundry() {
        return isLaundry;
    }

    public void setLaundry(boolean laundry) {
        isLaundry = laundry;
    }
}
