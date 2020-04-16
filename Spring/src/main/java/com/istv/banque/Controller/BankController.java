package com.istv.banque.Controller;

import com.istv.banque.Model.BankAccount;
import com.istv.banque.Model.Customer;
import com.istv.banque.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BankController {

    @Autowired
    private CustomerRepository customerRepository ;

    @RequestMapping(path="/")
    public String getIndex(HttpServletRequest request, Model model){
        if(request.getUserPrincipal()==null)
            return "/login";
        Customer currentCustomer = customerRepository.findByUniqueId(Integer.parseInt(request.getUserPrincipal().getName()));
        model.addAttribute("customer", currentCustomer);
    return "index";
    }

    @RequestMapping(path="/operations")
    public String showOperations(HttpServletRequest request, Model model, @RequestParam int accountId){
        if(request.getUserPrincipal()==null)
            return "/login";
        Customer currentCustomer = customerRepository.findByUniqueId(Integer.parseInt(request.getUserPrincipal().getName()));
        model.addAttribute("customer", currentCustomer);
        boolean isOwner = false ;
        BankAccount currentBankAccount = null ;
        for(BankAccount ba : currentCustomer.getBankAccount()){
            if(ba.getId()==accountId) {
                isOwner = true;
                currentBankAccount = ba ;
            }
        }
        if(!isOwner)
            return "redirect:/";
        model.addAttribute("account", currentBankAccount);
        return "account_operation";
    }
}
