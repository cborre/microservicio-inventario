package com.nexos.microservicio.app.inventario.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexos.microservicio.app.inventario.models.entity.Cargo;
import com.nexos.microservicio.app.inventario.repository.CargoRepository;

@Service
public class CargoServiceImpl implements ICargoService {

	@Autowired
	CargoRepository cargoRepository;
	
	@Override
	@Transactional
	public Cargo guardar(Cargo cargo) {
		
		// Return
		return cargoRepository.save(cargo);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Cargo> listar() {
		
		// Return
		return (List<Cargo>) cargoRepository.findAll();
	}
	
}