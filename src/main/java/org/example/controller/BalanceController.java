package org.example.controller;

import org.example.dto.BalanceDTO;
import org.example.dto.BalanceRequestDTO;
import org.example.service.BalanceDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BalanceController {
    @Autowired
    public BalanceDTOService balanceDTOService;

    @GetMapping("/v1/balance/{id}")
    public ResponseEntity<BalanceDTO> getBalanceById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(balanceDTOService.findById(id));
    }


    @GetMapping("/v1/balance/customerId/{id}")
    public ResponseEntity<BalanceDTO> getBalanceByCustomerId(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(balanceDTOService.findByUserId(id));
    }




    @PatchMapping("/v1/balance/{id}")
    public ResponseEntity<BalanceDTO> updateBalance(@PathVariable(name = "id") int id, @RequestBody BalanceRequestDTO balanceRequestDTODTO) {
        return ResponseEntity.ok(balanceDTOService.updateBalance(id,balanceRequestDTODTO));
    }

}
