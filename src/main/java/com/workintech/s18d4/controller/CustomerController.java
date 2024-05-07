package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public List<Customer> findAll() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    public Customer findAll(@PathVariable long id) {
        return customerService.find(id);
    }

    @PutMapping("/{id}")
    public CustomerResponse update(@PathVariable long id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.save(customer);
        return new CustomerResponse(updatedCustomer.getId(), updatedCustomer.getEmail(), updatedCustomer.getSalary());
    }
    @PostMapping
    public CustomerResponse save(@RequestBody Customer customer) {
       Customer savedCustomer = customerService.save(customer);
       return new CustomerResponse(savedCustomer.getId(), savedCustomer.getEmail(), savedCustomer.getSalary());

    }




}
