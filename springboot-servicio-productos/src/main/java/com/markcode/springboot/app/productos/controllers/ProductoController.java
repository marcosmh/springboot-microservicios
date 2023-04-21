package com.markcode.springboot.app.productos.controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.markcode.springboot.app.productos.models.entity.Producto;
import com.markcode.springboot.app.productos.models.service.IProducto;



@RestController
public class ProductoController {
	
	private final Logger logger = LoggerFactory.getLogger(ProductoController.class);
	
	@Autowired
	Environment env;
	
	@Value("${server.port}")
	private Integer port;

	@Autowired
	private IProducto productoService;
	
	
	@GetMapping("/listar")
	public List<Producto> listar() {
		//return productoService.findAll();
		return productoService.findAll()
				.stream()
				.map(producto -> {
					//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
					producto.setPort(port);
					logger.info("port: " + port);
					return producto;
				}).collect(Collectors.toList());
	}
	
	@GetMapping("/ver/{id}")
	public Producto detalle(@PathVariable Long id) throws InterruptedException {
		
		if(id.equals(10L)) {
			throw new IllegalStateException("Producto no encontrado");
		}
		
		if(id.equals(7L)) {
			TimeUnit.SECONDS.sleep(6L);
		}
		
		
		Producto producto = productoService.findById(id);
		//producto.setPort(Integer.parseInt(env.getProperty("local.server.port")));
		producto.setPort(port);
		logger.info("port: " + port);
		
		return producto;
	}
	
	
	
	
	
}
