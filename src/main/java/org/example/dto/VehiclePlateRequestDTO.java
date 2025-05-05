package org.example.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VehiclePlateRequestDTO {
    private Integer vehicleId;
    @NotBlank
    @Size(
            min = 5,
            max = 8,
            message = "UK plate must be between 5 and 8 characters, e.g. AB12 CDE"
    )
    private String plateNumber;
    private Boolean personalised;
    private Boolean available;
    @PositiveOrZero
    @Digits(integer = 8, fraction = 2)
    private Double price;
    private Integer userId;
}