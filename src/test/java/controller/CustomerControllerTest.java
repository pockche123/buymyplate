package controller;

import org.example.controller.CustomerController;
import org.example.dto.CustomerDTO;
import org.example.dto.CustomerRequestDTO;
import org.example.model.Customer;
import org.example.service.CustomerDTOService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @InjectMocks
    CustomerController customerController;

    @Mock
    CustomerDTOService customerDTOService;

    @Test
    public void test_getAllCustomers(){

        List<CustomerDTO> customerList = Arrays.asList(
                new CustomerDTO(1, "user123", "password12", "John","Doe"),
                new CustomerDTO(2,"user234", "password21", "Jane", "Doe")
        );
        Page<CustomerDTO> expectedPage = new PageImpl<>(customerList);


        when(customerDTOService.findAllCustomers(0,10)).thenReturn(expectedPage);

        ResponseEntity<Page<CustomerDTO>> actualResult = customerController.getAllCustomers(0,10);


        assertNotNull(actualResult.getBody());
        assertEquals(HttpStatus.OK, actualResult.getStatusCode());
    }

    @Test
    public void test_getCustomerById(){
        CustomerDTO customerDTO = new CustomerDTO(1, "user123", "password12", "John","Doe");
        when(customerDTOService.findCustomerById(1)).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> expectedCustomer = customerController.getCustomerById(1);

        assertNotNull(expectedCustomer.getBody());
        assertEquals(HttpStatus.OK, expectedCustomer.getStatusCode());
    }

    @Test
    public void test_addCustomer_returnsCreatedStatus(){
        CustomerRequestDTO customer = new CustomerRequestDTO(1, "user123", "password12", "John","Doe");
        CustomerDTO customerDTO = new CustomerDTO(1, "user123", "password12", "John", "Doe");
        when(customerDTOService.addCustomer(customer)).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> expected = customerController.createCustomer(customer);
        assertNotNull(expected.getBody());
        assertEquals(HttpStatus.CREATED, expected.getStatusCode());
    }

    @Test
    public void test_addCustomer_returnsConflictStatus(){
        CustomerRequestDTO customer = new CustomerRequestDTO();
        CustomerDTO customerDTO = null;
        when(customerDTOService.addCustomer(customer)).thenReturn(customerDTO);

        ResponseEntity<CustomerDTO> expected = customerController.createCustomer(customer);

        assertNull(expected.getBody());
        assertEquals(HttpStatus.CONFLICT, expected.getStatusCode());
    }



}
