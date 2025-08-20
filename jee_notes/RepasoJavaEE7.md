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
el uso de _annotations_ como ```@Resource```


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

Los viejos EJB (versión 2.1 y anteriores) se componían de 2 
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

### Tipos de EJB

1. Session beans (stateful and stateless)
2. Entity beans (obsoletos, sustituidos por **JPA**)
3. Message-driven beans (**_Java Message Service_**)

## CAP 5: CAPA DE DATOS. JPA = Java Persistence API 

Creando la estructrura de carpetas del proyecto:

/Projects/jee-inscripciones/src/main/java/com/rafa/...
    /{dao, datasource, dto, modelo, seguridad, service, servlets}
