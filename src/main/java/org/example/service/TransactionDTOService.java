package org.example.service;

import org.example.dto.TransactionDTO;
import org.example.dto.TransactionRequestDTO;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.model.VehiclePlate;
import org.example.repository.TransactionRepository;
import org.example.repository.UserRepository;
import org.example.repository.VehiclePlateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionDTOService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VehiclePlateRepository vehiclePlateRepository;

    public static TransactionDTO convertToTransactionDTO(Transaction transaction){
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(transaction.getTransactionId());
        transactionDTO.setUserId(transaction.getUser().getId());
        transactionDTO.setVehiclePlateId(transaction.getPlate().getPlateId());
        transactionDTO.setTransactionDate(transaction.getTransactionDate());
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

    public TransactionDTO addTransaction(TransactionRequestDTO transactionRequestDTO){
        Transaction transaction = new Transaction();
        User user = userRepository.findById(transactionRequestDTO.getUserId()).orElse(null);
        VehiclePlate vehiclePlate = vehiclePlateRepository.findById(transactionRequestDTO.getVehiclePlateId()).orElse(null);
        if(user == null || vehiclePlate == null  || transactionRequestDTO.getTransactionDate() == null || transactionRequestDTO.getPricePaid() == null){
            return null;
        }
        transaction.setUser(user);
        transaction.setPlate(vehiclePlate);
        transaction.setTransactionDate(transactionRequestDTO.getTransactionDate());
        transaction.setPricePaid(transactionRequestDTO.getPricePaid());
        transactionRepository.save(transaction);
        return convertToTransactionDTO(transaction);
    }
//public TransactionDTO addTransaction(TransactionRequestDTO transactionRequestDTO) {
//    Transaction transaction = new Transaction();
//    User user = userRepository.findById(transactionRequestDTO.getUserId())
//            .orElseThrow(() -> new RuntimeException("User not found"));
//    VehiclePlate vehiclePlate = vehiclePlateRepository.findById(transactionRequestDTO.getVehiclePlateId())
//            .orElseThrow(() -> new RuntimeException("Vehicle plate not found"));
//
//    if (transactionRequestDTO.getTransactionDate() == null || transactionRequestDTO.getPricePaid() == null) {
//        throw new RuntimeException("Transaction date or price paid cannot be null");
//    }
//
//    transaction.setUser(user);
//    transaction.setPlate(vehiclePlate);
//    transaction.setTransactionDate(transactionRequestDTO.getTransactionDate());
//    transaction.setPricePaid(transactionRequestDTO.getPricePaid());
//
//    Transaction savedTransaction = transactionRepository.saveAndFlush(transaction); // Use saveAndFlush
//    return convertToTransactionDTO(savedTransaction);
//}


    public TransactionDTO replaceTransaction(int id, TransactionRequestDTO replacedTransaction){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction id not found"));
        User user = userRepository.findById(replacedTransaction.getUserId()).orElse(null);
        VehiclePlate vehiclePlate = vehiclePlateRepository.findById(replacedTransaction.getVehiclePlateId()).orElse(null);

        if(user == null || vehiclePlate == null || transaction == null  || replacedTransaction.getTransactionDate() == null){
            return null;
        }
        transaction.setPricePaid(replacedTransaction.getPricePaid());
        transaction.setTransactionDate(replacedTransaction.getTransactionDate());
        transaction.setUser(user);
        transaction.setPlate(vehiclePlate);
        transactionRepository.save(transaction);
        return convertToTransactionDTO(transaction);
    }

    public TransactionDTO updateTransaction(int id, TransactionRequestDTO updatedTransaction){
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new RuntimeException("Transaction id not found"));

        if(updatedTransaction.getPricePaid() != null){
            transaction.setPricePaid(updatedTransaction.getPricePaid());
        }

        if(updatedTransaction.getTransactionDate() != null) {
            transaction.setTransactionDate(updatedTransaction.getTransactionDate());
        }

        if(updatedTransaction.getUserId() != null) {
            User user = userRepository.findById(updatedTransaction.getUserId()).orElseThrow(()->new RuntimeException("User id not found"));
            transaction.setUser(user);
        }

        if(updatedTransaction.getVehiclePlateId() != null) {
            VehiclePlate vehiclePlate = vehiclePlateRepository.findById(updatedTransaction.getVehiclePlateId()).orElseThrow(()->new RuntimeException("VehiclePlate id not found"));
            transaction.setPlate(vehiclePlate);
        }
        transactionRepository.save(transaction);
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
