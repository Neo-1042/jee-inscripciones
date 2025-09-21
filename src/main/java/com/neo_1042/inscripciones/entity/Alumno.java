package com.neo_1042.inscripciones.entity;

import javax.persistence.*;

@Entity(name = "TBL_ALUMNO")
public class Alumno {

    @Id
    @Column(name = "id_alumno")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAlumno;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @ManyToMany
    @JoinTable(name = "TBL_INSCRIPCION", joinColumns = { @JoinColumn(name = "id_alumno") }, inverseJoinColumns = {
            @JoinColumn(name = "id_curso") })
    private List<Curso> cursos;

    // Getters and setters
    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

}
