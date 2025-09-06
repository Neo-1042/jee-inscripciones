# Aplicativo de Inscripciones a Cursos

Antes de poder inscribirse en un curso, el cliente deberá registrarse como un
alumno de la compañía de capacitación.

## Casos de Uso

1. Login para identificar alumno (Pantalla 1)
2. Registrar alumno nuevo (Pantalla 2)
3. Inscribir un alumno a un curso (Pantalla 3)

Los datos y resultados que se mostrarán en las pantallas, se obtendrán por medio de
invocar a los diferentes métodos o servicios del FACADE.

## Soporte del back end a las pantallas

1. Login. El usuario ingresará su id, y al presionar "ingresar", el sistema deberá
verrificar que el id ingresado sea correcto. Para dar soporte a esta pantalla, el
_facade_ deberá proveer un método:
```java
// Retorna el alumno encontrado en la BD
public Alumno identificarAlumno(int idAlumno);
```

La clase ```Alumno``` será el mapping de JPA con el que representaremos a la tabla
ALUMNO en la BD.

2. Registro
Para dar soporte a esta pantalla, el
_facade_ deberá proveer un método:
```java
// Retorna el id del alumno registrado
public int registrarAlumno(Alumno a);
```

3. Inscripción a un curso


```java
// Retorna el identificador del curso al que se inscribió
public int registrarInscripcion(Alumno a
                            ,int idCurso
                            ,int idFormaPago);
```

## Soporte Adicional

Los combos de cursos y formas de pago deben ser provistas
de información por el _facade_.

* Nueva Regla de Negocio: distintos cursos aceptarán diferentes
formas de pago. Se agregarán los siguientes métodos para
obtener información sobre los cursos y sus formas de pago:

```java
public List<Curso> obtenerCursos();

public List<FormaPago> obtenerFormasPago(int idCurso);
```

_Mappings_ (ORM) para estas entidades:
- Clase "Curso" <-> Tabla CURSO
- Clase "FormaPago" <-> Tabla FORMA_PAGO

# Capa de Datos. Modelo de Datos Relacional

### Contexto

La empresa de capacitación ofrece cursos a sus alumnos. Los
alumnos se inscriben en dichos cursos y los pagan eligiendo de
la lista de formas de pago disponibles para cada curso.

Extrayendo los **sustantivos** y sus relaciones, podemos deducir
que CURSO, ALUMNO, INSCRIPCION y FORMA_PAGO serán entidades.

![image info](./images/ERD_00.png)

Dado que cada curso admite un subconjunto diferente de formas de
pago, tendremos una relación _many-to-many_ entre CURSO y
FORMA_PAGO, que se implementará a través de la **tabla de paso:
CURSO_FORMA_PAGO**

## Descripción de las entidades (tables)

- ALUMNO: contiene nombre y número de teléfono de los clientes.
- CURSO: contiene el nombre (descripción), duración (horas)
y precio.
- FORMA_PAGO: diferentes modalidades de pago que acepta la 
empresa de capacitación.
- INSCRIPCION: mantiene un registro de las inscripciones de
los alumnos en los diferentes cursos. Además, esta tabla
implementa la relación _many-to-many_ que existe entre
ALUMNO y CURSO.
-CURSO_FORMA_PAGO: tabla netamente relacional (de paso) que
implementa la relación _many-to-many_ entre CURSO y FORMA_PAGO.

EOF