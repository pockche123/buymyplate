package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
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
        name="vehicle_plates",
        indexes = {
                @Index(name="idx_plate_number", columnList = "plateNumber")
        }
)
public class VehiclePlate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int plateId;
    @Column(nullable=false, unique=true)
    @Size(
            min = 2,
            max = 8,
            message = "UK plate must be between 5 and 8 characters, e.g. AB12 CDE"
    )
    private String plateNumber;
    private Boolean personalised;
    private Boolean available;

    private double price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = true)
    private User user;



}
