package service;

import org.example.dto.CustomerDTO;
import org.example.model.Customer;
import org.example.repository.CustomerRepository;
import org.example.service.CustomerDTOService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CustomerDTOServiceTest {

    @InjectMocks
    private CustomerDTOService customerDTOService;

    @Mock
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp(){}

    @AfterEach
    public void tearDown(){
        customerDTOService = null;
    }

    @Test
    public void test_convertToCustomerDTO(){
        Customer customer = new Customer(1, "user123", "password12", "John","Doe");

        CustomerDTO actualDTO  = customerDTOService.convertToCustomerDTO(customer);

        assertEquals(1, actualDTO.getCustomerId());
        assertEquals("user123", actualDTO.getUsername());
        assertEquals("password12", actualDTO.getPassword());
        assertEquals("John", actualDTO.getFirstName());
        assertEquals("Doe", actualDTO.getLastName());


    }


}