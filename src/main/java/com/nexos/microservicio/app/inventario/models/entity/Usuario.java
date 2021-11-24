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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String nombre;

	@Column(name = "fecha_registro")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaRegistro;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cargo_id")
	private Cargo cargo;

	@PrePersist
	public void prePersist() {
		this.fechaRegistro = new Date();
	}
}