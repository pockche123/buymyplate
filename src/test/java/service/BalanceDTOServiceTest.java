package service;

import org.example.dto.BalanceDTO;
import org.example.model.Balance;
import org.example.model.Customer;
import org.example.repository.BalanceRepository;
import org.example.service.BalanceDTOService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BalanceDTOServiceTest {
    @InjectMocks
    BalanceDTOService balanceDTOService;

    @Mock
    BalanceRepository balanceRepository;

    @Test
    public void  test_findBalanceRepositoryById(){
        Customer customer = new Customer();
        Balance balance = new Balance(1, 200, customer);
        BalanceDTO balanceDTO = new BalanceDTO(1, 200, 1);
        when(balanceRepository.findById(1)).thenReturn(Optional.of(balance));

        BalanceDTO actual = balanceDTOService.findById(1);

        assertNotNull(actual);
        assertEquals(200, balanceDTO.getAmount());
    }


}
