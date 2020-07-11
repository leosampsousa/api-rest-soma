package com.inventory.somapadawanjava01.resources;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.somapadawanjava01.model.Inventory;
import com.inventory.somapadawanjava01.repository.InventoryRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value="/api")
@Api(value="API soma")
@CrossOrigin(origins="*")
public class InventoryResource {
	@Autowired
	InventoryRepository inventoryRepository;
	
	@GetMapping("/inventory")
	

	@ApiOperation(value="Retorna a lista de itens do inventório")
	public List<Inventory> listaInventory(){
		return inventoryRepository.findAll();
	}
	
	@GetMapping("/inventory/{id}")
	@ApiOperation(value="Retorna um item específico do inventório")
	public ResponseEntity<Inventory> listaInventoryUnique(@PathVariable(value="id") long id){
		Optional<Inventory> item = inventoryRepository.findById(id);
		if(item.isPresent())
			return new ResponseEntity<Inventory>(item.get(), HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/inventory")
	@ApiOperation(value="Salva um item no inventório")
	public Inventory saveInventory(@RequestBody Inventory item) {
		return inventoryRepository.save(item);
	}
	
	@DeleteMapping("/inventory/{id}")
	@ApiOperation(value="Deleta um item específico do inventório")
	public ResponseEntity<Object> deleteItem(@PathVariable(value = "id") long id){
		Optional<Inventory> item = inventoryRepository.findById(id);
		if(item.isPresent()) {
			inventoryRepository.delete(item.get());
			return new ResponseEntity<>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/inventory/{id}")
	@ApiOperation(value="Atualiza um item específico do inventório")
	public ResponseEntity<Inventory> putItem(@PathVariable(value = "id") long id, @RequestBody Inventory newItem){
		Optional<Inventory> oldItem = inventoryRepository.findById(id);
		if(oldItem.isPresent()) {
			Inventory item = oldItem.get();
			item.setName(newItem.getName());
			item.setCategory(newItem.getCategory());
			item.setValue(newItem.getValue());
			item.setComplete(newItem.isComplete());
			inventoryRepository.save(item);
			
			return new ResponseEntity<Inventory>(item, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/howmany")
	@ApiOperation(value="Retorna quantos registros existem de acordo com o parametro complete."
			+ " Esse parametro pode assumir 3 valores: All, True e False. Caso não seja passado,"
			+ "o seu valor default é all.")
	public ResponseEntity<Long> howMany(@RequestParam(name="complete", required = false, defaultValue = "all") String complete) {
		if(complete.equals("all")) {
			return new ResponseEntity<Long>(inventoryRepository.count(), HttpStatus.OK);
		}else if(complete.equals("false")) {
			return new ResponseEntity<Long>(inventoryRepository.countByComplete(Boolean.parseBoolean(complete)), HttpStatus.OK);
		}else if(complete.equals("true")){
			return new ResponseEntity<Long>(inventoryRepository.countByComplete(Boolean.parseBoolean(complete)), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
}
