package org.example.service;

import org.example.dto.UserDTO;
import org.example.dto.UserRequestDTO;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class UserDTOService {

    @Autowired
    private UserRepository userRepository;

    public static UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getId());
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        System.out.println("this is the role:"  + user.getRole());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public Page<UserDTO> findAllUsers(int page, int size){
        Pageable pageable = PageRequest.of(page,size);
        return userRepository.findAll(pageable).map(UserDTOService::convertToUserDTO);

    }

    public UserDTO findUserById(int id){
        Optional<User> optionalUserDTO = userRepository.findById(id);
        return optionalUserDTO.map(UserDTOService::convertToUserDTO).orElse(null);
    }

    public UserDTO addUser(UserRequestDTO userRequestDTO){
        if(userRequestDTO.getFirstName() == null || userRequestDTO.getLastName() == null || userRequestDTO.getUsername() == null || userRequestDTO.getPassword() == null){
            return null;
        }
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(userRequestDTO.getPassword());
        user.setRole(userRequestDTO.getRole());

//        user.setRole(UserRole.CUSTOMER);
        userRepository.save(user);
        return convertToUserDTO(user);
    }


    public UserDTO updateUser(int id, UserRequestDTO userRequestDTO){
        User existingUser = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if(userRequestDTO.getUsername()!=null){
            existingUser.setUsername(userRequestDTO.getUsername());
        }
        if(userRequestDTO.getFirstName() != null){
            existingUser.setFirstName(userRequestDTO.getFirstName());
        }
        if(userRequestDTO.getLastName() != null){
            existingUser.setLastName(userRequestDTO.getLastName());
        }
        if(userRequestDTO.getPassword() != null){
            existingUser.setPassword(userRequestDTO.getPassword());
        }

        User savedUser = userRepository.save(existingUser);
        System.out.println("id: "  + savedUser);

        return convertToUserDTO(savedUser);

    }

    public boolean deleteUser(int id){
       return userRepository.findById(id)
               .map(user -> {
                   userRepository.delete(user);
                   return true;
               }).orElse(false);
    }



}
