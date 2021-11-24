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

import com.nexos.microservicio.app.inventario.dto.CargoDto;
import com.nexos.microservicio.app.inventario.models.entity.Cargo;
import com.nexos.microservicio.app.inventario.services.ICargoService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/inventario")
@Api(value = "ConfigSetup", tags = "Operations pertaining to cargo")
public class CargoController {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	ICargoService iCargoService;

	@ApiOperation(
			value = "Lista todos los cargos",
			response = CargoDto.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@GetMapping("/cargos")
	public ResponseEntity<?> listar() {

		// Consulto los cargos
		List<Cargo> cargos = iCargoService.listar();

		// Se convierte en una lista de cargos DTO
		List<CargoDto> cargoResponse = cargos.stream().map(this::convertToDto).collect(Collectors.toList());

		// Return
		return ResponseEntity.status(HttpStatus.OK).body(cargoResponse);
	}

	@ApiOperation("Se encarga de guardar un cargo")
	@ApiResponses(value = {
			@ApiResponse(code = 201, message = "Guardo el cargo correctamente"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@PostMapping("/cargos")
	public ResponseEntity<?> guardar(@RequestBody @Valid CargoDto cargoDto, BindingResult result) {

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
		Cargo cargoRequest = convertToEntity(cargoDto);

		// Se guarda el cargo
		Cargo cargo = iCargoService.guardar(cargoRequest);

		// Se convierte de Entity a Dto
		CargoDto cargoResponse = convertToDto(cargo);

		// Return
		return ResponseEntity.status(HttpStatus.CREATED).body(cargoResponse);
	}

	/**
	 * Este metodo se encarga de convertir de DTO a Entity
	 * 
	 * @param cargoDto
	 * @return
	 */
	private Cargo convertToEntity(CargoDto cargoDto) {

		Cargo cargo = modelMapper.map(cargoDto, Cargo.class);

		// Return
		return cargo;
	}

	/**
	 * Este metodo se encarga de convertir de Entity a DTO
	 * 
	 * @param cargo
	 * @return
	 */
	private CargoDto convertToDto(Cargo cargo) {

		CargoDto cargoDto = modelMapper.map(cargo, CargoDto.class);

		// Return
		return cargoDto;
	}
}