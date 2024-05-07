package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.AccountResponse;
import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final CustomerService customerService;

    @GetMapping
    public List<Account> findAll() {
        return accountService.findAll();
    }

    @GetMapping("/{id}")
    public Account findAll(@PathVariable long id) {
        return accountService.find(id);
    }

    @PostMapping("/{customerId}")
    public AccountResponse save(@PathVariable long customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        if(customer != null) {
            customer.getAccounts().add(account);
            account.setCustomer(customer);
            accountService.save(account);
        }else{
            throw new RuntimeException("Customer not found");
        }
        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(), new CustomerResponse(customer.getId(), customer.getEmail(), customer.getSalary()));
    }

    @PutMapping("/{customerId}")
    public AccountResponse update(@PathVariable long customerId, @RequestBody Account account) {
        Customer customer = customerService.find(customerId);
        Account accountToUpdate = accountService.find(account.getId());
        for(Account account1 : customer.getAccounts()) {
            if(Objects.equals(account.getId(), account1.getId())) {
                accountToUpdate = account1;
            }
            if(accountToUpdate == null) {
                throw new RuntimeException("Account not found");
            }
        }
        int index = customer.getAccounts().indexOf(accountToUpdate);
        customer.getAccounts().set(index, account);
        account.setCustomer(customer);
        accountService.save(account);

        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(), new CustomerResponse(customer.getId(), customer.getEmail(), customer.getSalary()));
    }

    @DeleteMapping("/{id}")
    public AccountResponse delete(@PathVariable long id) {
        Account account = accountService.find(id);
        Customer customer = account.getCustomer();
        if(account==null) {
            throw new RuntimeException("Account not found");
        }
        accountService.delete(id);
        return new AccountResponse(account.getId(), account.getAccountName(), account.getMoneyAmount(), new CustomerResponse(customer.getId(), customer.getEmail(), customer.getSalary()));
    }
}
