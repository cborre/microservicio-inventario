package com.nexos.microservicio.app.inventario.dto;

import javax.validation.constraints.NotBlank;

public class CargoDto {

	private Integer id;

	@NotBlank
	private String nombre;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}