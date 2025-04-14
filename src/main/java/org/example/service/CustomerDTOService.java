package org.example.service;

import org.example.dto.CustomerDTO;
import org.example.model.Customer;
import org.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

@Service
public class CustomerDTOService {

    @Autowired
    private CustomerRepository customerRepository;

    public static CustomerDTO convertToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getId());
        customerDTO.setUsername(customer.getUsername());
        customerDTO.setPassword(customer.getPassword());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setLastName(customer.getLastName());

        return customerDTO;
    }

    public Page<CustomerDTO> findAllCustomers(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return customerRepository.findAll(pageable).map(CustomerDTOService::convertToCustomerDTO);

    }

    public CustomerDTO findCustomerById(int id){
        Optional<Customer> optionalCustomerDTO = customerRepository.findById(id);
        return optionalCustomerDTO.map(CustomerDTOService::convertToCustomerDTO).orElse(null);
    }

    public CustomerDTO addCustomer(Customer customer){
        customerRepository.save(customer);
        return convertToCustomerDTO(customer);
    }

    public CustomerDTO replaceCustomer(int id, Customer customer){
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));

        existingCustomer.setUsername(customer.getUsername());
        existingCustomer.setFirstName(customer.getFirstName());
        existingCustomer.setLastName(customer.getLastName());
        existingCustomer.setPassword(customer.getPassword());
        return convertToCustomerDTO(existingCustomer);
    }

    public CustomerDTO updateCustomer(int id, Customer customer){
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));

        if(customer.getUsername()!=null){
            existingCustomer.setUsername(customer.getUsername());
        }
        if(customer.getFirstName() != null){
            existingCustomer.setFirstName(customer.getFirstName());
        }
        if(customer.getLastName() != null){
            existingCustomer.setLastName(customer.getLastName());
        }
        if(customer.getPassword() != null){
            existingCustomer.setPassword(customer.getPassword());
        }
        return convertToCustomerDTO(existingCustomer);

    }

    public boolean deleteCustomer(int id){
       return customerRepository.findById(id)
               .map(customer -> {
                   customerRepository.delete(customer);
                   return true;
               }).orElse(false);
    }



}
