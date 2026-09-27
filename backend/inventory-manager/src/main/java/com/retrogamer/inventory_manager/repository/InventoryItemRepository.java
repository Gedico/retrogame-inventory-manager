package com.retrogamer.inventory_manager.repository;

import com.retrogamer.inventory_manager.model.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Long> {

    List<InventoryItem> findByUserId(Long userId);

    // Usa Status_Code per navigare fino alla proprietà 'code' dell'entità StatusCode
    List<InventoryItem> findByUserIdAndStatus_Code(Long userId, String statusCode);

    List<InventoryItem> findByLotId(Long lotId);
}