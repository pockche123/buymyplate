package org.example.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDTO {

   private int customerId;
   @NotBlank
    private String username;
   @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

}
