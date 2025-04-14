package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Index;
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
    private String plateNumber;
    private boolean isPersonalised;
    private boolean isAvailable;

    private double price;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = true)
    private Customer customer;
}
