package com.inventorymanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InventorymanagementserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventorymanagementserviceApplication.class, args);
	}

}
