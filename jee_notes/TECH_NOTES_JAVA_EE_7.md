# C6 PONIENDO TODO JUNTO. REPASO

## CAP 1 COMENZANDO CON JEE. Diseño de aplicación por capas

WEBAPP = {servlets, JSP, HTML, *.class, *.jar}

User <-> View <-> Controller <-> Facade <-> DAOs <-> Database

### Backend
Se encarga de procesar los datos que han sido ingresados por el usuario, y en función a éstos, responder. 

### Facade

Es el punto de contacto entre el front y el resto del back,
donde reside la lógica de negocio (MODEL)

### Capa de acceso a los datos DAOs = Data Access Object
    
Encapsulan las operaciones y transacciones SQL. 
Se tiene un DAO por cada tabla.

## CAP 2: CAPA DE PRESENTACIÓN

Arquitectura **MVC = Model View Controller**
- **Model** = lógica de negocio (backend)
- **View** = vistas/pantallas (JSP)
- **Controller** = Los controladores (Servlets) que tienen 
interacción directa con el facade.

VIEW <-> CONTROLLER <-> facade <-> MODEL

## CAP 3: JNDI E INYECCIÓN DE DEPENDENCIAS.

**JNDI** = Java Naming and Directory Interface

**CDI**  = Contexts and Dependency Injection (@)

El _JEE container_ utiliza al **servicio JNDI** como un 
repositorio para publicar allí los recursos que deben estar
a disposición de los componentes. El programador puede acceder
a esos componentes por medio de la JNDI API. 
Asimismo, podemos tener acceso a estos recursos a través de
la inyección de dependencias que implementa CDI mediante
el uso de _annotations_ como ```@Resource```.


## CAP 4: CAPA DEL NEGOCIO. Enterprise JavaBeans (EJB)

### Enterprise JavaBeans (Session Beans)

Componentes cuya responsabilidad es encapsular y resolver la
lógica de negocio de la aplicación. El container JEE 
administra su ciclo de vida y les
provee servicios de transacciones y seguridad.

A partir de la especificación EJB 3, se simplificó la
implementación de los EJB. Java EE 7 incluye una implementación
EJB 3.2

### POJO = Plain Old Java Object

Los **viejos EJB (versión 2.1 y anteriores)** se componían de 2 
interfaces: _interface home_ e _interface remote_. Estas
interfaces definían los métodos de **instanciación** y los
métodos de **negocio** del componente. 
Entonces, el EJB consistía de una clase que debía implementar
estas interfaces; y todo esto debería escribirse en un archivo

```ejb-jar.xml```

Además, la "implementación" de las interfaces _home_ y 
_remote_ por parte de la clase EJB no era real. En el código de
la clase, no debía escribirse la palabra _implements_, por lo
que la validación de la correcta implementación de los métodos
no se hacía en tiempo de compilación, sino en tiempo de
despliegue (_deployment_).

Por esto, surgió la necesidad de establecer la diferencia entre
un objeto Java común (POJO) y un componente EJB.

Hablamos de una **aplicación Java Enterprise** cuando se
utilizan los EJB con el énfasis en características de
aplicaciones:
- Escalables
- Transaccionales
- Distribuibles
- Seguras

### Aplicación _Enterprise_ vs Aplicación _Web_

**Enterprise App** => La app utiliza **EJB** en la capa de negocio
y **JPA** en la capa de persistencia, independientemente 
de la tecnología que use en el front-end.

**Web App** => El backend **NO UTILIZA EJB**. El front-end
puede usar servlets, JSPs (que son en realidad servlets),
JSF, etc.

### Tipos de EJB

1. **Session beans** (stateful and stateless. Usualmente 
nos referimos a ellos cuando se habla de los Beans)
2. **Entity beans** (obsoletos, sustituidos por **JPA**)
3. **Message-driven beans** (**_Java Message Service_**)

## CAP 5: CAPA DE DATOS. JPA = Java Persistence API 

Creando la estructrura de carpetas del proyecto:

/Projects/jee-inscripciones/src/main/java/com/rafa/...
    /{dao, datasource, dto, modelo, seguridad, service, servlets, common}

### DAO = Data Access Object

