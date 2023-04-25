package com.markcode.springboot.app.item.models.service;

import java.util.List;

import com.markcode.springboot.app.item.models.Item;
import springbootservicio.app.commons.models.entity.Producto;

public interface ItemService {
	
	List<Item> findAll();
	Item findById(Long id, Integer cantidad);
	
	Producto save(Producto producto);
	Producto update(Producto producto,Long id);
	void delete(Long id);
	

}
