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
public Alumno identificarAlumno(int idAlumno)
```

La clase ```Alumno``` será el mapping de JPA con el que representaremos a la tabla
ALUMNO en la BD.