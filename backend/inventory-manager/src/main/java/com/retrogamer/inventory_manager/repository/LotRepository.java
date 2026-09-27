package com.retrogamer.inventory_manager.repository;

import com.retrogamer.inventory_manager.model.Lot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Long> {

    List<Lot> findByUserId(Long userId);

    List<Lot> findByUserIdOrderByCreatedAtDesc(Long userId);
}