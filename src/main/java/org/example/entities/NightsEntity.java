package org.example.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "nights")
public class NightsEntity {
    @Id
    @Column(name = "night_id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "bedroom_number", referencedColumnName = "bedroom_number")
    private BedroomEntity bedroom;

    @OneToMany
    private List<PersonEntity> guests;

    private String responsible;

    @Column(name = "check_in", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkIn;

    @Column(name = "check_out")
    @Temporal(TemporalType.DATE)
    private Date checkOut;

    @Column(name = "nights_value")
    private double nightsValue;

    @OneToMany
    @JoinColumn(name = "products", referencedColumnName = "resgister")
    private List<ProductVO> products;
    @Column(name = "products_value")
    private double productsValue;

    @OneToMany
    @JoinColumn(name = "products", referencedColumnName = "resgister")
    private List<ProductVO> laundries;
    @Column(name = "laundry_value")
    private double laundryValue;

    @Column(name = "total")
    private double totalValue;

    public Integer getId() {
        return id;
    }

    public BedroomEntity getBedroom() {
        return bedroom;
    }

    public void setBedroom(BedroomEntity bedroom) {
        this.bedroom = bedroom;
    }

    public List<PersonEntity> getGuests() {
        return guests;
    }

    public void setGuests(List<PersonEntity> guests) {
        this.guests = guests;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public double getNightsValue() {
        return nightsValue;
    }

    public void setNightsValue(double nightsValue) {
        this.nightsValue = nightsValue;
    }

    public List<ProductVO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductVO> products) {
        this.products = products;
    }

    public double getProductsValue() {
        return productsValue;
    }

    public void setProductsValue(double productsValue) {
        this.productsValue = productsValue;
    }

    public List<ProductVO> getLaundries() {
        return laundries;
    }

    public void setLaundries(List<ProductVO> laundries) {
        this.laundries = laundries;
    }

    public double getLaundryValue() {
        return laundryValue;
    }

    public void setLaundryValue(double laundryValue) {
        this.laundryValue = laundryValue;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
}
