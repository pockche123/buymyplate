package service;

import org.example.dto.TransactionDTO;
import org.example.dto.TransactionRequestDTO;
import org.example.model.Transaction;
import org.example.model.User;
import org.example.model.VehiclePlate;
import org.example.repository.TransactionRepository;
import org.example.repository.UserRepository;
import org.example.repository.VehiclePlateRepository;
import org.example.service.TransactionDTOService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionDTOServiceTest {
    @InjectMocks
    private TransactionDTOService transactionDTOService;

    @Mock
    private TransactionRepository transactionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private VehiclePlateRepository vehiclePlateRepository;


    @Test
    public void test_convertToTransactionDTO(){
        User user = new User();
        user.setId(1);
        VehiclePlate plate = new VehiclePlate();
        plate.setPlateId(1);
        plate.setPrice(50.50);
        Transaction transaction = new Transaction(1,user,plate, plate.getPrice(), LocalDate.now());

        TransactionDTO transactionDTO = transactionDTOService.convertToTransactionDTO(transaction);


        assertEquals(1, transactionDTO.getUserId());
        assertEquals(1, transactionDTO.getTransactionId());
        assertEquals(1, transactionDTO.getVehiclePlateId());
        assertEquals(50.50, transactionDTO.getPricePaid());
        assertEquals(LocalDate.now(), transactionDTO.getTransactionDate());
    }

    @Test
    public void test_findAllTransactions(){
        List<Transaction> transactionList = Arrays.asList(
                new Transaction(1, new User(), new VehiclePlate(), 50.50, LocalDate.now()),
                new Transaction(2, new User(), new VehiclePlate(), 70.60, LocalDate.now())
        );
        Pageable pageable = PageRequest.of(0,10);
        Page<Transaction> expectedPage= new PageImpl<>(transactionList,pageable, 2);

        when(transactionRepository.findAll(pageable)).thenReturn(expectedPage);

        Page<TransactionDTO> actualPage = transactionDTOService.findAllTransactions(0,10);
        List<TransactionDTO> actualContent = actualPage.getContent();

        assertEquals(2, actualContent.size());
        System.out.println(actualContent);
        assertEquals(50.50, actualContent.get(0).getPricePaid());
        assertEquals(70.60, actualContent.get(1).getPricePaid());
    }

    @Test
    public void test_findTransactionById(){
        Transaction transaction = new Transaction(1, new User(), new VehiclePlate(), 50.50, LocalDate.now());

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

        TransactionDTO transactionDTO = transactionDTOService.findTransactionDTOById(1);

        assertEquals(50.50, transactionDTO.getPricePaid());
    }

    @Test
    public void test_findTransactionById_throwsException(){
        when(transactionRepository.findById(1)).thenThrow(new RuntimeException("Transaction id not found"));

        assertThrows(RuntimeException.class, ()-> {
            transactionDTOService.findTransactionDTOById(1);
        });

        verify(transactionRepository).findById(1);
    }

    @Test
    public void test_addTransaction() {
        // Arrange
        User mockUser = new User();
        VehiclePlate mockPlate = new VehiclePlate();
        when(userRepository.findById(1)).thenReturn(Optional.of(mockUser));
        when(vehiclePlateRepository.findById(1)).thenReturn(Optional.of(mockPlate));

        Transaction savedTransaction = new Transaction();

        savedTransaction.setTransactionId(1);
        savedTransaction.setUser(mockUser);
        savedTransaction.setPlate(mockPlate);
        savedTransaction.setPricePaid(50.50);
        savedTransaction.setTransactionDate(LocalDate.now());

        when(transactionRepository.save(any(Transaction.class))).thenReturn(savedTransaction);

        // Act
        TransactionDTO result = transactionDTOService.addTransaction(
                new TransactionRequestDTO(1, 1, 1, 50.50, LocalDate.now())
        );

        // Assert
//        assertEquals(1, result.getTransactionId());
        assertEquals(50.50, result.getPricePaid());
        assertEquals(LocalDate.now(), result.getTransactionDate());
    }

    @Test
    public void test_replaceTransaction(){
        User user = new User();
        VehiclePlate vehiclePlate = new VehiclePlate();
        Transaction transaction = new Transaction(1, user, vehiclePlate, 50.50, LocalDate.now());
        TransactionRequestDTO replacedTransaction = new TransactionRequestDTO(1, 1, 1, 60.70, LocalDate.now().minusDays(1));
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(vehiclePlateRepository.findById(1)).thenReturn(Optional.of(vehiclePlate));
        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

        TransactionDTO actualTransaction =transactionDTOService.replaceTransaction(1, replacedTransaction);

        assertEquals(60.70, actualTransaction.getPricePaid());
        assertEquals(LocalDate.now().minusDays(1), actualTransaction.getTransactionDate());
    }


    @Test
    public void test_replaceTransaction_throwRuntimeException(){
        TransactionRequestDTO expectedTransaction = new TransactionRequestDTO();
        when(transactionRepository.findById(1)).thenThrow(new RuntimeException("Transaction id not found"));

        assertThrows(RuntimeException.class, () -> {
            transactionDTOService.replaceTransaction(1,expectedTransaction);
        });

        verify(transactionRepository).findById(1);
    }

    @Test
    public void test_updateTransaction(){
        Transaction transaction = new Transaction(1, new User(), new VehiclePlate(), 50.50, LocalDate.now());
        TransactionRequestDTO updatedTransaction = new TransactionRequestDTO();
        updatedTransaction.setTransactionDate(LocalDate.now().minusDays(1));
        updatedTransaction.setPricePaid(70.80);


        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

        TransactionDTO actualTransaction = transactionDTOService.updateTransaction(1,updatedTransaction);

        assertEquals(70.80, actualTransaction.getPricePaid());
        assertEquals(LocalDate.now().minusDays(1), actualTransaction.getTransactionDate());

    }

    @Test
    public void test_deleteTransactionReturnsTrueAndFalse(){
        Transaction transaction = new Transaction();
        transaction.setTransactionId(1);

        when(transactionRepository.findById(1)).thenReturn(Optional.of(transaction));

        boolean result = transactionDTOService.deleteTransaction(1);
        boolean resultFalse = transactionDTOService.deleteTransaction(2);

        assertTrue(result);
        assertFalse(resultFalse);
    }


}
