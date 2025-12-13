# CAPA DEL NEGOCIO. Enterprise Java Beans (EJB)

### Enterprise JavaBeans (Session Beans)

Los Enterprise Java Beans son componentes basados en 
POJOs cuya responsabilidad es encapsular y resolver la
lógica de negocio de la aplicación. El container JEE 
administra su ciclo de vida y les
provee servicios de transacciones y seguridad.

A partir de la especificación EJB 3, se simplificó y mejoró
la implementación de los EJB. Java EE 7 incluye una
implementación EJB 3.2

### POJO = Plain Old Java Object

Los **viejos EJB (versión 2.1 y anteriores)** se componían de 2 
interfaces: _interface home_ e _interface remote_. Estas
interfaces definían los métodos de **instanciación** y los
métodos de **negocio** del componente. 
Entonces, el EJB consistía de una clase que debía implementar
estas interfaces; y todo esto debería escribirse en un archivo:

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

### Ejemplo de un _stateless_ session bean:

```java
package com.example.service;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;

@Stateless
@LocalBean // Optional, restricts accessibility to the local app
public class HelloServiceBean {

    public String sayHello(String name) {
        // Business logic goes here
        // No client-specific state is stored between calls
        return "Hello, " + name;
    }
}
```

## Tipos de cliente de un EJB

Recordemos que el EJB es un componente del lado del servidor
que se ejecuta dentro de un _EJB container_.
Llamaremos "cliente del EJB" a cualquier proceso, local o
remoto, que requiera acceder a sus servicios.

1. Cliente **web** (un servlet, por ejmplo)
2. Cliente **EJB** (otro EJB)
3. Cliente **standalone** (un _main_)

Acceso Local -> Acceder al EJB desde un servlet o desde otro EJB.
Es decir, un componente dentro de la misma máquina virtual.

Acceso Remoto -> Acceder al EJB desde otra máquina virtual.
Por ejemplo, desde un programa Java _standalone_.

## Recordatorio: CDI = Context Dependency Injection

CDI (a partir de JEE 6) consiste en un conjunto de annotations
que simplifica el acceso a los recursos que se encuentran
publicados en el servicio de nombres (JNDI = Java
Naming and Directory Interface).
Es decir, CDI encapsula buena parte de las llamadas JNDI
haciéndolas trasnparentes al programador:

Ejemplo con **@Resource**:

```java
@WebServlet("/testDataSource")
public class TestDataSource extends HttpServlet {

    @Resource(name="jeeAFondoDS")
    private DataSource ds;
}
```

## Cliente Web de un EJB

El siguiente servlet utiliza CDI para obtener una instancia
del EJB 'CalculadoraBean', luego invoca el método ```sumar()```

```java
@WebServlet("/testEJB")
public class TestEJB extends HttpServlet {

    @EJB
    private CalculadoraBean ejb;

    protected void doGet(
        HttpServletRequest request
        ,HttpServletResponse response
    ) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
    }
}
```