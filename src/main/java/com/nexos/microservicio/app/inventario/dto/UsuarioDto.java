package com.nexos.microservicio.app.inventario.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {
	
	private Integer id;

	@NotBlank
	private String nombre;
	
	private Date fechaRegistro;
	
	@NotNull
	private CargoDto cargo;
}