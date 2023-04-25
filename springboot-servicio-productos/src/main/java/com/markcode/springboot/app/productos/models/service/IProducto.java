package com.markcode.springboot.app.productos.models.service;

import java.util.List;

import springbootservicio.app.commons.models.entity.Producto;

public interface IProducto {

	List<Producto> findAll();
	Producto findById(Long id);
	
	Producto save(Producto producto);
	
	void deleteById(Long id);
	
	
}
