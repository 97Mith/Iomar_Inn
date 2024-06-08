package org.example.entities;

import javax.persistence.*;

@Entity
@Table(name = "tb_products_registration")
public class ProductVO {
    @Id
    @Column(name = "resgister")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer registerNum;

    @Column(name = "product_name", nullable = false)
    private String name;

    @Column(name = "quantity")
    private Integer qnt = 0;

    private String description;

    @Column(name = "unitary_value", nullable = false)
    private double unValue;

    @Column(name = "sub_total")
    private double subTotal;

    @ManyToOne
    @JoinColumn(name = "bedroom_number")
    private BedroomEntity bedroomNumber;

    @ManyToOne(optional = true)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

    @OneToOne(optional = true)
    @JoinColumn(name = "customer")
    private PersonEntity guestId;

    @Column(name = "observation")
    private String obs;

    @Column(name = "is_a_laundry_service")
    private boolean isLaundry = false;

    public ProductVO() {
    }

    public ProductVO(Integer qnt, String description, double unValue, double subTotal, BedroomEntity bedroomNumber, CompanyEntity companyId, PersonEntity guestId, String obs, boolean isLaundry) {
        this.qnt = qnt;
        this.description = description;
        this.unValue = unValue;
        this.subTotal = subTotal;
        this.bedroomNumber = bedroomNumber;
        this.company = companyId;
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

    public BedroomEntity getBedroomNumber() {
        return bedroomNumber;
    }

    public void setBedroomNumber(BedroomEntity bedroomNumber) {
        this.bedroomNumber = bedroomNumber;
    }

    public CompanyEntity getCompanyId() {
        return company;
    }

    public void setCompanyId(CompanyEntity companyId) {
        this.company = companyId;
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
