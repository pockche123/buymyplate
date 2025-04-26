package org.example.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BalanceRequestDTO {
    private int balanceId;
    private double amount;
    private int userId;
}