Clases que deben ser implementadas con **Session Beans**
y sus métodos deben resolverse usando **JPA**.
Los DAO se invocarán desde la capa del "FACADE".

Recordatorio:

User <-> View <-> Controller <-> Facade <-> DAO <-> DB

## CAP 6: Poniendo Todo Junto. JEE APP de Inscripciones

La aplicación que generaré tendrá la siguiente configuración:

- Front end web con estructura MVC
- Única página JSP para implementar todas las vistas
- Servlets (controllers) para controlar el proceso web e
invocar al FACADE
- FACADE implementado como un Session Bean, cuyos métodos
transaccionales invocarán a los DAO.
- DAOs implementados también como Session Beans, cuyos
métodos usarán JPA para resolver los accesos a las tablas
de la base de datos.

### Etapas: Análisis y Desarrollo

Dentro de cada etapa se trabajarán sobre las 4 capas del
**modelo _n-tier_** (próximamente).

### Análisis

Alcance y casos de uso > View > Model (lógica de negocio) 
    > Modelo de Datos

*Sin análisis del modelo de objetos de dominio (_mappings, ORM_) ni tampoco los DAO

### Desarrollo

Modelo de Datos (con _mappings_) > DAO > Facade
    Controller > Presentación (View)

## Capa de Datos

Creación de los siguientes scripts SQL:

TBL_CURSO
```sql
CREATE OR REPLACE TABLE TBL_CURSO (
    id_curso INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1)
        NOT NULL PRIMARY KEY
    ,descripcion VARCHAR -- Nombre del curso
    ,duracion INTEGER -- En meses
    ,precio DOUBLE -- USD
);
```

TBL_FORMA_PAGO
```sql
CREATE OR REPLACE TABLE TBL_FORMA_PAGO (
    id_forma_pago INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1)
        NOT NULL PRIMARY KEY
    ,descripcion VARCHAR
    ,recargo DOUBLE
);
```

Recordatorio: sintaxis para agregar constraint FK (Oracle SQL):
```sql
ALTER TABLE child_table_name
ADD CONSTRAINT constraint_name
FOREIGN KEY (child_column_name)
REFERENCES parent_table_name (parent_column_name);
```


TBL_CURSO_FORMA_PAGO (tabla relacional)
```sql
CREATE OR REPLACE TABLE TBL_CURSO_FORMA_PAGO (
    id_curso INTEGER -- FK
    ,id_forma_pago INTEGER -- FK
);

ALTER TABLE TBL_CURSO_FORMA_PAGO
ADD CONSTRAINT fk_id_curso
FOREIGN KEY (id_curso)
REFERENCES tbl_curso (id_curso)
;

ALTER TABLE TBL_CURSO_FORMA_PAGO
ADD CONSTRAINT fk_id_forma_pago
FOREIGN KEY (id_forma_pago)
REFERENCES tbl_forma_pago (id_forma_pago)
;

-- PK compuesta por las 2 FKs:
ALTER TABLE TBL_CURSO_FORMA_PAGO
ADD CONSTRAINT pk_curso_forma_pago
PRIMARY KEY (id_curso, id_forma_pago)
```

TBL_ALUMNO
```sql
CREATE OR REPLACE TABLE TBL_ALUMNO (
    id_alumno INTEGER GENERATED BY DEFAULT AS IDENTITY (START WITH 1) NOT NULL PRIMARY KEY
    ,nombre VARCHAR
    ,telefono VARCHAR
);
```

TBL_INSCRIPCION
```sql
CREATE OR REPLACE TABLE TBL_INSCRIPCION (
    id_inscripcion INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1) PRIMARY KEY
    ,id_alumno INTEGER -- FK
    ,id_curso INTEGER -- FK
    ,fecha DATE
);

ALTER TABLE TBL_INSCRIPCION
ADD CONSTRAINT fk_id_alumno
FOREIGN KEY (id_alumno)
REFERENCES tbl_alumno (id_alumno)
;

ALTER TABLE TBL_INSCRIPCION
ADD CONSTRAINT fk_id_curso
FOREIGN KEY (id_curso)
REFERENCES tbl_curso (id_curso)
;
```


END_OF_FILE