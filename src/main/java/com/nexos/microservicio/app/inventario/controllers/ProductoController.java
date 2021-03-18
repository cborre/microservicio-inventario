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

import com.nexos.microservicio.app.inventario.dto.ProductoDto;
import com.nexos.microservicio.app.inventario.models.entity.Producto;
import com.nexos.microservicio.app.inventario.services.IProductoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/inventario")
public class ProductoController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	IProductoService iProductoService;

	@Operation(summary = "Lista todos los productos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Muestra la lista de productos", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDto.class)) }),
			@ApiResponse(responseCode = "500", description = "En caso de ocurrir un error interno", content = @Content) })
	@GetMapping("/productos")
	public ResponseEntity<?> listar() {

		// Consulto los productos
		List<Producto> productos = iProductoService.listar();

		// Se convierte en una lista de productos DTO 
		List<ProductoDto> productoResponse = productos.stream()
		          .map(this::convertToDto)
		          .collect(Collectors.toList());
		
		// Return
		return ResponseEntity.status(HttpStatus.OK).body(productoResponse);
	}

	@Operation(summary = "Se encarga de guardar un producto")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Guardo el producto correctamente", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = ProductoDto.class)) }),
			@ApiResponse(responseCode = "400", description = "En caso de ingresar un dato incorrecto, ejemlo si envia el nombre vacio", content = @Content),
			@ApiResponse(responseCode = "500", description = "En caso de ocurrir un error interno", content = @Content) })
	@PostMapping("/productos")
	public ResponseEntity<?> guardar(@RequestBody @Valid ProductoDto productoDto, BindingResult result) {

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
		Producto productoRequest = convertToEntity(productoDto);

		// Se guarda el producto
		Producto producto = iProductoService.guardar(productoRequest);

		// Se convierte de Entity a Dto
		ProductoDto productoResponse = convertToDto(producto);

		// Return
		return ResponseEntity.status(HttpStatus.CREATED).body(productoResponse);
	}

	/**
	 * Este metodo se encarga de convertir de DTO a Entity
	 * @param poductoDto
	 * @return
	 */
	private Producto convertToEntity(ProductoDto productoDto) {

		Producto producto = modelMapper.map(productoDto, Producto.class);

		// Return
		return producto;
	}

	/**
	 * Este metodo se encarga de convertir de Entity a DTO
	 * @param producto
	 * @return
	 */
	private ProductoDto convertToDto(Producto producto) {

		ProductoDto productoDto = modelMapper.map(producto, ProductoDto.class);

		// Return
		return productoDto;
	}
}