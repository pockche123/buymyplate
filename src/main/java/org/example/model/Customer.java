package org.example.model;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.ToString;




@Entity
@NoArgsConstructor
@ToString
public class Customer extends User {

    public Customer(int id, String username, String password, String firstName, String lastName) {
        super(id, username, password, firstName, lastName, UserRole.CUSTOMER) ;
    }


}
