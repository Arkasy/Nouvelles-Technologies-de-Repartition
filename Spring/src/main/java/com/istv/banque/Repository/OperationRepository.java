package com.istv.banque.Repository;

import com.istv.banque.Model.Operation;
import org.springframework.data.repository.CrudRepository;

public interface OperationRepository extends CrudRepository<Operation, Long> {
}
