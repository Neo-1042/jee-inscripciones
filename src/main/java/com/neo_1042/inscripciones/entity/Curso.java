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

	@ManyToMany
	@JoinTable(
		name="TBL_CURSO_FORMA_PAGO"
		,joinColumns={@JoinColumn(name="id_curso")}
		,inverseJoinColumns={@JoinColumn(name="id_forma_pago")}
	)
	private List<FormaPago> formasPago;

	@ManyToMany
	@JoinTable(
		name="INSCRIPCION"
		,joinColumns={@JoinColumn(name="id_curso")}
		,inverseJoinColumns={@JoinColumn(name="id_alumno")}
	)
	private List<Curso> alumnos;

	public int getIdCurso() {
		return idCurso;
	}

	public void setIdCurso(int idCurso) {
		this.idCurso = idCurso;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public List<FormaPago> getFormasPago() {
		return formasPago;
	}

	public void setFormasPago(List<FormaPago> formasPago) {
		this.formasPago = formasPago;
	}

	public List<Curso> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Curso> alumnos) {
		this.alumnos = alumnos;
	}

	


}

