package service;

import org.example.dto.UserDTO;
import org.example.dto.UserRequestDTO;
import org.example.model.User;
import org.example.model.UserRole;
import org.example.repository.UserRepository;
import org.example.service.UserDTOService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;



import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class UserDTOServiceTest {

    @InjectMocks
    private UserDTOService userDTOService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void test_convertToUserDTO(){
        User user = new User(1, "user123", "password12", "John","Doe",  UserRole.CUSTOMER);

        UserDTO actualDTO  = UserDTOService.convertToUserDTO(user);

        assertEquals(1, actualDTO.getUserId());
        assertEquals("user123", actualDTO.getUsername());
        assertEquals("password12", actualDTO.getPassword());
        assertEquals("John", actualDTO.getFirstName());
        assertEquals("Doe", actualDTO.getLastName());
    }

    @Test
    public void test_getAllUserDTO(){
        List<User> userList = Arrays.asList(
                new User(1, "user123", "password12", "John","Doe", UserRole.CUSTOMER),
                new User(2,"user234", "password21", "Jane", "Doe", UserRole.ADMIN)
        );
        Pageable pageable = PageRequest.of(0,10);
        Page<User> userPage = new PageImpl<>(userList, pageable, userList.size());

        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<UserDTO> actualPage = userDTOService.findAllUsers(0, 10);
        List<UserDTO> actualList = actualPage.getContent();

        assertEquals(2, actualList.size());
        assertEquals(1, actualList.get(0).getUserId());
        assertEquals(2, actualList.get(1).getUserId());

    }

    @Test
    public void test_getUserDTOById(){
        User user = new User(1, "user123", "password12", "John","Doe", UserRole.CUSTOMER);
        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        UserDTO userDTO = userDTOService.findUserById(1);

        assertEquals("John", userDTO.getFirstName());
        assertEquals("Doe", userDTO.getLastName());
    }

    @Test
    public void test_addUser(){
        UserRequestDTO userRequestDTO = new UserRequestDTO(1, "user123", "password12", "John","Doe", UserRole.CUSTOMER);

        User user = new User(1, "user123", "password12", "John","Doe", UserRole.CUSTOMER);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO actualDTO = userDTOService.addUser(userRequestDTO);

//        assertEquals(1, actualDTO.getUserId());
        assertEquals("user123", actualDTO.getUsername());
    }




    @Test
    public void test_updateUser_userFound(){
        User user = new User(1, "user123", "password12", "John","Doe", UserRole.CUSTOMER);

        UserRequestDTO expectUser = new UserRequestDTO();
        expectUser.setFirstName("Jane");
        expectUser.setLastName("Dane");
        expectUser.setUsername("user234");
        expectUser.setPassword("password21");

        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO actualUser = userDTOService.updateUser(1, expectUser);

        assertEquals("Jane", actualUser.getFirstName());
        assertEquals("Dane", actualUser.getLastName());
        assertEquals("user234", actualUser.getUsername());
        assertEquals("password21", actualUser.getPassword());

    }
    
    @Test
    public void test_updateUser_throwRuntimeException(){
        UserRequestDTO expectedUser = new UserRequestDTO();
        when(userRepository.findById(1)).thenThrow(new RuntimeException("User not found"));

        assertThrows(RuntimeException.class, () -> {
            userDTOService.updateUser(1,expectedUser);
        });

        verify(userRepository).findById(1);
    }

    @Test
    public void test_deleteUser_returnTrueAndFalse(){
        User user = new User(1, "user123", "password12", "John","Doe", UserRole.CUSTOMER);

        when(userRepository.findById(1)).thenReturn(Optional.of(user));

        boolean result = userDTOService.deleteUser(1);
        boolean resultFalse = userDTOService.deleteUser(2);

        assertTrue(result);
        assertFalse(resultFalse);
    }
}