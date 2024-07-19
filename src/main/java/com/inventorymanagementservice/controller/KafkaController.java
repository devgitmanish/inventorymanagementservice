package com.inventorymanagementservice.controller;

import com.inventorymanagementservice.entity.Inventory;
import com.inventorymanagementservice.service.KafkaMessagePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaMessagePublisher kafkaMessagePublisher;

    @PostMapping("/publish/event")
    public ResponseEntity<?> sendEventAsObject(@RequestBody Inventory inventory){
        try{
            kafkaMessagePublisher.sendInventoryEvent(inventory);
            return ResponseEntity.ok("message event published successfully...");
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
