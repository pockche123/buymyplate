package org.example.repository;

import org.example.model.VehiclePlate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiclePlateRepository extends JpaRepository<VehiclePlate, Integer> {
    Page<VehiclePlate> findAll(Pageable pageable);

    @Query("SELECT v from VehiclePlate v WHERE lower(v.plateNumber) LIKE lower(CONCAT('%',:plate, '%'))")
    Page<VehiclePlate> searchByPartialPlate(@Param("plate") String plate, Pageable pageable );

}
