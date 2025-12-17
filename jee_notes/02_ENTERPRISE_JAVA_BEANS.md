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
del EJB 'CalculadoraBean', luego invoca el método 
```sumar()``` pasándole los parámetros a, b que espera
recibir del "request".

```java
@WebServlet("/testEJB")
public class TestEJB extends HttpServlet {

    @EJB
    private CalculadoraBean ejb;

    protected void doGet(
        HttpServletRequest request
        ,HttpServletResponse response
    ) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        pw.print("<html><body>");

        // Obtener valores del request
        int a = Integer.parseInt(request.getParameter("a"));
        int b = Integer.parseInt(request.getParameter("b"));

        // Invocar al método del EJB
        out.print(ejb.sumar(a, b));

        out.print("</body></html>");
    }
}
```

## Remote or Business Interface

Para que el EJB pueda ser accedido desde un proceso
remoto (que se ejecuta en otra máquina virtual), su clase
debe implementar la interface _remote_, también llamada
**business interface**.

La business interface describe los métodos o lógica de
negocio del componente EJB. En su encabezado, debe
incorporar la anotación @Remote:

```java
@Remote
public interface Calculadora {

    public double sumar(double a, double b);
}
```

Habiendo definido la _remote interface_, el siguiente paso es
programar su implementación con un EJB, ya sea _stateless_ o
_stateful_. 

```java
@Stateless
public class CalculadoraBean implements Calculadora {

    @Override
    public double sumar(double a, double b) {
        return a+b;
    }
}
```

## Cliente _standalone_ de un EJB

Para acceder a un EJB desde un programa Java _standalone_,
debemos buscarlo a través de un "lookup" JNDI 
(Java Naming and Directory Interface).

```java
public class StandaloneComponent {

    public static void main(String[] args) {
        // La conexión JNDI debe especificarse en el archivo
        // jndi.properties
        Context ctx = new InitialContext();

        // Este nombre deberá ser validado con el proveedor
        // del contenedor EJB (TomEE, JBoss, TomCat, Jetty)
        String jndiName = "CalculadoraBeanRemote";
        Calculadora ejb = (Calculadora) ctx.lookup(jndiName);

        // Obtener los parámetros de consola
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);

        // Mostrar el resultado
        System.out.println("Resultado: " ejb.sumar(a,b));

    }
}
```

## _Stateful_ vs _stateless_

La diferencia entre un EJB stateless y uno stateful es la
posibilidad de mantener un vínculo conversacional con el
cliente. De algún modo, podemos plantear una analogía
entre los EJB y los POJOs:

**stateless** ---> Clase utilitaria con miembros estáticos

**stateful**  ---> Clase con métodos y variables de instancia

### Ejemplo de CalculadoraMemBean como _stateful_ bean

File: Calculadora.java (interface)
```java
@Remote
public interface CalculadoraMem {

    void add(double a);

    void resetMem();

    double getMem();
}
```

EJB stateful de implementación:

```java
@Stateful
public class CalculadoraMemBean implements CalculadoraMem {

    // Memoria de la calculadora. Variable privada
    private double mem = 0;

    // Suma un valor a la memoria
    public void add(double a) {
        mem = mem + a;
    }

    // Reset memoria
    @Override
    public void resetMem() {
        mem = 0;
    }

    // Get memoria
    public double getMem() {
        return this.mem;
    }
}
```

```java
public class TestMem {

    public static void main(String[] args) throws Exception
    {
        Context ctx = new InitialContext();

        String jndiName = "CalculadoraMemRemote";

        Calculadora ejb = (Calculadora)ctx.lookup(jndiName);

        Scanner scanner = new Scanner(System.in);

        // Ingresar valor
        String s = leerValorConsola(scanner);

        // Mientras no se escriba "FIN" por consola,
        // Sumar el valor. Al final, imprimir el resultado
        while( !s.equals ("FIN")) {
            double v = Double.parseDouble(s);

            ejb.add(v);

            s = leerValorConsola(scanner);
        }

        System.out.println("Resultado = " + ejb.getMem());
    }

    private static String leerValorConsola(Scanner scanner) {
        String m = "Ingrese valor \"FIN\" para finalizar:";
        System.out.print(m);
        
        return scanner.next();
    }
}
```

## Performance (_Stateless_ es más recomendable)

Los EJB stateful son más costosos en términos de
procesamiento y uso de memoria, ya que el container debe
mantener tantas instancias activas del bean como clientes
concurrentes lo estén requiriendo.

Sin embargo, con un pequeño pool de instancias de EJBs
_stateless_ se puede reducir el _overhead_.

### Singleton (stateless EJB)

A partir de la especificación EJB 3.1 aparecen los 
_singleton_ como caso particular de los _stateless_,
cuya característica es que no serán _pooleados_;
el container no podrá tener más de una instancia activa
del _singleton_: esta instancia será responsable de atender
todos los clientes que demanden al EJB.

## Ciclo de Vida de un EJB

A grandes rasgos, un EJB se crea y se destruye. Además,
el EJB container puede evaluar el "poner en pausa"
("passivar") a un
EJB stateful para ahorrar recursos (i.e. serializar
y guardar en disco).

### Callback Methods

Para saber cada vez que un EJB cambia
de estado, se dispone de un conjunto de
__annotations__ con las que se pueden definir
**callback methods**:

1. ```@PostConstruct``` -> Se ejecuta inmediatamente
después de que el EJB se haya instanciado

2. ```@PreDestroy``` -> Se ejecuta justo antes de que
el EJB se destruya

3. ```@PrePassivate``` -> Sólo para **EJB stateful**. 
Se ejecuta
justo antes de que se serialice y guarde en disco.

4. ```@PostActivate``` -> Sólo para **EJB stateful**.
Se invoca justo después de que el EJB haya sido restaurado.

### Ciclo de Vida de un EJB stateful

1. Create
2. Dependency Injection (if any)
3. PostConstruct callback (if any)
4. init method or \<ejbCreate<METHOD\> (if any)

(Fuente: [https://docs.oracle.com/javaee/6/tutorial/doc/giplj.html](https://docs.oracle.com/javaee/6/tutorial/doc/giplj.html)
¿Por qué se reinician los 
números y se invierten las operaciones?)
1. Remove
2. PreDestroy callback (if any)