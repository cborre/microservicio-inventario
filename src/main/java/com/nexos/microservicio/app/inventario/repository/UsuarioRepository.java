package com.nexos.microservicio.app.inventario.repository;

import org.springframework.data.repository.CrudRepository;

import com.nexos.microservicio.app.inventario.models.entity.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, Integer> {

}