package com.nexos.microservicio.app.inventario.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nexos.microservicio.app.inventario.exceptions.CargoNoEncontradoException;
import com.nexos.microservicio.app.inventario.models.entity.Cargo;
import com.nexos.microservicio.app.inventario.models.entity.Usuario;
import com.nexos.microservicio.app.inventario.repository.CargoRepository;
import com.nexos.microservicio.app.inventario.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements IUsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	
	@Autowired
	CargoRepository cargoRepository;

	@Override
	@Transactional
	public Usuario guardar(Usuario usuario) throws CargoNoEncontradoException {
		
		Optional<Cargo> cargo = cargoRepository.findById(usuario.getCargo().getId());
		
		if(!cargo.isPresent()) {
			throw new CargoNoEncontradoException("Cargo no encontrado");
		}
		
		usuario.setCargo(cargo.get());
		
		// Return
		return usuarioRepository.save(usuario);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> listar() {
		
		// Return
		return (List<Usuario>) usuarioRepository.findAll();
	}
	
}