package org.example.service;

import org.example.dto.VehiclePlateDTO;
import org.example.dto.VehiclePlateRequestDTO;
import org.example.model.User;
import org.example.model.VehiclePlate;
import org.example.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    public static VehiclePlateDTO convertToVehiclePlateDTO(VehiclePlate vehiclePlate) {
        VehiclePlateDTO vehiclePlateDTO = new VehiclePlateDTO();
        vehiclePlateDTO.setVehicleId(vehiclePlate.getPlateId());
        vehiclePlateDTO.setPlateNumber(vehiclePlate.getPlateNumber());
        vehiclePlateDTO.setPersonalised(vehiclePlate.getPersonalised());
        vehiclePlateDTO.setAvailable(vehiclePlate.getAvailable());
        vehiclePlateDTO.setPrice(vehiclePlate.getPrice());
        if (vehiclePlate.getUser() != null) {
            vehiclePlateDTO.setUserId(vehiclePlate.getUser().getId());
        } else {
            vehiclePlateDTO.setUserId(null);
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

    public VehiclePlateDTO addVehiclePlate(VehiclePlateRequestDTO vehiclePlateRequestDTO) {
        if( vehiclePlateRequestDTO.getPlateNumber() == null|| vehiclePlateRequestDTO.getPersonalised() == null || vehiclePlateRequestDTO.getAvailable() == null || vehiclePlateRequestDTO.getPrice() == null ) {
            return null;
        }
//        User user = userRepository.findById(vehiclePlateRequestDTO.getUserId()).orElse(null);
        User user = null;
        if (vehiclePlateRequestDTO.getUserId() != null) {
            user = userRepository.findById(vehiclePlateRequestDTO.getUserId()).orElse(null);
        }

        VehiclePlate vehiclePlate = new VehiclePlate();
        vehiclePlate.setPlateNumber(vehiclePlateRequestDTO.getPlateNumber());
        vehiclePlate.setPersonalised(vehiclePlateRequestDTO.getPersonalised());
        vehiclePlate.setAvailable(vehiclePlateRequestDTO.getAvailable());
        vehiclePlate.setPrice(vehiclePlateRequestDTO.getPrice());
        vehiclePlate.setUser(user);
        vehiclePlateRepository.save(vehiclePlate);
        return convertToVehiclePlateDTO(vehiclePlate);
    }

    public VehiclePlateDTO replaceVehiclePlate(int id, VehiclePlateRequestDTO vehiclePlateRequestDTO) {
        VehiclePlate actualVehiclePlate = vehiclePlateRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle Plate not found"));

        if(vehiclePlateRequestDTO.getPlateNumber() == null || vehiclePlateRequestDTO.getPersonalised() == null || vehiclePlateRequestDTO.getAvailable() == null || vehiclePlateRequestDTO.getPrice() == null ) {
            return null;
        }

        User user = null;
        if (vehiclePlateRequestDTO.getUserId() != null) {
            user = userRepository.findById(vehiclePlateRequestDTO.getUserId()).orElse(null);
        }

        actualVehiclePlate.setPlateNumber(vehiclePlateRequestDTO.getPlateNumber());
        actualVehiclePlate.setPersonalised(vehiclePlateRequestDTO.getPersonalised());
        actualVehiclePlate.setAvailable(vehiclePlateRequestDTO.getAvailable());
        actualVehiclePlate.setUser(user);
        actualVehiclePlate.setPrice(vehiclePlateRequestDTO.getPrice());

        vehiclePlateRepository.save(actualVehiclePlate);
        return convertToVehiclePlateDTO(actualVehiclePlate);
    }

    public VehiclePlateDTO updateVehiclePlate(int id, VehiclePlateRequestDTO vehiclePlateRequestDTO){
        VehiclePlate actualVehiclePlate = vehiclePlateRepository.findById(id).orElseThrow(() -> new RuntimeException("Vehicle Plate not found"));


        if(vehiclePlateRequestDTO.getUserId() != null) {
            User user = userRepository.findById(vehiclePlateRequestDTO.getUserId()).orElse(null);
            actualVehiclePlate.setUser(user);
        }

        if( vehiclePlateRequestDTO.getPrice() != null){
            actualVehiclePlate.setPrice(vehiclePlateRequestDTO.getPrice());
        }
        if(vehiclePlateRequestDTO.getPersonalised() != null){
            actualVehiclePlate.setPersonalised(vehiclePlateRequestDTO.getPersonalised());
        }
        if(vehiclePlateRequestDTO.getAvailable() != null){
            actualVehiclePlate.setAvailable(vehiclePlateRequestDTO.getAvailable());
        }
        if(vehiclePlateRequestDTO.getPlateNumber() != null){
            actualVehiclePlate.setPlateNumber(vehiclePlateRequestDTO.getPlateNumber());
        }

        vehiclePlateRepository.save(actualVehiclePlate);

        return convertToVehiclePlateDTO(actualVehiclePlate);
    }

    public boolean deleteVehiclePlate(int id){
        return vehiclePlateRepository.findById(id)
                .map(vehiclePlate -> {
                    vehiclePlateRepository.delete(vehiclePlate);
                    return true;
                }).orElse(false);
    }

    public Page<VehiclePlateDTO> findVehiclePlatesByUserId(int userId, Pageable pageable) {
        return vehiclePlateRepository.findVehiclePlatesByUserId(userId, pageable).map(VehiclePlateDTOService::convertToVehiclePlateDTO);
    }

}