package com.nexos.microservicio.app.inventario.services;

import java.util.List;

import com.nexos.microservicio.app.inventario.models.entity.Producto;

public interface IProductoService {

	public Producto guardar(Producto producto);
	
	public List<Producto> listar();
}