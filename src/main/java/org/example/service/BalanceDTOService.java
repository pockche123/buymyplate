package org.example.service;

import org.example.dto.BalanceDTO;
import org.example.dto.BalanceRequestDTO;
import org.example.model.Balance;
import org.example.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceDTOService {
    @Autowired
    private BalanceRepository balanceRepository;

    public static BalanceDTO convertToDTO(Balance balance) {
        BalanceDTO balanceDTO = new BalanceDTO();
        balanceDTO.setBalanceId(balance.getBalanceId());
        balanceDTO.setAmount(balance.getAmount());
        balanceDTO.setUserId(balance.getUser().getId());

        return balanceDTO;
    }

    public BalanceDTO findById(int id){
        return balanceRepository.findById(id).map(BalanceDTOService::convertToDTO).orElse(null);
    }

    public BalanceDTO findByUserId(int customerId){
        Balance balance =  balanceRepository.findByUser_Id(customerId);
        return convertToDTO(balance);
    }

    public BalanceDTO updateBalance(int balanceId, BalanceRequestDTO balanceRequestDTO){
        Balance balance = balanceRepository.findById(balanceId).orElseThrow(() -> new RuntimeException("Balance not found"));

        if(balance.getAmount() != balanceRequestDTO.getAmount()){
            balance.setAmount(balanceRequestDTO.getAmount());
        }

        balanceRepository.save(balance);
        return convertToDTO(balance);
    }
}
