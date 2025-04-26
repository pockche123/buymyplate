package org.example.controller;

import org.example.dto.UserDTO;
import org.example.dto.UserRequestDTO;
import org.example.service.UserDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    public UserDTOService userDTOService;


    @GetMapping("/v1/users")
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0")int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<UserDTO> result = userDTOService.findAllUsers(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/users/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(userDTOService.findUserById(id));
    }

    @PostMapping("/v1/users")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserRequestDTO user) {
        UserDTO userDTO = userDTOService.addUser(user);
        if (userDTO == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);

    }


    @PatchMapping("/v1/users/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable(name="id") int id, @RequestBody UserRequestDTO user) {
        UserDTO userDTO = userDTOService.updateUser(id, user);
        return ResponseEntity.status(HttpStatus.OK).body(userDTO);
    }

    @DeleteMapping("/v1/users/{id}")
    public ResponseEntity<UserDTO> deleteUser(@PathVariable(name="id") int id) {
        boolean result = userDTOService.deleteUser(id);
        if(!result){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
