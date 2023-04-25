package com.markcode.springboot.app.item.models.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.markcode.springboot.app.item.models.Item;
import com.markcode.springboot.app.item.models.Producto;

@Service("serviceRestTemplate")
public class ItemServiceImpl implements ItemService {
	
	private final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
	
	@Autowired
	private RestTemplate clienteRest;

	@Override
	public List<Item> findAll() {
		logger.info("ItemServiceFeign.findAll");
		List<Producto> productos = Arrays.asList(clienteRest.getForObject("http://servicio-productos/listar", Producto[].class));
		
		return productos.stream()
				.map( p -> new Item(p, 1))
				.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		logger.info("ItemServiceFeign.findById");
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		Producto producto = clienteRest.getForObject("http://servicio-productos/ver/{id}", Producto.class,pathVariables);
		return new Item(producto, cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		logger.info("ItemServiceFeign.save");
		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
		HttpEntity<Producto> response = clienteRest.exchange("http://servicio-productos/crear", 
				HttpMethod.POST, body, Producto.class);
		Producto productoResponse = response.getBody();
		return productoResponse;
	}

	@Override
	public Producto update(Producto producto, Long id) {
		logger.info("ItemServiceFeign.update");
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		HttpEntity<Producto> body = new HttpEntity<Producto>(producto);
		HttpEntity<Producto> response = clienteRest.exchange("http://servicio-productos/editar/{id}", 
				HttpMethod.PUT, body, Producto.class,pathVariables);
		return response.getBody();
	}

	@Override
	public void delete(Long id) {
		logger.info("ItemServiceFeign.delete");
		Map<String, String> pathVariables = new HashMap<String, String>();
		pathVariables.put("id", id.toString());
		
		clienteRest.delete("http://servicio-productos/eliminar/{id}", 
				HttpMethod.DELETE,pathVariables);
		
		
		
	}

}
