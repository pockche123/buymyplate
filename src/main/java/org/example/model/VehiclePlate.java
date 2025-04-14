package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(
        indexes = {
                @Index(name="idx_plate_number", columnList = "plateNumber")
        }
)
public class VehiclePlate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plateId;
    @Column(nullable=false, unique=true)
    private String plateNumber;
    private Boolean personalised;
    private Boolean available;

    private double price;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;



}
