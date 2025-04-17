package org.example.service;

import org.example.dto.CustomerDTO;
import org.example.dto.CustomerRequestDTO;
import org.example.model.Customer;
import org.example.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


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

    public CustomerDTO addCustomer(CustomerRequestDTO customerRequestDTO){
        if(customerRequestDTO.getFirstName() == null || customerRequestDTO.getLastName() == null || customerRequestDTO.getUsername() == null || customerRequestDTO.getPassword() == null){
            return null;
        }
        Customer customer = new Customer();
        customer.setId(customerRequestDTO.getCustomerId());
        customer.setFirstName(customerRequestDTO.getFirstName());
        customer.setLastName(customerRequestDTO.getLastName());
        customer.setUsername(customerRequestDTO.getUsername());
        customer.setPassword(customerRequestDTO.getPassword());
        customerRepository.save(customer);
        return convertToCustomerDTO(customer);
    }

    public CustomerDTO replaceCustomer(int id, CustomerRequestDTO customerRequestDTO){
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));

        if(customerRequestDTO.getFirstName() == null || customerRequestDTO.getLastName() == null || customerRequestDTO.getUsername() == null || customerRequestDTO.getPassword() == null){
            return null;
        }

        existingCustomer.setUsername(customerRequestDTO.getUsername());
        existingCustomer.setFirstName(customerRequestDTO.getFirstName());
        existingCustomer.setLastName(customerRequestDTO.getLastName());
        existingCustomer.setPassword(customerRequestDTO.getPassword());
        Customer savedCustomer = customerRepository.save(existingCustomer);

        return convertToCustomerDTO(savedCustomer);
    }

    public CustomerDTO updateCustomer(int id, CustomerRequestDTO customerRequestDTO){
        Customer existingCustomer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));

        if(customerRequestDTO.getUsername()!=null){
            existingCustomer.setUsername(customerRequestDTO.getUsername());
        }
        if(customerRequestDTO.getFirstName() != null){
            existingCustomer.setFirstName(customerRequestDTO.getFirstName());
        }
        if(customerRequestDTO.getLastName() != null){
            existingCustomer.setLastName(customerRequestDTO.getLastName());
        }
        if(customerRequestDTO.getPassword() != null){
            existingCustomer.setPassword(customerRequestDTO.getPassword());
        }
        Customer savedCustomer = customerRepository.save(existingCustomer);

        return convertToCustomerDTO(savedCustomer);

    }

    public boolean deleteCustomer(int id){
       return customerRepository.findById(id)
               .map(customer -> {
                   customerRepository.delete(customer);
                   return true;
               }).orElse(false);
    }



}
