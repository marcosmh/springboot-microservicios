package com.markcode.springboot.app.item.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.markcode.springboot.app.item.models.Item;
import com.markcode.springboot.app.item.models.Producto;
import com.markcode.springboot.app.item.models.service.ItemService;





@RestController
public class ItemController {
	
	private final Logger logger = LoggerFactory.getLogger(ItemController.class);

	@Autowired
	private CircuitBreakerFactory cbFactory;
	
	@Autowired
	@Qualifier("serviceFeign")
	//@Qualifier("serviceRestTemplate")
	private ItemService itemService;
	
	@GetMapping("/listar")
	public List<Item> listar(@RequestParam(name="nombre", required=false) 
		String nombre, @RequestHeader(name="token-request", required=false) String token) {
		logger.info("nombre= " +  nombre +"  token= "+ token);
		return itemService.findAll();
	}
	
	//@HystrixCommand(fallbackMethod = "metodoAlternativo")
	@GetMapping("/ver/{id}/cantidad/{cantidad}")
	public Item detalle(@PathVariable Long id, @PathVariable Integer cantidad) {
		//return itemService.findById(id, cantidad);
		
		return cbFactory.create("items")
				.run( () -> itemService.findById(id, cantidad), e-> metodoAlternativo(id, cantidad, e) );
	}
	
	
	public Item metodoAlternativo(Long id, Integer cantidad, Throwable e) {
		logger.info(e.getMessage());
		
		logger.info("Metodo Alternativo...");
		Item item = new Item();
		Producto producto = new Producto();
		item.setCantidad(cantidad);
		producto.setId(id);
		producto.setNombre("Camara Sony");
		producto.setPrecio(810.00);
		item.setProducto(producto);
		return item;
		
	}
	
	
}
