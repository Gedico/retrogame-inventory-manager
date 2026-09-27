package com.retrogamer.inventory_manager.repository;

import com.retrogamer.inventory_manager.model.StatusCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StatusCodeRepository extends JpaRepository<StatusCode, Integer> {

    Optional<StatusCode> findByCode(String code);
}