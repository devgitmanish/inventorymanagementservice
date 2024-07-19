package com.inventorymanagementservice.controller;

import com.inventorymanagementservice.config.UserInformationLoadViaDB;
import com.inventorymanagementservice.entity.Inventory;
import com.inventorymanagementservice.entity.Supplier;
import com.inventorymanagementservice.service.InventoryService;
import com.inventorymanagementservice.service.JwtService;
import com.inventorymanagementservice.service.SupplierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @MockBean
    private SupplierService supplierService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserInformationLoadViaDB userInformationLoadViaDB;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        Inventory singleInventory = Inventory.builder()
                .productId("A4323223")
                .category("electronics")
                .location("werehouse1-gurugram")
                .build();

        Mockito.when(inventoryService.getInventoryLevels(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(Collections.singletonList(singleInventory));

        Mockito.when(inventoryService.createInventory(Mockito.any(Inventory.class)))
                .thenReturn(singleInventory);

        Mockito.when(inventoryService.updateInventory(Mockito.any(Inventory.class)))
                .thenReturn(singleInventory);

        Supplier singleSupplier = Supplier.builder()
                .supplierId("S123")
                .build();

        Mockito.when(supplierService.coordinateWithSupplier(Mockito.anyString(), Mockito.anyInt(), Mockito.anyString()))
                .thenReturn(singleSupplier);
    }

    @Test
    public void healthCheckTest() throws Exception {
        mockMvc.perform(get("/inventory/health")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("inventory-management-service-up-and-running"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void getInventoryLevelsTest() throws Exception {
        mockMvc.perform(get("/inventory/getInventoryLevels")
                        .param("productId", "A4323223")
                        .param("category", "electronics")
                        .param("location", "werehouse1-gurugram")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[{'productId':'A4323223','category':'electronics','location':'werehouse1-gurugram'}]"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void createInventoryTest() throws Exception {
        String newInventoryJson = "{\"productId\":\"A4323223\",\"category\":\"electronics\",\"location\":\"werehouse1-gurugram\"}";

        mockMvc.perform(post("/inventory/createInventory")
                        .content(newInventoryJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{'productId':'A4323223','category':'electronics','location':'werehouse1-gurugram'}"));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void updateInventoryTest() throws Exception {
        String updateInventoryJson = "{\"productId\":\"A4323223\",\"category\":\"electronics\",\"location\":\"werehouse1-gurugram\"}";

        mockMvc.perform(post("/inventory/updateInventory")
                        .content(updateInventoryJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void coordinateWithSupplierTest() throws Exception {
        mockMvc.perform(post("/inventory/coordinateWithSupplier")
                        .param("productId", "A4323223")
                        .param("requiredQuantity", "10")
                        .param("urgency", "high")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}

