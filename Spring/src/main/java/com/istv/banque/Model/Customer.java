package com.istv.banque.Model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @Column(name="unique_id")
    private int uniqueId ;

    private String firstname ;

    private String lastname ;

    private String email;

    private String password ;

    @Column(name="is_enabled")
    private boolean enabled ;

    @OneToMany(cascade=CascadeType.ALL)
    private Collection<BankAccount> bankAccount ;

    public Customer() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<BankAccount> getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(Collection<BankAccount> bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Customer( int uniqueId, String firstname, String lastname, String email, String password, boolean enabled, Collection<BankAccount> bankAccount) {
        this.uniqueId = uniqueId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.bankAccount = bankAccount;
    }
}
