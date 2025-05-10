package org.example.controller;

import org.example.dto.TransactionDTO;
import org.example.dto.TransactionRequestDTO;
import org.example.service.TransactionDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransactionController {

    @Autowired
    private TransactionDTOService transactionDTOService;



    @GetMapping("/v1/transactions")
    public ResponseEntity<Page<TransactionDTO>> getAllTransactions(
            @RequestParam(name = "page", defaultValue = "0")int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(transactionDTOService.findAllTransactions(page, size));
    }

    @GetMapping("/v1/transactions/{id}")
    public ResponseEntity<TransactionDTO> getTransactionById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(transactionDTOService.findTransactionDTOById(id));
    }

    @PostMapping("/v1/transactions")
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
        TransactionDTO transactionDTO = transactionDTOService.addTransaction(transactionRequestDTO);
        if(transactionDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionDTO);
    }

    @PutMapping("/v1/transactions/{id}")
    public ResponseEntity<TransactionDTO> replaceTransaction(@PathVariable(name="id") int id, @RequestBody TransactionRequestDTO transactionRequestDTO) {
        TransactionDTO transactionDTO = transactionDTOService.replaceTransaction(id, transactionRequestDTO);
        if(transactionDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionDTO);
    }

    @PatchMapping("/v1/transactions/{id}")
    public ResponseEntity<TransactionDTO> updateTransaction(@PathVariable(name="id") int id, @RequestBody TransactionRequestDTO transactionRequestDTO) {
        TransactionDTO transactionDTO = transactionDTOService.updateTransaction(id, transactionRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(transactionDTO);
    }

    @DeleteMapping("/v1/transactions/{id}")
    public ResponseEntity<TransactionDTO> deleteTransaction(@PathVariable(name = "id") int id) {
        boolean result = transactionDTOService.deleteTransaction(id);
        if(!result) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
