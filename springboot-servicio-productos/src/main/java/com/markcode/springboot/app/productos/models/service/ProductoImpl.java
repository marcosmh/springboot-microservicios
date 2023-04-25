package com.markcode.springboot.app.productos.models.service;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.markcode.springboot.app.productos.models.dao.ProductoDAO;
import springbootservicio.app.commons.models.entity.Producto;

@Service
public class ProductoImpl implements IProducto {
	
	@Autowired
	ProductoDAO productoDAO;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		return productoDAO.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Producto save(Producto producto) {
		return productoDAO.save(producto);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		productoDAO.deleteById(id);
		
	}

}
