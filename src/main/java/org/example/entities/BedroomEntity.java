package org.example.entities;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.util.Date;

@Entity
@Table(name = "tb_bedrooms")
public class BedroomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bedroom_number")
    private Integer id;

    @Column(name = "number_of_beds", nullable = false)
    public Integer capacity;

    @Column(name = "is_a_suite", nullable = false)
    public boolean hasBathroom;

    @Column(name = "situation", nullable = false)
    public String status;

    @Column(nullable = false)
    public double value;

    @Column(name = "check_in")
    private Date checkInDate;

    @Column(name = "check_out")
    private Date checkOutDate;

    @Column(name = "general_total")
    private double totalOfStaying;

    public BedroomEntity() {
    }

    public BedroomEntity(Integer capacity, boolean hasBathroom, String status, double value) {
        this.capacity = capacity;
        this.hasBathroom = hasBathroom;
        this.status = status;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public boolean isHasBathroom() {
        return hasBathroom;
    }

    public void setHasBathroom(boolean hasBathroom) {
        this.hasBathroom = hasBathroom;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public double getTotalOfStaying() {
        return totalOfStaying;
    }

    public void setTotalOfStaying(double totalOfStaying) {
        this.totalOfStaying = totalOfStaying;
    }
}
