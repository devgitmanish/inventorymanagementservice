package com.inventorymanagementservice.controller;

import com.inventorymanagementservice.entity.Inventory;
import com.inventorymanagementservice.entity.Supplier;
import com.inventorymanagementservice.service.InventoryService;
import com.inventorymanagementservice.service.SupplierService;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private static final Logger log = LoggerFactory.getLogger(InventoryController.class);

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/health")
    public String healthCheck(){
        return "inventory-management-service-up-and-running";
    }

    @GetMapping("/getInventoryLevels")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Inventory> getInventoryLevels(@RequestParam String productId,
                                              @RequestParam String category,
                                              @RequestParam String location) {
        return inventoryService.getInventoryLevels(productId, category, location);
    }

    @PostMapping("/createInventory")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryService.createInventory(inventory);
    }

    @PostMapping("/updateInventory")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public void updateInventory(@RequestBody Inventory inventory) {
        inventory = inventoryService.updateInventory(inventory);
        if(StringUtils.isNotEmpty(inventory.getProductId()))
            log.info("Inventory data updated successfully");
    }

    @PostMapping("/coordinateWithSupplier")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void coordinateWithSupplier(@RequestParam String productId,
                                       @RequestParam int requiredQuantity,
                                       @RequestParam String urgency) {
        Supplier supplier = supplierService.coordinateWithSupplier(productId, requiredQuantity, urgency);
        if(StringUtils.isNotEmpty(supplier.getSupplierId()))
            log.info("Inventory updated by supplier");
    }
}
