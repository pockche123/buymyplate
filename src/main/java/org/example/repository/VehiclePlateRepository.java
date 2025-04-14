package org.example.repository;

import org.example.model.VehiclePlate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiclePlateRepository extends JpaRepository<VehiclePlate, Integer> {

}
