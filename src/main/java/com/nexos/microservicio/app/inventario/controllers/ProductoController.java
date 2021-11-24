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

import com.nexos.microservicio.app.inventario.dto.ProductoDto;
import com.nexos.microservicio.app.inventario.models.entity.Producto;
import com.nexos.microservicio.app.inventario.services.IProductoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/inventario")
@Api(value = "ConfigSetup", tags = "Operations pertaining to producto")
public class ProductoController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	IProductoService iProductoService;

	@ApiOperation(value = "Lista todos los productos")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Muestra la lista de productos"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
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

	@ApiOperation(value = "Se encarga de guardar un producto")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Guardo el producto correctamente"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
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
	 * @param productoDto
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