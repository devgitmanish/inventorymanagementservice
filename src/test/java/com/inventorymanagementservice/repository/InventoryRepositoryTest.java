package com.inventorymanagementservice.repository;

import com.inventorymanagementservice.entity.Inventory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class InventoryRepositoryTest {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @BeforeEach
    void setup(){
        Inventory inventory = Inventory.builder()
                .productId("A4323223")
                .category("electronics")
                .location("werehouse1-gurugram").build();
        testEntityManager.persist(inventory);
    }
    @Test
    public void findByProductIdTest(){
        List<Inventory> listInventory = inventoryRepository.findByProductId("A4323223");
        assertEquals("A4323223", listInventory.get(0).getProductId());
    }

    @Test
    public void findByCategoryTest(){
        List<Inventory> listInventory = inventoryRepository.findByCategory("electronics");
        assertEquals("electronics", listInventory.get(0).getCategory());
    }
}
