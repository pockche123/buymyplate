package org.example.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.example.model.UserRole;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerDTO {

   private int customerId;
   @NotBlank
   @Size(min = 3, max = 50, message = "Username must be 3–50 characters")
   private String username;
   @NotBlank
   @Size(min = 8, message = "Password must be at least 8 characters")
   private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    private UserRole role;

}
