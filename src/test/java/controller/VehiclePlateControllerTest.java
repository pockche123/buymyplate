package controller;

import org.example.controller.VehiclePlateController;
import org.example.dto.VehiclePlateDTO;
import org.example.dto.VehiclePlateRequestDTO;
import org.example.service.VehiclePlateDTOService;
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

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehiclePlateControllerTest {
    @InjectMocks
    private VehiclePlateController vehiclePlateController;
    @Mock
    private VehiclePlateDTOService vehiclePlateDTOService;

    @Test
    public void test_getAllVehiclePlates() {
        List<VehiclePlateDTO> vehiclePlateDTOList = Arrays.asList(
                new VehiclePlateDTO(1, "sa12uvw", false, false, 20.30, 1),
        new VehiclePlateDTO(2, "uk56xrw", false, false, 60.30, 1)
        );
        Page<VehiclePlateDTO> page = new PageImpl<>(vehiclePlateDTOList);
        when(vehiclePlateDTOService.findAllVehiclePlates(0,10)).thenReturn(page);

        ResponseEntity<Page<VehiclePlateDTO>> actual = vehiclePlateController.getAllVehiclePlates(0, 10);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }

    @Test
    public void test_getAllVehiclePlatesByReg(){
        List<VehiclePlateDTO> vehiclePlateDTOList = Arrays.asList(
                new VehiclePlateDTO(1, "sa12uvw", false, false, 20.30, 1),
                new VehiclePlateDTO(2, "sa56xrw", false, false, 60.30, 1)
        );
        Page<VehiclePlateDTO> page = new PageImpl<>(vehiclePlateDTOList);
        when(vehiclePlateDTOService.findAllVehiclePlatesByReg("s",0,10)).thenReturn(page);

        ResponseEntity<Page<VehiclePlateDTO>> actual = vehiclePlateController.getAllVehiclePlatesByReg("s",0,10);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(page, actual.getBody());
    }

    @Test
    public void test_getVehiclePlateById(){
        VehiclePlateDTO vehiclePlateDTO =   new VehiclePlateDTO(1, "sa12uvw", false, false, 20.30, 1);
        when(vehiclePlateDTOService.findVehiclePlateById(1)).thenReturn(vehiclePlateDTO);

        ResponseEntity<VehiclePlateDTO> actual = vehiclePlateController.getVehiclePlateById(1);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(vehiclePlateDTO, actual.getBody());
    }

    @Test
    public void test_addVehiclePlate(){
        VehiclePlateDTO vehiclePlateDTO =   new VehiclePlateDTO(1, "sa12uvw", false, false, 20.30, 1);
        VehiclePlateRequestDTO vehiclePlateRequestDTO =   new VehiclePlateRequestDTO(1, "sa12uvw", false, false, 20.30, 1);

        when(vehiclePlateDTOService.addVehiclePlate(vehiclePlateRequestDTO)).thenReturn(vehiclePlateDTO);

        ResponseEntity<VehiclePlateDTO> actual = vehiclePlateController.createVehiclePlate(vehiclePlateRequestDTO);
        assertNotNull(actual);
        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        assertEquals(vehiclePlateDTO, actual.getBody());
    }

    @Test
    public void test_addVehiclePlate_ReturnsBadRequest(){
        VehiclePlateRequestDTO vehiclePlateRequestDTO = new VehiclePlateRequestDTO();
        when(vehiclePlateDTOService.addVehiclePlate(vehiclePlateRequestDTO)).thenReturn(null);

        ResponseEntity<VehiclePlateDTO> actual = vehiclePlateController.createVehiclePlate(vehiclePlateRequestDTO);

        assertNotNull(actual);
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void test_replaceVehiclePlate(){
        VehiclePlateDTO vehiclePlateDTO =   new VehiclePlateDTO(1, "sa12uvw", false, false, 20.30, 1);
        VehiclePlateRequestDTO vehiclePlateRequestDTO =   new VehiclePlateRequestDTO(1, "sa12uvw", false, false, 20.30, 1);

        when(vehiclePlateDTOService.replaceVehiclePlate(1, vehiclePlateRequestDTO)).thenReturn(vehiclePlateDTO);

        ResponseEntity<VehiclePlateDTO> actual = vehiclePlateController.replaceVehiclePlate(1, vehiclePlateRequestDTO);
        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(vehiclePlateDTO, actual.getBody());
    }

    @Test
    public void test_replaceVehiclePlate_ReturnsBadRequest(){
        VehiclePlateRequestDTO vehiclePlateRequestDTO =   new VehiclePlateRequestDTO();
        when(vehiclePlateDTOService.replaceVehiclePlate(1, vehiclePlateRequestDTO)).thenReturn(null);

        ResponseEntity<VehiclePlateDTO>actual = vehiclePlateController.replaceVehiclePlate(1, vehiclePlateRequestDTO);

        assertNotNull(actual);
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }


    @Test
    public void test_updateVehiclePlate(){
        VehiclePlateDTO vehiclePlateDTO =   new VehiclePlateDTO(1, "sa12uvw", false, false, 20.30, 1);

        VehiclePlateRequestDTO vehiclePlateRequestDTO = new VehiclePlateRequestDTO();
        vehiclePlateRequestDTO.setPlateNumber("sa12 uvw");
        when(vehiclePlateDTOService.updateVehiclePlate(1, vehiclePlateRequestDTO)).thenReturn(vehiclePlateDTO);

        ResponseEntity<VehiclePlateDTO> actual = vehiclePlateController.updateVehiclePlate(1, vehiclePlateRequestDTO);

        assertNotNull(actual);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }


    @Test
    public void test_deleteVehiclePlate_returnsTrueAndFalse(){

        when(vehiclePlateDTOService.deleteVehiclePlate(1)).thenReturn(true);
        when(vehiclePlateDTOService.deleteVehiclePlate(2)).thenReturn(false);

        ResponseEntity<VehiclePlateDTO> result =  vehiclePlateController.deleteVehiclePlate(1);
        ResponseEntity<VehiclePlateDTO> resultFalse = vehiclePlateController.deleteVehiclePlate(2);

        assertNotNull(result);
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertEquals(HttpStatus.NOT_FOUND, resultFalse.getStatusCode());
    }
}