package com.nexos.microservicio.app.inventario.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexos.microservicio.app.inventario.dto.UsuarioDto;
import com.nexos.microservicio.app.inventario.exceptions.CargoNoEncontradoException;
import com.nexos.microservicio.app.inventario.models.entity.Usuario;
import com.nexos.microservicio.app.inventario.services.IUsuarioService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/inventario")
@Api(value = "ConfigSetup", tags = "Operations pertaining to usuario")
public class UsuarioController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	IUsuarioService iUsuarioService;

	@ApiOperation(value = "Lista todos los usuarios")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Muestra la lista de usuarios"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("/usuarios")
	public ResponseEntity<?> listar() {

		// Consulto los usuarios
		List<Usuario> usuarios = iUsuarioService.listar();

		// Se convierte en una lista de usuarios DTO 
		List<UsuarioDto> usuarioResponse = usuarios.stream()
		          .map(this::convertToDto)
		          .collect(Collectors.toList());
		
		// Return
		return ResponseEntity.status(HttpStatus.OK).body(usuarioResponse);
	}

	@ApiOperation(value = "Se encarga de guardar un usuario")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Guardo un usuario correctamente"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@PostMapping("/usuarios")
	public ResponseEntity<?> guardar(@RequestBody @Valid UsuarioDto usuarioDto, BindingResult result) {
		
		// Variables
		Map<String, Object> response = new HashMap<>();

		// Se verifica que no hay errores en la peticion
		if (result.hasErrors()) {

			// Variables
			Map<String, Object> errores = new HashMap<>();

			// ForEach error
			result.getFieldErrors().forEach(err -> {
				errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
			});

			// Return
			return ResponseEntity.badRequest().body(errores);
		}

		// Se convierte de Dto a Entity
		Usuario usuarioRequest = convertToEntity(usuarioDto);

		// Se guarda el usuario
		Usuario usuario;
		
		try {
			
			// Guardar
			usuario = iUsuarioService.guardar(usuarioRequest);
			
		} catch (CargoNoEncontradoException e) {
			
			response.put("message", e.getMessage());
			response.put("error", "CARGO_NO_ENCONTRADO");
			
			// Return
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		// Se convierte de Entity a Dto
		UsuarioDto usuarioResponse = convertToDto(usuario);

		// Return
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponse);
	}

	/**
	 * Este metodo se encarga de convertir de DTO a Entity
	 * @param usuarioDto
	 * @return
	 */
	private Usuario convertToEntity(UsuarioDto usuarioDto) {

		Usuario usuario = modelMapper.map(usuarioDto, Usuario.class);

		// Return
		return usuario;
	}

	/**
	 * Este metodo se encarga de convertir de Entity a DTO
	 * @param usuario
	 * @return
	 */
	private UsuarioDto convertToDto(Usuario usuario) {

		UsuarioDto usuarioDto = modelMapper.map(usuario, UsuarioDto.class);

		// Return
		return usuarioDto;
	}
}