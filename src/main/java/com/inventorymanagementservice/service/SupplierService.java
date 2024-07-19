package com.inventorymanagementservice.service;

import com.inventorymanagementservice.entity.Supplier;
import com.inventorymanagementservice.repository.SupplierRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SupplierService {

    private static final Logger log = LoggerFactory.getLogger(SupplierService.class);

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier coordinateWithSupplier(String productId, int requiredQuantity, String urgency) {

        log.info("Coordinate with supplier with productId {} : Quantity {} : Urgency {}"
                , productId, requiredQuantity, urgency);
        Supplier supplier = new Supplier();
        supplier.setProductId(productId);
        supplier.setRequiredQuantity(requiredQuantity);
        supplier.setUrgency(urgency);
        return supplierRepository.save(supplier);
    }
}
