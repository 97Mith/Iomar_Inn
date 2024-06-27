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

    @Column(name = "products_value")
    private double productsValue;

    @Column(name = "laundry_value")
    private double laundryValue;

    @Column(name = "total")
    private double totalValue;
}
