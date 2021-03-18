package com.nexos.microservicio.app.inventario.services;

import java.util.List;

import com.nexos.microservicio.app.inventario.models.entity.Cargo;

public interface ICargoService {

	public Cargo guardar(Cargo cargo);
	
	public List<Cargo> listar();
	
}