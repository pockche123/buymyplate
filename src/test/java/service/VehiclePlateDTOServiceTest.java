package service;


import org.example.dto.VehiclePlateDTO;
import org.example.model.Customer;
import org.example.model.VehiclePlate;
import org.example.repository.VehiclePlateRepository;
import org.example.service.VehiclePlateDTOService;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VehiclePlateDTOServiceTest {
    @InjectMocks
    public VehiclePlateDTOService vehiclePlateDTOService;

    @Mock
    public VehiclePlateRepository vehiclePlateRepository;

    @Test
    public void test_convertToVehiclePlateDTO(){
        VehiclePlate vehiclePlate = new VehiclePlate(1,"sa12 uvw", true,true,50.50, null);
        VehiclePlateDTO vehiclePlateDTO = vehiclePlateDTOService.convertToVehiclePlateDTO(vehiclePlate);

        assertEquals(1, vehiclePlateDTO.getVehicleId());
        assertEquals("sa12 uvw", vehiclePlateDTO.getPlateNumber());
        assertTrue(vehiclePlateDTO.isPersonalised());
        assertTrue(vehiclePlateDTO.isAvailable());
        assertEquals(50.5, vehiclePlate.getPrice());
        assertNull(vehiclePlateDTO.getCustomerId());


    }

    @Test
    public void test_getAllVehiclePlateDTO(){
        List<VehiclePlate> vehiclePlates = Arrays.asList(
                new VehiclePlate(1,"sa12 uvw", true,true,50.50, new Customer()),
                new VehiclePlate(2, "uj65 xuy", false, true, 30.30, new Customer())

        );

        Pageable pageable = PageRequest.of(0,10);
        Page<VehiclePlate> vehiclePlatePage = new PageImpl<>(vehiclePlates, pageable,vehiclePlates.size());
        when(vehiclePlateRepository.findAll(pageable)).thenReturn(vehiclePlatePage);

        Page<VehiclePlateDTO> actualPage = vehiclePlateDTOService.findAllVehiclePlates(0,10);
        List<VehiclePlateDTO> actualList = actualPage.getContent();

        assertEquals(2, actualList.size());
        assertEquals("sa12 uvw", actualList.get(0).getPlateNumber());
        assertEquals("uj65 xuy", actualList.get(1).getPlateNumber());
    }

    @Test
    public void test_getAllVehiclesByPlate(){
        List<VehiclePlate> vehiclePlates = Arrays.asList(
                new VehiclePlate(1,"sa12 6vw", true,true,50.50, new Customer()),
                new VehiclePlate(2,"su88 4ky", true,true,50.50, new Customer())

        );

        Pageable pageable = PageRequest.of(0, 10);
        Page<VehiclePlate> vehiclePlatePage = new PageImpl<>(vehiclePlates, pageable, 2 );
        when(vehiclePlateRepository.searchByPartialPlate("s",pageable)).thenReturn(vehiclePlatePage);

        Page<VehiclePlateDTO> actualPage = vehiclePlateDTOService.findAllVehiclePlatesByReg("s",0,10);
        List<VehiclePlateDTO> actualList = actualPage.getContent();

        assertEquals(2, actualList.size());
        assertEquals("sa12 6vw", actualList.get(0).getPlateNumber());
    }

    @Test
    public void test_getVehicleDTOById(){
        VehiclePlate vehiclePlate =  new VehiclePlate(1,"sa12 6vw", true,true,50.50, new Customer());
        when(vehiclePlateRepository.findById(1)).thenReturn(Optional.of(vehiclePlate));

        VehiclePlateDTO vehiclePlateDTO = vehiclePlateDTOService.findVehiclePlateById(1);

        assertEquals(vehiclePlateDTO.getPlateNumber(), "sa12 6vw");
    }

    @Test
    public void test_getVehicleDTOById_throwsException(){
        when(vehiclePlateRepository.findById(2)).thenThrow( new RuntimeException("No vehicle plate found"));

        assertThrows(RuntimeException.class, ()-> {
            vehiclePlateDTOService.findVehiclePlateById(2);
        });

        verify(vehiclePlateRepository).findById(2);
    }

    @Test
    public void test_addVehiclePlate(){
        VehiclePlate vehiclePlate =  new VehiclePlate(1,"sa12 uvw", true,true,50.50, new Customer());
        when(vehiclePlateRepository.save(any(VehiclePlate.class))).thenReturn(vehiclePlate);

        VehiclePlateDTO actualDTO = vehiclePlateDTOService.addVehiclePlate(vehiclePlate);

        assertEquals(1, actualDTO.getVehicleId());
        assertEquals("sa12 uvw", actualDTO.getPlateNumber());
        assertTrue(vehiclePlate.getPersonalised());
        assertTrue(vehiclePlate.getAvailable());
    }

    @Test
    public void test_replaceVehiclePlates(){
        VehiclePlate vehiclePlate =  new VehiclePlate(1,"sa12 6vw", true,true,50.50, new Customer());
        VehiclePlate expectedVehiclePlate =  new VehiclePlate(1,"su88 4ky", false,false,80.00, new Customer());

        when(vehiclePlateRepository.findById(1)).thenReturn(Optional.of(vehiclePlate));

        VehiclePlateDTO actualVehiclePlateDTO = vehiclePlateDTOService.replaceVehiclePlate(1, expectedVehiclePlate);

        assertEquals("su88 4ky", actualVehiclePlateDTO.getPlateNumber());
        assertFalse(actualVehiclePlateDTO.isAvailable());
        assertFalse(actualVehiclePlateDTO.isPersonalised());
        assertEquals(80.00, actualVehiclePlateDTO.getPrice());
    }

    @Test
    public void test_replaceVehiclePlates_throwsRuntimeException(){
        when(vehiclePlateRepository.findById(2)).thenThrow(new RuntimeException("Vehicle Plate not found"));

        assertThrows(RuntimeException.class, () -> {
            vehiclePlateDTOService.replaceVehiclePlate(2, new VehiclePlate());
        });

        verify(vehiclePlateRepository).findById(2);
    }

    @Test
    public void test_updateVehiclePlates(){
        VehiclePlate vehiclePlate =  new VehiclePlate(1,"sa12 6vw", true,true,50.50, new Customer());
        VehiclePlate expectedVehiclePlate = new VehiclePlate();
        expectedVehiclePlate.setPrice(100.0);
        expectedVehiclePlate.setPersonalised(false);
        expectedVehiclePlate.setAvailable(false);
        expectedVehiclePlate.setPlateNumber("ali6");

        when(vehiclePlateRepository.findById(1)).thenReturn(Optional.of(vehiclePlate));

        VehiclePlateDTO vehiclePlateDTO = vehiclePlateDTOService.updateVehiclePlate(1,expectedVehiclePlate);

        assertEquals(100.0, vehiclePlateDTO.getPrice());
        assertFalse(vehiclePlateDTO.isPersonalised());
        assertFalse(vehiclePlateDTO.isAvailable());
        assertEquals("ali6", vehiclePlateDTO.getPlateNumber());

    }

    @Test
    public void test_updateVehiclePlates_throwsRunTimeException(){
        when(vehiclePlateRepository.findById(2)).thenThrow(new RuntimeException("Vehicle Plate not found"));

        assertThrows(RuntimeException.class, () -> {
            vehiclePlateDTOService.updateVehiclePlate(2, new VehiclePlate());
        });

        verify(vehiclePlateRepository).findById(2);
    }

    @Test
    public void test_deleteVehiclePlate_returnTrueAndFalse(){
        VehiclePlate vehiclePlate =  new VehiclePlate(1,"sa12 6vw", true,true,50.50, new Customer());

        when(vehiclePlateRepository.findById(1)).thenReturn(Optional.of(vehiclePlate));

        boolean result = vehiclePlateDTOService.deleteVehiclePlate(1);
        boolean resultFalse = vehiclePlateDTOService.deleteVehiclePlate(2);

        assertTrue(result);
        assertFalse(resultFalse);
    }

}
