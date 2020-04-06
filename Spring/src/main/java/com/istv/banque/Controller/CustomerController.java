package com.istv.banque.Controller;


import com.istv.banque.Model.BankAccount;
import com.istv.banque.Model.Customer;
import com.istv.banque.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class CustomerController {


    @Autowired
    private PasswordEncoder passwordEncoder ;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(path="/register")
    private String registerTemplate(HttpServletRequest request){
        if(request.getUserPrincipal()!=null){
            return "index";
        }
        else
            return "register";
    }

    @PostMapping(path="/register")
    private String createCustomer(HttpServletRequest request, Model model, @RequestParam String firstname, @RequestParam String lastname, @RequestParam String email, @RequestParam String password, @RequestParam String confirmPassword){
        if(customerRepository.findByEmail(email)!= null) {
            model.addAttribute("error", "email_already_used");
            return "register";
        }
        if(!password.equals(confirmPassword)){
            model.addAttribute("error", "password_not_equals");
            return "register";
        }
        Iterable<Customer> customerList = customerRepository.findAll();
        int uniqueId = 0 ;
        boolean uniqueIdValid = false ;
        while(uniqueId == 0 && !uniqueIdValid){
            uniqueId = 100000000 + (int)(Math.random() * (1000000000 - 100000000));
            uniqueIdValid = true ;
            for(Customer customer : customerList){
                if(customer.getUniqueId() == uniqueId)
                    uniqueIdValid = false ;
            }
        }
        Customer newCustomer = new Customer(uniqueId, firstname,lastname, email, passwordEncoder.encode(password), true, new ArrayList<>());
        newCustomer.getBankAccount().add(new BankAccount("Compte Courant", 00.00));
        customerRepository.save(newCustomer);
        model.addAttribute("uniqueId", uniqueId);
        return "customer/register/confirm";
    }
}
