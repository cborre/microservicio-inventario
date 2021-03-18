package com.nexos.microservicio.app.inventario.services;

import java.util.List;

import com.nexos.microservicio.app.inventario.exceptions.CargoNoEncontradoException;
import com.nexos.microservicio.app.inventario.models.entity.Usuario;

public interface IUsuarioService {

	public Usuario guardar(Usuario usuario) throws CargoNoEncontradoException;
	
	public List<Usuario> listar();

}