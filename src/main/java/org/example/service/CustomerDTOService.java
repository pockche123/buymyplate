package org.example.service;

import org.example.dto.CustomerDTO;
import org.example.model.Customer;
import org.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerDTOService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerDTO convertToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getId());
        customerDTO.setUsername(customer.getUsername());
        customerDTO.setPassword(customer.getPassword());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());


        return customerDTO;
    }
}
