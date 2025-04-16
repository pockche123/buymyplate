package org.example.controller;

import org.example.dto.CustomerDTO;
import org.example.dto.CustomerRequestDTO;
import org.example.model.Customer;
import org.example.service.CustomerDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerController {

    @Autowired
    public CustomerDTOService customerDTOService;


    @GetMapping("/v1/customers")
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(
            @RequestParam(defaultValue = "0")int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<CustomerDTO> result = customerDTOService.findAllCustomers(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/customers/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable int id) {
        return ResponseEntity.ok(customerDTOService.findCustomerById(id));
    }

    @PostMapping("/v1/customers")
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerRequestDTO customer) {
        CustomerDTO customerDTO = customerDTOService.addCustomer(customer);
        if (customerDTO == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(customerDTO);

    }

    @PutMapping("/v1/customers/{id}")
    public ResponseEntity<CustomerDTO> replaceCustomer(@PathVariable int id, @RequestBody CustomerRequestDTO customer) {
        CustomerDTO customerDTO = customerDTOService.replaceCustomer(id, customer);
        if (customerDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(customerDTO);
    }

    @PatchMapping("/v1/customers/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable int id, @RequestBody CustomerRequestDTO customer) {
        CustomerDTO customerDTO = customerDTOService.updateCustomer(id, customer);
        return ResponseEntity.ok(customerDTO);
    }

    @DeleteMapping("/v1/customers/{id}")
    public ResponseEntity<CustomerDTO> deleteCustomer(@PathVariable int id) {
        boolean result = customerDTOService.deleteCustomer(id);
        if(!result){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
