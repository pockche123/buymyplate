package controller;

import org.example.controller.UserController;
import org.example.dto.UserDTO;
import org.example.dto.UserRequestDTO;
import org.example.model.UserRole;
import org.example.service.UserDTOService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserDTOService userDTOService;

    @Test
    public void test_getAllUsers(){

        List<UserDTO> userList = Arrays.asList(
                new UserDTO(1, "user123", "password12", "John","Doe", UserRole.CUSTOMER),
                new UserDTO(2,"user234", "password21", "Jane", "Doe", UserRole.CUSTOMER)
        );
        Page<UserDTO> expectedPage = new PageImpl<>(userList);


        when(userDTOService.findAllUsers(0,10)).thenReturn(expectedPage);

        ResponseEntity<Page<UserDTO>> actualResult = userController.getAllUsers(0,10);


        assertNotNull(actualResult.getBody());
        assertEquals(HttpStatus.OK, actualResult.getStatusCode());
    }

    @Test
    public void test_getUserById(){
        UserDTO userDTO = new UserDTO(1, "user123", "password12", "John","Doe", UserRole.CUSTOMER);
        when(userDTOService.findUserById(1)).thenReturn(userDTO);

        ResponseEntity<UserDTO> expectedUser = userController.getUserById(1);

        assertNotNull(expectedUser.getBody());
        assertEquals(HttpStatus.OK, expectedUser.getStatusCode());
    }

    @Test
    public void test_addUser_returnsCreatedStatus(){
        UserRequestDTO user = new UserRequestDTO(1, "user123", "password12", "John","Doe", UserRole.CUSTOMER);
        UserDTO userDTO = new UserDTO(1, "user123", "password12", "John", "Doe", UserRole.CUSTOMER);
        when(userDTOService.addUser(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> expected = userController.createUser(user);
        assertNotNull(expected.getBody());
        assertEquals(HttpStatus.CREATED, expected.getStatusCode());
    }

    @Test
    public void test_addUser_returnsConflictStatus(){
        UserRequestDTO user = new UserRequestDTO();
        UserDTO userDTO = null;
        when(userDTOService.addUser(user)).thenReturn(userDTO);

        ResponseEntity<UserDTO> expected = userController.createUser(user);

        assertNull(expected.getBody());
        assertEquals(HttpStatus.CONFLICT, expected.getStatusCode());
    }




    @Test
    public void test_updateUser_returnsCreatedStatus() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();


        UserDTO userDTO = new UserDTO(1, "user123", "password12", "John", "Doe", UserRole.CUSTOMER);
        when(userDTOService.updateUser(1, userRequestDTO)).thenReturn(userDTO);

        ResponseEntity<UserDTO> actual = userController.updateUser(1, userRequestDTO);

        assertNotNull(actual.getBody());
        assertEquals(HttpStatus.OK, actual.getStatusCode());

    }

    @Test
    public void test_deleteUser_returnsNoContentStatus() {

        when(userDTOService.deleteUser(1)).thenReturn(true);

        ResponseEntity<UserDTO>actual = userController.deleteUser(1);
        assertTrue(actual.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void test_deleteUser_returnsConflictStatus() {
        when(userDTOService.deleteUser(1)).thenReturn(false);
        ResponseEntity<UserDTO>actual = userController.deleteUser(1);
        assertFalse(actual.getStatusCode().is2xxSuccessful());
    }
}
