package com.inventorymanagementservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="supplier_id")
    private String supplierId;
    @Column(name="product_id")
    private String productId;
    @Column(name="required_quantity")
    private int requiredQuantity;
    private String urgency;


}
