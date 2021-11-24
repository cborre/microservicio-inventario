package com.nexos.microservicio.app.inventario.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MercanciaDto {

	private Integer id;

	@NotBlank
	private String nombre;

	@NotNull
	private Integer cantidad;

	@NotNull
	@PastOrPresent
	@DateTimeFormat(pattern = "yyyy-MM-dd")	
	private Date fechaIngreso;

	@NotNull
	private ProductoDto producto;

	@NotNull
	private UsuarioDto usuarioRegistro;
	private UsuarioDto usuarioModifica;
	private Date fechaModifica;
}