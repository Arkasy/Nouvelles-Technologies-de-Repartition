package com.istv.banque.Config;

import com.istv.banque.Model.Customer;
import com.istv.banque.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Autowired
    public UserService(CustomerRepository _customerRepository) {
        this.customerRepository = _customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByUniqueId(Integer.parseInt(username));
        if(customer == null){
            System.out.println("user not found");

            throw new UsernameNotFoundException(username);
        }
        System.out.println("user found");
        return new PrincipalUser(customer);
    }
}
