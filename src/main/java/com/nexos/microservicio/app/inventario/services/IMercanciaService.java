package com.nexos.microservicio.app.inventario.services;

import java.util.List;

import com.nexos.microservicio.app.inventario.exceptions.MercanciaNoEncontradaException;
import com.nexos.microservicio.app.inventario.exceptions.ProductoNoEncontradoException;
import com.nexos.microservicio.app.inventario.exceptions.SinPermisoException;
import com.nexos.microservicio.app.inventario.exceptions.UsuarioNoEncontradoException;
import com.nexos.microservicio.app.inventario.models.entity.Mercancia;

public interface IMercanciaService {

	public Mercancia guardar(Mercancia mercancia) throws ProductoNoEncontradoException, UsuarioNoEncontradoException;

	public Mercancia editar(Integer id, Mercancia mercancia) throws ProductoNoEncontradoException, UsuarioNoEncontradoException, MercanciaNoEncontradaException;

	public List<Mercancia> listar();

	public void eliminar(Integer id, Integer usuarioId) throws MercanciaNoEncontradaException, UsuarioNoEncontradoException, SinPermisoException;

}