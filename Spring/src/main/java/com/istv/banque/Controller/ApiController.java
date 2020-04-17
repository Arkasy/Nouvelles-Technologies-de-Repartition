package com.istv.banque.Controller;

import com.istv.banque.Model.BankAccount;
import com.istv.banque.Model.Customer;
import com.istv.banque.Model.Operation;
import com.istv.banque.Repository.BankAccountRepository;
import com.istv.banque.Repository.CustomerRepository;
import com.istv.banque.Repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping(path="/api")
public class ApiController {

    @Autowired
    private BankAccountRepository bankAccountRepo ;

    @Autowired
    private CustomerRepository customerRepo ;

    @Autowired
    private OperationRepository operationRepo ;

    @Autowired
    private PasswordEncoder passwordEncoder ;

    @GetMapping(path="/customer")
    private @ResponseBody
    Iterable<Customer> getCustomers() {
        return customerRepo.findAll();
    }

    @GetMapping(path="/customer/{id}")
    private @ResponseBody Customer getCustomerById(@PathVariable Integer id) {
        return customerRepo.findByUniqueId(id);
    }

    @GetMapping(path="/customer/{id}/account/{accountID}/operations")
    private @ResponseBody
    Collection<Operation> getOperationOfAccount(@PathVariable Integer id, @PathVariable Integer accountID) {
        Customer customer = customerRepo.findByUniqueId(id);
        BankAccount currentBankAccount = bankAccountRepo.findById(accountID);
        if(currentBankAccount!=null && customer.getBankAccount().contains(currentBankAccount))
            return currentBankAccount.getOperations();
        else
            return null ;
    }


}
