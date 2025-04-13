package org.example.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDTO {
    private int transactionId;
    @Positive
    @NotNull
    private int customerId;
    @Positive
    @NotNull
    private int vehiclePlateId;
    @PositiveOrZero
    @Digits(integer = 8, fraction = 2)
    private double pricePaid;
}
