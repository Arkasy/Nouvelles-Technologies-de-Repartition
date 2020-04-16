package com.istv.banque;

import com.istv.banque.Model.BankAccount;
import com.istv.banque.Model.Customer;
import com.istv.banque.Model.Operation;
import com.istv.banque.Repository.BankAccountRepository;
import com.istv.banque.Repository.CustomerRepository;
import com.istv.banque.Repository.OperationRepository;
import com.istv.banque.operations_webservice.AddOperationRequest;
import com.istv.banque.operations_webservice.AddOperationResponse;
import com.istv.banque.operations_webservice.BankOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class OperationEndPoint {

    private static final String NAMESPACE_URI = "http://istv.com/banque/operations_webservice" ;

    private OperationRepository operationRepo ;

    private BankAccountRepository bankAccountRepo ;

    @Autowired
    public OperationEndPoint(OperationRepository _operationRepo, BankAccountRepository _bankAccountRepo){
        this.operationRepo = _operationRepo;
        this.bankAccountRepo = _bankAccountRepo;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddOperationRequest")
    @ResponsePayload
    public AddOperationResponse AddOperationRequest(@RequestPayload AddOperationRequest request){
        AddOperationResponse response = new AddOperationResponse();
        System.out.println(request.toString());
        BankAccount account = bankAccountRepo.findById(request.getIdAccount());
        if(account==null)
            return null ;
        Operation newOperation = new Operation(request.getName(), request.getAmount());
        Operation savedOperation = operationRepo.save(newOperation);
        account.getOperations().add(newOperation);
        account.setBalance(account.getBalance() + newOperation.getValue());
        BankAccount result = bankAccountRepo.save(account);
        if(result!= null) {
            BankOperation bkOperation = new BankOperation();
            bkOperation.setAccountId(account.getId());
            bkOperation.setId(savedOperation.getId());
            bkOperation.setValue(savedOperation.getValue());
            bkOperation.setName(savedOperation.getName());
            response.setOperation(bkOperation);
            return response ;
        }
        else
            return new AddOperationResponse();
    }
}
