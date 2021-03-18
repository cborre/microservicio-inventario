package com.nexos.microservicio.app.inventario.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UsuarioDto {
	
	private Integer id;

	@NotBlank
	private String nombre;
	
	private Date fechaRegistro;
	
	@NotNull
	private CargoDto cargo;

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

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public CargoDto getCargo() {
		return cargo;
	}

	public void setCargo(CargoDto cargo) {
		this.cargo = cargo;
	}
	
}