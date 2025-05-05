package controller;

import org.example.controller.BalanceController;
import org.example.dto.BalanceDTO;
import org.example.dto.BalanceRequestDTO;
import org.example.service.BalanceDTOService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BalanceControllerTest {
    @InjectMocks
    private BalanceController balanceController;

    @Mock
    private BalanceDTOService balanceDTOService;

    @Test
    public void test_getBalanceById(){
        BalanceDTO balanceDTO = new BalanceDTO();
        when(balanceDTOService.findById(1)).thenReturn(balanceDTO);

        ResponseEntity<BalanceDTO> actualBalanceDTO = balanceController.getBalanceById(1);

        assertNotNull(actualBalanceDTO);
        assertEquals(HttpStatus.OK, actualBalanceDTO.getStatusCode());
    }

    @Test
    public void test_updateBalance(){
        BalanceDTO balanceDTO = new BalanceDTO(1,300,1);
        BalanceRequestDTO balanceRequestDTO = new BalanceRequestDTO();
        balanceRequestDTO.setAmount(300);

        when(balanceDTOService.updateBalance(1,balanceRequestDTO)).thenReturn(balanceDTO);

        ResponseEntity<BalanceDTO> actual = balanceController.updateBalance(1, balanceRequestDTO);
        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }




}
