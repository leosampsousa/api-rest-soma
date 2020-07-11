package com.inventory.somapadawanjava01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.somapadawanjava01.model.Inventory;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	long countByComplete(boolean complete);
	
}
