package org.example.repository;

import org.example.model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Integer> {

    Balance findByUser_Id(Integer userId);
}
