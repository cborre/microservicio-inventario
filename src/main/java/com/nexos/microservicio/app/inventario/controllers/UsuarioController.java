package com.nexos.microservicio.app.inventario.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/inventario")
public class UsuarioController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	IUsuarioService iUsuarioService;

	@Operation(summary = "Lista todos los usuarios")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Muestra la lista de usuarios", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class)) }),
			@ApiResponse(responseCode = "500", description = "En caso de ocurrir un error interno", content = @Content) })
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

	@Operation(summary = "Se encarga de guardar un usuario")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Guardo un usuario correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioDto.class)) }),
			@ApiResponse(responseCode = "400", description = "En caso de ingresar un dato incorrecto, ejemlo si envia el nombre vacio", content = @Content),
			@ApiResponse(responseCode = "500", description = "En caso de ocurrir un error interno", content = @Content) })
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