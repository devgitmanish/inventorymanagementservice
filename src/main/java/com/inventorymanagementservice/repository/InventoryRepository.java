package com.inventorymanagementservice.repository;

import com.inventorymanagementservice.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, String> {

    List<Inventory> findByProductId(String productId);
    List<Inventory> findByCategory(String category);


}
