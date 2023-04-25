package com.markcode.springboot.app.item.models.service;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.markcode.springboot.app.item.clientes.ProductoClienteRest;
import com.markcode.springboot.app.item.models.Item;
import com.markcode.springboot.app.item.models.Producto;

@Service("serviceFeign")
@Primary
public class ItemServiceFeign implements ItemService {
	
	private final Logger logger = LoggerFactory.getLogger(ItemServiceFeign.class);
	
	@Autowired
	private ProductoClienteRest clienteFeign;

	@Override
	public List<Item> findAll() {
		logger.info("ItemServiceFeign.listar");
		return clienteFeign.listar()
				.stream().map(p -> new Item(p,1))
				.collect(Collectors.toList());
	}

	@Override
	public Item findById(Long id, Integer cantidad) {
		logger.info("ItemServiceFeign.findById");
		return new Item(clienteFeign.detalle(id), cantidad);
	}

	@Override
	public Producto save(Producto producto) {
		logger.info("ItemServiceFeign.save");
		return clienteFeign.save(producto);
	}

	@Override
	public Producto update(Producto producto, Long id) {
		logger.info("ItemServiceFeign.update");
		return clienteFeign.update(producto, id);
	}

	@Override
	public void delete(Long id) {
		logger.info("ItemServiceFeign.delete");
		clienteFeign.delete(id);
		
	}

}
