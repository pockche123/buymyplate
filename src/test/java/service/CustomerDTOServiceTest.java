package service;

import org.example.dto.CustomerDTO;
import org.example.dto.CustomerRequestDTO;
import org.example.model.Customer;
import org.example.repository.CustomerRepository;
import org.example.service.CustomerDTOService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;



import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CustomerDTOServiceTest {

    @InjectMocks
    private CustomerDTOService customerDTOService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    public void test_convertToCustomerDTO(){
        Customer customer = new Customer(1, "user123", "password12", "John","Doe");

        CustomerDTO actualDTO  = CustomerDTOService.convertToCustomerDTO(customer);

        assertEquals(1, actualDTO.getCustomerId());
        assertEquals("user123", actualDTO.getUsername());
        assertEquals("password12", actualDTO.getPassword());
        assertEquals("John", actualDTO.getFirstName());
        assertEquals("Doe", actualDTO.getLastName());
    }

    @Test
    public void test_getAllCustomerDTO(){
        List<Customer> customerList = Arrays.asList(
                new Customer(1, "user123", "password12", "John","Doe"),
                new Customer(2,"user234", "password21", "Jane", "Doe")
        );
        Pageable pageable = PageRequest.of(0,10);
        Page<Customer> customerPage = new PageImpl<>(customerList, pageable, customerList.size());

        when(customerRepository.findAll(pageable)).thenReturn(customerPage);

        Page<CustomerDTO> actualPage = customerDTOService.findAllCustomers(0, 10);
        List<CustomerDTO> actualList = actualPage.getContent();

        assertEquals(2, actualList.size());
        assertEquals(1, actualList.get(0).getCustomerId());
        assertEquals(2, actualList.get(1).getCustomerId());

    }

    @Test
    public void test_getCustomerDTOById(){
        Customer customer = new Customer(1, "user123", "password12", "John","Doe");
        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        CustomerDTO customerDTO = customerDTOService.findCustomerById(1);

        assertEquals("John", customerDTO.getFirstName());
        assertEquals("Doe", customerDTO.getLastName());
    }

    @Test
    public void test_addCustomer(){
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO(1, "user123", "password12", "John","Doe");

        Customer customer = new Customer(1, "user123", "password12", "John","Doe");
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        CustomerDTO actualDTO = customerDTOService.addCustomer(customerRequestDTO);

        assertEquals(1, actualDTO.getCustomerId());
    }

    @Test
    public void test_replaceCustomer(){
        Customer customer = new Customer(1, "user123", "password12", "John","Doe");
        CustomerRequestDTO expectCustomer =  new CustomerRequestDTO(1,"user234", "password21", "Jane", "Dane");


        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        CustomerDTO actualCustomer = customerDTOService.replaceCustomer(1, expectCustomer);

        assertEquals("Jane", actualCustomer.getFirstName());
        assertEquals("Dane", actualCustomer.getLastName());
        assertEquals("user234", actualCustomer.getUsername());
        assertEquals("password21", actualCustomer.getPassword());
    }

    @Test
    public void test_replaceCustomer_throwRuntimeException(){
        CustomerRequestDTO expectedCustomer = new CustomerRequestDTO();
        when(customerRepository.findById(1)).thenThrow(new RuntimeException("Customer not found"));

        assertThrows(RuntimeException.class, () -> {
            customerDTOService.replaceCustomer(1,expectedCustomer);
        });

        verify(customerRepository).findById(1);
    }


    @Test
    public void test_updateCustomer_userFound(){
        Customer customer = new Customer(1, "user123", "password12", "John","Doe");

        CustomerRequestDTO expectCustomer = new CustomerRequestDTO();
        expectCustomer.setFirstName("Jane");
        expectCustomer.setLastName("Dane");
        expectCustomer.setUsername("user234");
        expectCustomer.setPassword("password21");

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        CustomerDTO actualCustomer = customerDTOService.updateCustomer(1, expectCustomer);

        assertEquals("Jane", actualCustomer.getFirstName());
        assertEquals("Dane", actualCustomer.getLastName());
        assertEquals("user234", actualCustomer.getUsername());
        assertEquals("password21", actualCustomer.getPassword());

    }
    
    @Test
    public void test_updateCustomer_throwRuntimeException(){
        CustomerRequestDTO expectedCustomer = new CustomerRequestDTO();
        when(customerRepository.findById(1)).thenThrow(new RuntimeException("Customer not found"));

        assertThrows(RuntimeException.class, () -> {
            customerDTOService.updateCustomer(1,expectedCustomer);
        });

        verify(customerRepository).findById(1);
    }

    @Test
    public void test_deleteCustomer_returnTrueAndFalse(){
        Customer customer = new Customer(1, "user123", "password12", "John","Doe");

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));

        boolean result = customerDTOService.deleteCustomer(1);
        boolean resultFalse = customerDTOService.deleteCustomer(2);

        assertTrue(result);
        assertFalse(resultFalse);
    }
}