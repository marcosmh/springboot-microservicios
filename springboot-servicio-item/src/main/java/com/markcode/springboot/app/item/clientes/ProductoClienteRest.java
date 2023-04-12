package com.markcode.springboot.app.item.clientes;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.markcode.springboot.app.item.models.Producto;

@FeignClient(name="servicio-productos")
public interface ProductoClienteRest {

	@GetMapping("/listar")
	List<Producto> listar();
	
	@GetMapping("/ver/{id}")
	Producto detalle(@PathVariable Long id);
	
	
	
	
	
}
