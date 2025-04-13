package org.example.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehiclePlateDTO {
    private int vehicleId;
    @NotBlank
    private String plateNumber;
    private boolean isPersonalised;
    private boolean isAvailable;
    @PositiveOrZero
    @Digits(integer = 8, fraction = 2)
    private double price;
    @Positive
    private Integer customerId;

}
