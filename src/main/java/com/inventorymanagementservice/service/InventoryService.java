package com.inventorymanagementservice.service;

import ch.qos.logback.core.util.StringUtil;
import com.inventorymanagementservice.entity.Inventory;
import com.inventorymanagementservice.repository.InventoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class InventoryService {
    private static final Logger log = LoggerFactory.getLogger(InventoryService.class);

    @Autowired
    private InventoryRepository inventoryRepository;

    @Cacheable("inventory")
    public List<Inventory> getInventoryLevels(String productId, String category,
                                              String location) {
        if (!StringUtil.isNullOrEmpty(productId)) {
            log.info("Inventory find by product Id : {} ", productId);
            return inventoryRepository.findByProductId(productId);
        } else if (!StringUtil.isNullOrEmpty(category)) {
            log.info("Inventory find by category : {} ", category);
            return inventoryRepository.findByCategory(category);
        }
        log.info("inventory not found for productId {} : category {}", productId, category);
        return Collections.emptyList();
    }

    @Transactional
    @CacheEvict(value = "inventory", allEntries = true)
    public Inventory createInventory(Inventory inventory) {
        log.info("Inventory created for productId {}", inventory.getProductId());
        notifyUser(inventory);
        return inventoryRepository.save(inventory);
    }

    /* Updates the inventory quantity for a given product.*/

    public Inventory updateInventory(Inventory inventory) {
        if (inventory == null || inventory.getProductId() == null) {
            log.info("Inventory is Empty");
            throw new IllegalArgumentException("Inventory and Product ID must not be null");
        }

        List<Inventory> existingInventories = inventoryRepository.findByProductId(inventory.getProductId());
        if (existingInventories.isEmpty()) {
            throw new IllegalStateException("No inventory found for the given product ID");
        }

        Inventory existingInventory = existingInventories.get(0); // Assuming the first item is the one to update
        int updatedQuantity = existingInventory.getQuantity() + inventory.getQuantity();
        existingInventory.setQuantity(updatedQuantity);
        return inventoryRepository.save(existingInventory);
    }

    @Async("asyncTaskExecutor")
    public void notifyUser(Inventory inventory){
        log.info("notifying user inventory saved for productId {}", inventory.getProductId());
        /* logic implemented here for sending mail*/
    }
}

