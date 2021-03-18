package com.nexos.microservicio.app.inventario.dto;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.springframework.format.annotation.DateTimeFormat;

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

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Date getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public ProductoDto getProducto() {
		return producto;
	}

	public void setProducto(ProductoDto producto) {
		this.producto = producto;
	}

	public UsuarioDto getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(UsuarioDto usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public UsuarioDto getUsuarioModifica() {
		return usuarioModifica;
	}

	public void setUsuarioModifica(UsuarioDto usuarioModifica) {
		this.usuarioModifica = usuarioModifica;
	}

	public Date getFechaModifica() {
		return fechaModifica;
	}

	public void setFechaModifica(Date fechaModifica) {
		this.fechaModifica = fechaModifica;
	}

}