package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VehiclePlate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plateId;
    private String plateNumber;
    private boolean isPersonalised;
    private boolean isAvailable;

    private double price;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;
}
