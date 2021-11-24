package com.nexos.microservicio.app.inventario.models.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mercancias")
public class Mercancia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(unique = true)
	private String nombre;

	private Integer cantidad;

	@Column(name = "fecha_ingreso")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaIngreso;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "producto_id")
	private Producto producto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_registro_id")
	private Usuario usuarioRegistro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_modifica_id", nullable = true)
	private Usuario usuarioModifica;

	@Column(name = "fecha_modificacion", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaModifica;

}