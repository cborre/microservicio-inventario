package com.nexos.microservicio.app.inventario.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexos.microservicio.app.inventario.dto.MercanciaDto;
import com.nexos.microservicio.app.inventario.dto.MercanciaUpdateDto;
import com.nexos.microservicio.app.inventario.exceptions.MercanciaNoEncontradaException;
import com.nexos.microservicio.app.inventario.exceptions.ProductoNoEncontradoException;
import com.nexos.microservicio.app.inventario.exceptions.SinPermisoException;
import com.nexos.microservicio.app.inventario.exceptions.UsuarioNoEncontradoException;
import com.nexos.microservicio.app.inventario.models.entity.Mercancia;
import com.nexos.microservicio.app.inventario.services.IMercanciaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/inventario")
public class MercanciaController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	IMercanciaService iMercanciaService;

	@Operation(summary = "Lista todas las mercancias")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Muestra la lista de mercancias", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MercanciaDto.class)) }),
			@ApiResponse(responseCode = "500", description = "En caso de ocurrir un error interno", content = @Content) })
	@GetMapping("/mercancias")
	public ResponseEntity<?> listar() {

		// Consulto los mercancias
		List<Mercancia> mercancias = iMercanciaService.listar();

		// Se convierte en una lista de mercancias DTO
		List<MercanciaDto> mercanciaResponse = mercancias.stream().map(this::convertToDto).collect(Collectors.toList());

		// Return
		return ResponseEntity.status(HttpStatus.OK).body(mercanciaResponse);
	}

	@Operation(summary = "Se encarga de guardar una mercancia")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Guardo mercancia correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MercanciaDto.class)) }),
			@ApiResponse(responseCode = "400", description = "En caso de ingresar un dato incorrecto", content = @Content),
			@ApiResponse(responseCode = "500", description = "En caso de ocurrir un error interno", content = @Content) })
	@PostMapping("/mercancias")
	public ResponseEntity<?> guardar(@RequestBody @Valid MercanciaDto mercanciaDto, BindingResult result) {

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
		Mercancia mercanciaRequest = convertToEntity(mercanciaDto);

		// Se guarda el mercancia
		Mercancia mercancia;

		try {

			// Guardar
			mercancia = iMercanciaService.guardar(mercanciaRequest);

		} catch (ProductoNoEncontradoException e) {

			response.put("message", e.getMessage());
			response.put("error", "PRODUCTO_NO_ENCONTRADO");

			// Return
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (UsuarioNoEncontradoException e) {

			response.put("message", e.getMessage());
			response.put("error", "USUARIO_NO_ENCONTRADO");

			// Return
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (DataIntegrityViolationException e) {

			String mensaje = null;

			if (e.getMessage().contains("ConstraintViolationException")) {
				mensaje = "La mercancia ya existe";
				response.put("message", mensaje);
				response.put("error", "MERCANCIA_YA_EXISTE");
			}

			// Return
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		// Se convierte de Entity a Dto
		MercanciaDto mercanciaResponse = convertToDto(mercancia);

		// Return
		return ResponseEntity.status(HttpStatus.CREATED).body(mercanciaResponse);
	}

	@Operation(summary = "Se encarga de editar una mercancia")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Actualiza mercancia correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = MercanciaUpdateDto.class)) }),
			@ApiResponse(responseCode = "400", description = "En caso de ingresar un dato incorrecto", content = @Content),
			@ApiResponse(responseCode = "500", description = "En caso de ocurrir un error interno", content = @Content) })
	@PutMapping("/mercancias/{id}")
	public ResponseEntity<?> editar(
			@Parameter(description = "ID de la mercancia a editar")
			@PathVariable Integer id,
			@RequestBody @Valid MercanciaUpdateDto mercanciaDto,
			BindingResult result) {

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
		Mercancia mercanciaRequest = convertToEntity(mercanciaDto);

		// Se guarda el mercancia
		Mercancia mercancia;

		try {

			// Guardar
			mercancia = iMercanciaService.editar(id, mercanciaRequest);

		} catch (MercanciaNoEncontradaException e) {

			response.put("message", e.getMessage());
			response.put("error", "MERCANCIA_NO_ENCONTRADA");

			// Return
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (ProductoNoEncontradoException e) {

			response.put("message", e.getMessage());
			response.put("error", "PRODUCTO_NO_ENCONTRADO");

			// Return
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (UsuarioNoEncontradoException e) {

			response.put("message", e.getMessage());
			response.put("error", "USUARIO_NO_ENCONTRADO");

			// Return
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (DataIntegrityViolationException e) {

			String mensaje = null;

			if (e.getMessage().contains("ConstraintViolationException")) {
				mensaje = "La mercancia ya existe";
				response.put("message", mensaje);
				response.put("error", "MERCANCIA_YA_EXISTE");
			}

			// Return
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}

		// Se convierte de Entity a Dto
		MercanciaUpdateDto mercanciaResponse = convertToUpdateDto(mercancia);

		// Return
		return ResponseEntity.status(HttpStatus.CREATED).body(mercanciaResponse);
	}

	@Operation(summary = "Se encarga de eliminar mercancia")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Elimina mercancia correctamente", content = {
					@Content(mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", description = "En caso de ingresar un dato incorrecto", content = @Content),
			@ApiResponse(responseCode = "500", description = "En caso de ocurrir un error interno", content = @Content) })
	@DeleteMapping("/mercancias/{id}/{usuarioId}")
	public ResponseEntity<?> eliminar(
			@Parameter(description = "ID de la mercancia a eliminar")
			@PathVariable Integer id,
			@Parameter(description = "ID del usuario que desea eliminar la mercancia")
			@PathVariable Integer usuarioId) {

		// Variables
		Map<String, Object> response = new HashMap<>();

		try {

			// Eliminar
			iMercanciaService.eliminar(id, usuarioId);

		} catch (UsuarioNoEncontradoException e) {

			response.put("message", e.getMessage());
			response.put("error", "USUARIO_NO_ENCONTRADA");

			// Return
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);

		} catch (MercanciaNoEncontradaException e) {

			response.put("message", e.getMessage());
			response.put("error", "MERCANCIA_NO_ENCONTRADA");

			// Return
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
			
		} catch (SinPermisoException e) {

			response.put("message", e.getMessage());
			response.put("error", "SIN_PERMISO");

			// Return
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}

		// Return
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	/**
	 * Este metodo se encarga de convertir de DTO a Entity
	 * 
	 * @param usuarioDto
	 * @return
	 */
	private Mercancia convertToEntity(MercanciaDto mercanciaDto) {

		Mercancia mercancia = modelMapper.map(mercanciaDto, Mercancia.class);

		// Return
		return mercancia;
	}

	private Mercancia convertToEntity(MercanciaUpdateDto mercanciaDto) {

		Mercancia mercancia = modelMapper.map(mercanciaDto, Mercancia.class);

		// Return
		return mercancia;
	}

	/**
	 * Este metodo se encarga de convertir de Entity a DTO
	 * 
	 * @param mercancia
	 * @return
	 */
	private MercanciaDto convertToDto(Mercancia mercancia) {

		MercanciaDto mercanciaDto = modelMapper.map(mercancia, MercanciaDto.class);

		// Return
		return mercanciaDto;
	}

	private MercanciaUpdateDto convertToUpdateDto(Mercancia mercancia) {

		MercanciaUpdateDto mercanciaDto = modelMapper.map(mercancia, MercanciaUpdateDto.class);

		// Return
		return mercanciaDto;
	}
}