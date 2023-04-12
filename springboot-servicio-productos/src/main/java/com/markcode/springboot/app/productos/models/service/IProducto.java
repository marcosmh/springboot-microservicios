package com.markcode.springboot.app.productos.models.service;

import java.util.List;

import com.markcode.springboot.app.productos.models.entity.Producto;

public interface IProducto {

	List<Producto> findAll();
	Producto findById(Long id);	
}
