package com.inventorymanagementservice.repository;

import com.inventorymanagementservice.entity.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SupplierRepositoryTest {

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setup(){
        Supplier supplier = Supplier.builder()
                .supplierId("E4321")
                .productId("A4323223")
                .requiredQuantity(50)
                .urgency("high").build();
        testEntityManager.persist(supplier);
    }
    @Test
    public void findByProductIdTest(){
        List<Supplier> suppliers = supplierRepository.findByProductId("A4323223");
        assertEquals("A4323223", suppliers.get(0).getProductId());
    }
}
