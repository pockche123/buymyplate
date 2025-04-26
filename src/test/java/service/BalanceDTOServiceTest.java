package service;

import org.example.dto.BalanceDTO;
import org.example.dto.BalanceRequestDTO;
import org.example.model.Balance;
import org.example.model.User;
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
    public void  test_findBalanceById(){
        User user = new User();
        Balance balance = new Balance(1, 200, user);
        BalanceDTO balanceDTO = new BalanceDTO(1, 200, 1);
        when(balanceRepository.findById(1)).thenReturn(Optional.of(balance));

        BalanceDTO actual = balanceDTOService.findById(1);

        assertNotNull(actual);
        assertEquals(200, balanceDTO.getAmount());
    }

    @Test
    public void test_updateBalance(){
        Balance balance= new Balance(1, 200, new User());
        BalanceRequestDTO balanceRequestDTO = new BalanceRequestDTO();
        balanceRequestDTO.setAmount(300);
        when(balanceRepository.findById(1)).thenReturn(Optional.of(balance));

        BalanceDTO balanceDTO = balanceDTOService.updateBalance(1, balanceRequestDTO);

        assertNotNull(balanceDTO);
        assertEquals(300, balanceDTO.getAmount());
    }


}
