package org.example.controller;

import org.example.dto.VehiclePlateDTO;
import org.example.dto.VehiclePlateRequestDTO;
import org.example.service.VehiclePlateDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class VehiclePlateController {

    @Autowired
    private VehiclePlateDTOService vehiclePlateDTOService;

    @GetMapping("/v1/vehiclePlates")
    public ResponseEntity<Page<VehiclePlateDTO>> getAllVehiclePlates(
            @RequestParam(name = "page", defaultValue = "0")int page,
            @RequestParam(name = "size", defaultValue = "10") int size)
    {
        Page<VehiclePlateDTO> result = vehiclePlateDTOService.findAllVehiclePlates(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/vehiclePlates/reg/{reg}")
    public ResponseEntity<Page<VehiclePlateDTO>> getAllVehiclePlatesByReg(@PathVariable(name="reg") String reg,
                                                                          @RequestParam(name = "page", defaultValue = "0")int page,
                                                                          @RequestParam(name = "size", defaultValue = "10") int size)
    {
        Page<VehiclePlateDTO> result = vehiclePlateDTOService.findAllVehiclePlatesByReg(reg, page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/vehiclePlates/{id}")
    public ResponseEntity<VehiclePlateDTO> getVehiclePlateById(@PathVariable(name="id") int id) {
        return ResponseEntity.ok(vehiclePlateDTOService.findVehiclePlateById(id));
    }

    @PostMapping("/v1/vehiclePlates")
    public ResponseEntity<VehiclePlateDTO> createVehiclePlate(@RequestBody VehiclePlateRequestDTO vehiclePlateRequestDTO) {
        VehiclePlateDTO vehiclePlateDTO = vehiclePlateDTOService.addVehiclePlate(vehiclePlateRequestDTO);
        if(vehiclePlateDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiclePlateDTO);
    }

    @PutMapping("/v1/vehiclePlates/{id}")
    public ResponseEntity<VehiclePlateDTO> replaceVehiclePlate(@PathVariable(name="id") int id, @RequestBody VehiclePlateRequestDTO vehiclePlateRequestDTO) {
        VehiclePlateDTO vehiclePlateDTO = vehiclePlateDTOService.replaceVehiclePlate(id, vehiclePlateRequestDTO);
        if(vehiclePlateDTO == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(vehiclePlateDTO);
    }

    @PatchMapping("/v1/vehiclePlates/{id}")
    public ResponseEntity<VehiclePlateDTO> updateVehiclePlate(@PathVariable(name="id") int id, @RequestBody VehiclePlateRequestDTO vehiclePlateRequestDTO) {
        VehiclePlateDTO vehiclePlateDTO = vehiclePlateDTOService.updateVehiclePlate(id, vehiclePlateRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(vehiclePlateDTO);
    }

    @DeleteMapping("/v1/vehiclePlates/{id}")
    public ResponseEntity<VehiclePlateDTO> deleteVehiclePlate(@PathVariable(name="id") int id) {
        boolean result = vehiclePlateDTOService.deleteVehiclePlate(id);
        if(!result){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}
