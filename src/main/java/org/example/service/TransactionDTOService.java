package org.example.service;

import org.example.dto.TransactionDTO;
import org.example.model.Transaction;
import org.example.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionDTOService {

    @Autowired
    private TransactionRepository transactionRepository;

    public static TransactionDTO convertToTransactionDTO(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(transaction.getTransactionId());
        transactionDTO.setCustomerId(transaction.getCustomer().getId());
        transactionDTO.setVehiclePlateId(transaction.getPlate().getPlateId());
        transactionDTO.setLocalDate(transaction.getTransactionDate());
        transactionDTO.setPricePaid(transaction.getPricePaid());

        return transactionDTO;

    }

    public Page<TransactionDTO> findAllTransactions(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return transactionRepository.findAll(pageable).map(TransactionDTOService::convertToTransactionDTO);
    }

    public TransactionDTO findTransactionDTOById(int id){
        return transactionRepository.findById(id).map(TransactionDTOService::convertToTransactionDTO).orElseThrow(() -> new RuntimeException("Transaction id not found"));
    }

    public TransactionDTO addTransaction(Transaction transaction){
        transactionRepository.save(transaction);
        return convertToTransactionDTO(transaction);
    }

    public TransactionDTO replaceTransaction(int id, Transaction replacedTransaction){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction id not found"));

        transaction.setPricePaid(replacedTransaction.getPricePaid());
        transaction.setTransactionDate(replacedTransaction.getTransactionDate());
        transaction.setCustomer(replacedTransaction.getCustomer());
        transaction.setPlate(replacedTransaction.getPlate());

        return convertToTransactionDTO(transaction);
    }

    public TransactionDTO updateTransaction(int id, Transaction updatedTransaction){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction id not found"));


        if(updatedTransaction.getPricePaid() != null){
            transaction.setPricePaid(updatedTransaction.getPricePaid());
        }

        if(updatedTransaction.getTransactionDate() != null) {
            transaction.setTransactionDate(updatedTransaction.getTransactionDate());
        }

        if(updatedTransaction.getCustomer() != null) {
            transaction.setCustomer(updatedTransaction.getCustomer());
        }

        if(updatedTransaction.getPlate() != null) {
            transaction.setPlate(updatedTransaction.getPlate());
        }

        return convertToTransactionDTO(transaction);
    }

    public boolean deleteTransaction(int id){
        return transactionRepository.findById(id).map(
                transaction -> {
                    transactionRepository.delete(transaction);
                    return true;
                }
        ).orElse(false);
    }


}
