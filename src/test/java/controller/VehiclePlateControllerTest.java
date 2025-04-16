package controller;

import org.example.controller.VehiclePlateController;
import org.example.dto.VehiclePlateDTO;
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



}
