package org.example.service;

import org.example.dto.BalanceDTO;
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
        balanceDTO.setCustomerId(balance.getCustomer().getId());

        return balanceDTO;
    }

    public BalanceDTO findById(int id){
        return balanceRepository.findById(id).map(BalanceDTOService::convertToDTO).orElse(null);
    }

}
