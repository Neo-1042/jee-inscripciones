package com.neo_1042.inscripciones.entity;

import javax.persistence.*;

@Entity(name=TBL_CURSO)
public class Curso {

	@Id
	@Column(name="id_curso")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idCurso;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="duracion")
	private int duracion;

	@Column(name="precio")
	private double precio;

	// To do: @ManyToMany from TBL_CURSO_FORMA_PAGO

}

