package com.nexos.microservicio.app.inventario.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexos.microservicio.app.inventario.exceptions.MercanciaNoEncontradaException;
import com.nexos.microservicio.app.inventario.exceptions.ProductoNoEncontradoException;
import com.nexos.microservicio.app.inventario.exceptions.SinPermisoException;
import com.nexos.microservicio.app.inventario.exceptions.UsuarioNoEncontradoException;
import com.nexos.microservicio.app.inventario.models.entity.Mercancia;
import com.nexos.microservicio.app.inventario.models.entity.Producto;
import com.nexos.microservicio.app.inventario.models.entity.Usuario;
import com.nexos.microservicio.app.inventario.repository.MercanciaRepository;
import com.nexos.microservicio.app.inventario.repository.ProductoRepository;
import com.nexos.microservicio.app.inventario.repository.UsuarioRepository;

@Service
public class MercanciaServiceImpl implements IMercanciaService {

	@Autowired
	MercanciaRepository mercanciaRepository;
	
	@Autowired
	ProductoRepository productoRepository;
	
	@Autowired
	UsuarioRepository usuarioRepository;

	@Override
	@Transactional
	public Mercancia guardar(Mercancia mercancia) throws ProductoNoEncontradoException, UsuarioNoEncontradoException {

		Optional<Producto> producto = productoRepository.findById(mercancia.getProducto().getId());

		// Validando que el producto exista
		if (!producto.isPresent()) {
			throw new ProductoNoEncontradoException("Producto no encontrado");
		}
		
		Optional<Usuario> usuario = usuarioRepository.findById(mercancia.getUsuarioRegistro().getId());

		// Validando que el usuario exista
		if (!usuario.isPresent()) {
			throw new UsuarioNoEncontradoException("Usuario no encontrado");
		}
		
		mercancia.setProducto(producto.get());
		mercancia.setUsuarioRegistro(usuario.get());
		
		// Return
		return mercanciaRepository.save(mercancia);
		
	}

	@Override
	@Transactional
	public Mercancia editar(Integer id, Mercancia mercancia) throws ProductoNoEncontradoException, UsuarioNoEncontradoException, MercanciaNoEncontradaException {
		
		Optional<Mercancia> mercanciaOptional = mercanciaRepository.findById(id);

		// Validando que la mercancia exista
		if (!mercanciaOptional.isPresent()) {
			throw new MercanciaNoEncontradaException("Mercancia no encontrada");
		}
		
		Optional<Producto> producto = productoRepository.findById(mercancia.getProducto().getId());

		// Validando que el producto exista
		if (!producto.isPresent()) {
			throw new ProductoNoEncontradoException("Producto no encontrado");
		}
		
		Optional<Usuario> usuarioModificador = usuarioRepository.findById(mercancia.getUsuarioModifica().getId());

		// Validando que el usuario exista
		if (!usuarioModificador.isPresent()) {
			throw new UsuarioNoEncontradoException("Usuario que modifica no encontrado");
		}
		
		Mercancia mercanciaDb = mercanciaOptional.get();
		
		mercanciaDb.setNombre(mercancia.getNombre());
		mercanciaDb.setCantidad(mercancia.getCantidad());
		mercanciaDb.setProducto(producto.get());
		mercanciaDb.setUsuarioModifica(usuarioModificador.get());
		mercanciaDb.setFechaModifica(new Date());

		// Return
		return mercanciaRepository.save(mercanciaDb);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Mercancia> listar() {

		// Return
		return (List<Mercancia>) mercanciaRepository.findAll();
	}

	@Override
	@Transactional
	public void eliminar(Integer id, Integer usuarioId) throws MercanciaNoEncontradaException, UsuarioNoEncontradoException, SinPermisoException {
		
		Optional<Mercancia> mercanciaOptional = mercanciaRepository.findById(id);

		// Validando que la mercancia exista
		if (!mercanciaOptional.isPresent()) {
			throw new MercanciaNoEncontradaException("Mercancia no encontrada");
		}
		
		Optional<Usuario> usuarioElimina = usuarioRepository.findById(usuarioId);

		// Validando que el usuario exista
		if (!usuarioElimina.isPresent()) {
			throw new UsuarioNoEncontradoException("Usuario que elimina no se encontro");
		}
		
		// Validando que el usuario a eliminar sea quien hizo el registro
		if(!mercanciaOptional.get().getUsuarioRegistro().equals(usuarioElimina.get())) {
			throw new SinPermisoException("El usuario no tiene permiso para eliminar mercancia");
		}			

		// Eliminar mercancia
		mercanciaRepository.deleteById(id);
	}

}