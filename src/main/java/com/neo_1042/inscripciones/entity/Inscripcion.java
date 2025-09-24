package com.neo_1042.inscripciones.entity;

import javax.persistence.*;

@Entity(name="TBL_INSCRIPCION")
public class Inscripcion {

	@Id
	@Column(name="id_inscripcion")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idInscripcion;

	@ManyToOne
	@JoinColumn(name="id_alumno")
	private Alumno alumno;

	@ManyToOne
	@JoinColumn(name="id_curso")
	private Curso curso;

	@ManyToOne
	@JoinColumn(name="id_forma_pago")
	private FormaPago formaPago;

	private Date fecha;

	public int getIdInscripcion() {
		return idInscripcion;
	}

	public void setIdInscripcion(int idInscripcion) {
		this.idInscripcion = idInscripcion;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public FormaPago getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}