package org.example.service;

import org.example.dto.VehiclePlateDTO;
import org.example.model.VehiclePlate;
import org.example.repository.VehiclePlateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class VehiclePlateDTOService {

    @Autowired
    private VehiclePlateRepository vehiclePlateRepository;

    public static VehiclePlateDTO convertToVehiclePlateDTO(VehiclePlate vehiclePlate) {
        VehiclePlateDTO vehiclePlateDTO = new VehiclePlateDTO();
        vehiclePlateDTO.setVehicleId(vehiclePlate.getPlateId());
        vehiclePlateDTO.setPlateNumber(vehiclePlate.getPlateNumber());
        vehiclePlateDTO.setPersonalised(vehiclePlate.getPersonalised());
        vehiclePlateDTO.setAvailable(vehiclePlate.getAvailable());
        vehiclePlateDTO.setPrice(vehiclePlate.getPrice());
        if (vehiclePlate.getCustomer() != null) {
            vehiclePlateDTO.setCustomerId(vehiclePlate.getCustomer().getId());
        } else {
            vehiclePlateDTO.setCustomerId(null);
        }
        return vehiclePlateDTO;

    }

    public Page<VehiclePlateDTO> findAllVehiclePlates(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return vehiclePlateRepository.findAll(pageable).map(VehiclePlateDTOService::convertToVehiclePlateDTO);
    }

    public Page<VehiclePlateDTO> findAllVehiclePlatesByReg(String s, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return vehiclePlateRepository.searchByPartialPlate(s, pageable).map(VehiclePlateDTOService::convertToVehiclePlateDTO);
    }

    public VehiclePlateDTO findVehiclePlateById(int id) {
        return vehiclePlateRepository.findById(id).map(VehiclePlateDTOService::convertToVehiclePlateDTO).orElseThrow(() -> new RuntimeException("No vehicle plate found"));
    }

    public VehiclePlateDTO addVehiclePlate(VehiclePlate vehiclePlate) {
        vehiclePlateRepository.save(vehiclePlate);
        return convertToVehiclePlateDTO(vehiclePlate);
    }

    public VehiclePlateDTO replaceVehiclePlate(int id, VehiclePlate vehiclePlate) {
        VehiclePlate actualVehiclePlate = vehiclePlateRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle Plate not found"));

        actualVehiclePlate.setPlateNumber(vehiclePlate.getPlateNumber());
        actualVehiclePlate.setPersonalised(vehiclePlate.getPersonalised());
        actualVehiclePlate.setAvailable(vehiclePlate.getAvailable());
        actualVehiclePlate.setCustomer(vehiclePlate.getCustomer());
        actualVehiclePlate.setPrice(vehiclePlate.getPrice());
        return convertToVehiclePlateDTO(actualVehiclePlate);
    }

    public VehiclePlateDTO updateVehiclePlate(int id, VehiclePlate vehiclePlate){
        VehiclePlate actualVehiclePlate = vehiclePlateRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle Plate not found"));

        if( vehiclePlate.getPrice()  > 0){
            actualVehiclePlate.setPrice(vehiclePlate.getPrice());
        }
        if(vehiclePlate.getPersonalised() != null){
            actualVehiclePlate.setPersonalised(vehiclePlate.getPersonalised());
        }
        if(vehiclePlate.getAvailable() != null){
            actualVehiclePlate.setAvailable(vehiclePlate.getAvailable());
        }
        if(vehiclePlate.getPlateNumber() != null){
            actualVehiclePlate.setPlateNumber(vehiclePlate.getPlateNumber());
        }

        return convertToVehiclePlateDTO(actualVehiclePlate);
    }

    public boolean deleteVehiclePlate(int id){
        return vehiclePlateRepository.findById(id)
                .map(vehiclePlate -> {
                    vehiclePlateRepository.delete(vehiclePlate);
                    return true;
                }).orElse(false);
    }

}