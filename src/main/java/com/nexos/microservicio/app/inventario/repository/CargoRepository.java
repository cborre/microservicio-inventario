package com.nexos.microservicio.app.inventario.repository;

import org.springframework.data.repository.CrudRepository;

import com.nexos.microservicio.app.inventario.models.entity.Cargo;

public interface CargoRepository extends CrudRepository<Cargo, Integer> {

}