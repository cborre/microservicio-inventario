package com.nexos.microservicio.app.inventario.repository;

import org.springframework.data.repository.CrudRepository;

import com.nexos.microservicio.app.inventario.models.entity.Producto;

public interface ProductoRepository extends CrudRepository<Producto, Integer> {

}