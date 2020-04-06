package com.istv.banque.Model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="operations")
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @ManyToOne
    private BankAccount account ;

    private String name ;

    private double value ;

    @Column(name="created_at")
    private Date createdAt ;
}
