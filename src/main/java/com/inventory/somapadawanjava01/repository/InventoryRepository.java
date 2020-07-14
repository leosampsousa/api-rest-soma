package com.inventory.somapadawanjava01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inventory.somapadawanjava01.model.Inventory;
@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
	long countByComplete(boolean complete);
	
}
