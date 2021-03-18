package com.nexos.microservicio.app.inventario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexos.microservicio.app.inventario.models.entity.Producto;
import com.nexos.microservicio.app.inventario.repository.ProductoRepository;

@Service
public class ProductoServiceImpl implements IProductoService {

	@Autowired
	ProductoRepository productoRepository;

	@Override
	@Transactional
	public Producto guardar(Producto producto) {

		// Return
		return productoRepository.save(producto);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> listar() {

		// Return
		return (List<Producto>) productoRepository.findAll();
	}

}