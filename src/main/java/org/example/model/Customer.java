package org.example.model;

import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
public class Customer extends User {

    public Customer(int id, String username, String password, String firstName, String lastName) {
        super(id, username, password, firstName, lastName);
    }


}
