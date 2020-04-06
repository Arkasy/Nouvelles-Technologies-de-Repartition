package com.istv.banque.Repository;

import com.istv.banque.Model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    Customer findByUniqueId(int uniqueId);

    Customer findByEmail(String email);
}
