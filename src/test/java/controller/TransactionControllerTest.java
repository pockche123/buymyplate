package controller;

import org.example.controller.TransactionController;
import org.example.dto.TransactionDTO;
import org.example.dto.TransactionRequestDTO;
import org.example.service.TransactionDTOService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTest {
    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionDTOService transactionDTOService;

    @Test
    public void test_getAllTransactions() {

        List<TransactionDTO> transactionDTOList = Arrays.asList(
                new TransactionDTO(1, 1, 1, 50.40, LocalDate.now()),
                new TransactionDTO(2, 1, 2, 80.40, LocalDate.now())

        );
        Page<TransactionDTO> page = new PageImpl<>(transactionDTOList);
        when(transactionDTOService.findAllTransactions(0,10)).thenReturn(page);

        ResponseEntity<Page<TransactionDTO>> result = transactionController.getAllTransactions(0,10);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(page, result.getBody());
    }

    @Test
    public void test_getTransactionById() {
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 1, 50.40, LocalDate.now());
        when(transactionDTOService.findTransactionDTOById(1)).thenReturn(transactionDTO);

        ResponseEntity<TransactionDTO> result = transactionController.getTransactionById(1);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(transactionDTO, result.getBody());
    }

    @Test
    public void test_createTransaction(){
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 1, 50.40, LocalDate.now());
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(1, 1, 1, 50.40, LocalDate.now());

        when(transactionDTOService.addTransaction(transactionRequestDTO)).thenReturn(transactionDTO);

        ResponseEntity<TransactionDTO> result = transactionController.createTransaction(transactionRequestDTO);
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(transactionDTO, result.getBody());

    }

    @Test
    public void test_createTransaction_returnsBadRequest(){
        TransactionDTO transactionDTO = null;
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();

        when(transactionDTOService.addTransaction(transactionRequestDTO)).thenReturn(transactionDTO);

        ResponseEntity<TransactionDTO> result = transactionController.createTransaction(transactionRequestDTO);
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        assertEquals(transactionDTO, result.getBody());
    }

    @Test
    public void test_replaceTransaction_returnsOKRequest(){
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 1, 50.40, LocalDate.now());
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(1, 1, 1, 50.40, LocalDate.now());
        when(transactionDTOService.replaceTransaction(1,transactionRequestDTO)).thenReturn(transactionDTO);

        ResponseEntity<TransactionDTO> result = transactionController.replaceTransaction(1, transactionRequestDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(transactionDTO, result.getBody());
    }

    @Test
    public void test_replaceTransaction_returnsBadRequest(){
        TransactionDTO transactionDTO = null;
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        when(transactionDTOService.replaceTransaction(1,transactionRequestDTO)).thenReturn(transactionDTO);

        ResponseEntity<TransactionDTO> result = transactionController.replaceTransaction(1, transactionRequestDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    public void test_updateTransaction_returnsOKRequest(){
        TransactionDTO transactionDTO = new TransactionDTO(1, 1, 1, 50.40, LocalDate.now());
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        transactionRequestDTO.setPricePaid(50.40);
        transactionRequestDTO.setTransactionDate(LocalDate.now());

        when(transactionDTOService.updateTransaction(1, transactionRequestDTO)).thenReturn(transactionDTO);

        ResponseEntity<TransactionDTO> result = transactionController.updateTransaction(1, transactionRequestDTO);

        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(transactionDTO, result.getBody());
    }

    @Test
    public void test_deleteTransactions(){

        when(transactionDTOService.deleteTransaction(1)).thenReturn(true);
        when(transactionDTOService.deleteTransaction(2)).thenReturn(false);

        ResponseEntity<TransactionDTO> result = transactionController.deleteTransaction(1);
        ResponseEntity<TransactionDTO> resultFalse = transactionController.deleteTransaction(2);

        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, resultFalse.getStatusCode());
    }
}
