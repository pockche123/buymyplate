package org.example.controller;

import org.example.dto.VehiclePlateDTO;
import org.example.service.VehiclePlateDTOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VehiclePlateController {

    @Autowired
    private VehiclePlateDTOService vehiclePlateDTOService;

    @GetMapping("/v1/vehiclePlates")
    public ResponseEntity<Page<VehiclePlateDTO>> getAllVehiclePlates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<VehiclePlateDTO> result = vehiclePlateDTOService.findAllVehiclePlates(page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/vehiclePlates/reg/{reg}")
    public ResponseEntity<Page<VehiclePlateDTO>> getAllVehiclePlatesByReg(@PathVariable String reg,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size){
        Page<VehiclePlateDTO> result = vehiclePlateDTOService.findAllVehiclePlatesByReg(reg, page, size);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/v1/vehiclePlates/{id}")
    public ResponseEntity<VehiclePlateDTO> getVehiclePlateById(@PathVariable int id) {
        return ResponseEntity.ok(vehiclePlateDTOService.findVehiclePlateById(id));
    }


}
