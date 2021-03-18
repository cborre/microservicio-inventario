package com.nexos.microservicio.app.inventario.repository;

import org.springframework.data.repository.CrudRepository;

import com.nexos.microservicio.app.inventario.models.entity.Mercancia;

public interface MercanciaRepository extends CrudRepository<Mercancia, Integer> {

}