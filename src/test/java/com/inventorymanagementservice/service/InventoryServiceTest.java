package com.inventorymanagementservice.service;

import com.inventorymanagementservice.entity.Inventory;
import com.inventorymanagementservice.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InventoryServiceTest {

    @Autowired
    private InventoryService inventoryService;
    @MockBean
    private InventoryRepository inventoryRepository;

    @BeforeEach
    void setup(){
        List<Inventory> optionalInventory = Collections.singletonList(Inventory.builder()
                .productId("A4323223")
                .category("electronics")
                .location("werehouse1-gurugram").build());

        Inventory singleInventory = Inventory.builder()
                .productId("A4323223")
                .category("electronics")
                .location("werehouse1-gurugram").build();

        Mockito.when(inventoryRepository.findByProductId("A4323223")).thenReturn(optionalInventory);
        Mockito.when(inventoryRepository.findByCategory("electronics")).thenReturn(optionalInventory);
        Mockito.when(inventoryRepository.save(singleInventory)).thenReturn(singleInventory);
    }
    @Test
    public void getInventoryLevelsTest(){
        String proudctId = "A4323223";
        String category = "electronics";
        String location = "wearehouse1-gurugram";
        List<Inventory> inventoryLevels = inventoryService.getInventoryLevels
                (proudctId, category, location);

        assertEquals(proudctId, inventoryLevels.get(0).getProductId());
    }

    @Test
    public void createInventoryTest(){
        Inventory singleInventory = Inventory.builder()
                .productId("A4323223")
                .category("electronics")
                .location("werehouse1-gurugram").build();
        Inventory inventory = inventoryService.createInventory(singleInventory);
        assertEquals("A4323223", inventory.getProductId());
    }

    @Test
    public void updateInventoryTest(){
        Inventory singleInventory = Inventory.builder()
                .productId("A4323223")
                .category("electronics")
                .location("werehouse1-gurugram").build();
        Inventory inventory = inventoryService.updateInventory(singleInventory);
        assertEquals("A4323223", inventory.getProductId());
    }

    @Test
    public void UpdateInventoryThrowsIllegalArgumentExceptionTest() {

        Inventory nullProductInventory = Inventory.builder()
                .productId(null)
                .category("electronics")
                .location("werehouse1-gurugram")
                .quantity(10)
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            inventoryService.updateInventory(nullProductInventory);
        });
        assertEquals("Inventory and Product ID must not be null", exception.getMessage());
    }

    @Test
    public void updateInventoryThrowsIllegalStateExceptionTest() {
        Mockito.when(inventoryRepository.findByProductId("NonExistingProduct")).thenReturn(Collections.emptyList());
        Inventory nonExistingInventory = Inventory.builder()
                .productId("NonExistingProduct")
                .category("electronics")
                .location("werehouse1-gurugram")
                .quantity(10)
                .build();

        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            inventoryService.updateInventory(nonExistingInventory);
        });
        assertEquals("No inventory found for the given product ID", exception.getMessage());
    }
}
