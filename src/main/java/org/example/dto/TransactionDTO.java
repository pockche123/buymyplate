package org.example.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

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
    private LocalDate localDate;
}
