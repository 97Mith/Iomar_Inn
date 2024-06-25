package org.example.entities;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Entity
@Table(name = "tb_reservation")
public class ReservationEntity {
    @Id
    @Column(name = "reserve_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "reservation_name")
    private String reservationName;

    @Column(name = "check_in", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkIn;

    @Column(name = "check_out", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date checkOut;

    @OneToOne
    @JoinColumn(name = "bedroom_id")
    BedroomEntity bedroom;

    public Integer getId() {
        return id;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
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

    public BedroomEntity getBedroom() {
        return bedroom;
    }

    public void setBedroom(BedroomEntity bedroom) {
        this.bedroom = bedroom;
    }
}
