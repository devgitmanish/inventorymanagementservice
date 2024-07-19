package com.inventorymanagementservice.service;

import com.inventorymanagementservice.entity.Inventory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    private static final Logger log = LoggerFactory.getLogger(KafkaMessagePublisher.class);

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendInventoryEvent(Inventory inventory) {

        log.info("Message sending from producer....");
        try {
            CompletableFuture<SendResult<String, Object>> future
                    = kafkaTemplate.send("inventory-management-topic", inventory);

            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    log.info("Sent Message =[" + inventory.toString() + "] " +
                            "with offset=[" + result.getRecordMetadata().offset() + " ]");
                } else {
                    log.info("Unable to Send Message =[" + inventory.toString() + "] " +
                            "due to " + ex.getMessage());
                }
            });
        } catch (Exception ex) {
            log.info("Error " + ex.getMessage());
        }
    }
}
