# JAR, WAR and EAR

1. **JAR** = Java Archive
2. **WAR** = Web Archive
3. **EAR** = Enterprise Archive

At their core, JAR, WAR and EAR files are essentially **ZIP** archives with specific internal directory structures and metadata. These represent different tiers of a Java Enterprise application, and they are designed to nest inside one another when deployed to an application server like WebSphere (WAS).

![image info](./images/EAR_WAR_JAR.png.webp) *Source=Medium*

## 1] JAR - The Building Block

- **What it is**: JAR files are the most basic archive format in Java. JARs packages compiled `*.class` files, metadata and resources (like properties and XML files) into a single file.

- **Structure**: Contains a `META-INF/MANIFEST.MF` file.

- **WAS Context**: You should use JARs for reusable utility libraries, backend business logic in the form of Enterprise Java Beans (EJB JARs) or JPA entity classes.

## 2] WAR - The Web Layer

- **What it is**: An archive specifically structured to **deliver web applications over HTTP**.

- **Structure**: must contain a `WEB-INF/` directory. Inside, there is a `WEB-INF/web.xml` (**deployment descriptor**), a `classes/` folder for compiled servlets or controllers, and a `lib/` folder where utility JARs are placed. It also houses static web resources like HTML, CSS, JavaScript and JSPs.

- **WAS Context**: A WAR is deployed into WebSphere's Web Container. If your backend APIs or microservices only need to handle REST/HTTP requests, a standalone WAR is usually sufficient.

## 3] EAR - The Wrapper

- **What it is**: The highest-level deployment unit in Java EE / Jakarta EE. It encapsulates **multiple WARs** and **JARs** into a single cohesive application.

- **Structure**: Contains a `META-INF/application.xml` deployment descriptor that tells the server which WARs and EJB JARs are inside, and how they map to specific context roots.

- **WAS Context**: Deployed at the Application Server level in WebSphere. **WAS** uses the **EAR structure** to manage **classloading** hierarchies (e.g., determining whether to use `PARENT_LAST` or `PARENT_FIRST` classloading) and to share resources globally across all modules within the EAR.

## Use Cases

### Use a JAR
When you are compiling a **common utility library** (like custom date formatters, logging utilites or integration mappings) that needs to be shared across multiple applications, or when defining business logic EJBs.

### Use a WAR
When you are deploying a **standalone web application** or a **RESTful microservice** (like a Spring Boot application packaged for external deployment). If the application **does not require EJBs** or multiple **distinct modules** communicating internally, deploying a **standalone WAR** is perfectly fine and often preferred for modern, decentralized architectures.

### Use an EAR
When you have a **complex enterprise system** (such as a **large banking application**) that consists of **multiple frontends** (**multiple WARs**) sharing a **common backend business logic** tier (EJBs JARs). Packaging them in an EAR allows WAS to deploy them as a single unit, ensuring they share the **same classloader context**, **transaction managers**, and **security roles**.

# WebSphere Archive Hierarchy

![image info](./images/WAS_Archive_Hierarchy.png)

## EAR  (target: WAS App Classloader)

Top-level enterprise archive encapsulation. It bundles **web modules** (WARs), **Enterprise Java Beans** (EJB JARs), and **shared libraries** with an `application.xml` deployment descriptor for the WAS.

## WAR (target: Web Container)

Encapsulates the **web application components** (Servlets, JSPs, HTML, JS, CSS) and web-specific libraries. WAS routes incoming HTTP/HTTPS requests directly to the Web Container executing this module.

## EJB JARs (target: EJB Container Engine)

Contains **transactional business logic components** (stateless/stateful Session Beans and Message-Driven Beans). The WAS EJB Container provides *declarative* security, transaction management and remoting(¿?). 

Core transactional enterprise services and JMS asynchronous message consumers. Executes within WAS EJB container runtime with managed lifecycle and connection pooling. Executes within WebSphere's EJB container runtime with managed lifecycle and connection pooling.

## Lib JARs (target = Application Classloader)

Utility libraries residing in the `EAR(root)` or `EAR/lib` directory. Made accessible to all WAR and EJB modules within the enterprise application, avoiding duplicate dependency loading. Common domain objects, DTOs, DAOs, enums, and utility classes used jointly by Web and EJB tiers across the deployment hierarchy.

# Arquitectura Java EE Tradicional

Los proyectos de tipo **service** (backend) y **app** (frontend/orquestador) se empaquetan en archivos **EAR** para servidores de aplicaciones completos como el **WAS**, o en archivos WAR para contenedores web más ligeros como el Tomcat.

Los cientos de archivos *.jar generados por un `mvn clean install` en la raíz de un proyecto complejo (como la persistencia, utilerías, DTOs, enums, librerías de terceros) no se despliegan sueltos; Maven ya los incluyó dentro de las carpetas:

`WEB-INF/lib` de los respectivos WAR, y éstos a su vez dentro de los EAR.

## WAS vs Tomcat/Jetty

1. **Apache Tomcat** **no soporta archivos EAR** de forma **nativa**. Tomcat y Jetty se quedan "cortos" en la categoría de **servlet containers = contenedores web**, no servidores de aplicaciones Java/Jakarta EE completos. Por tanto, para pruebas locales se deben ignorar los *.ear y solamente se deberán desplegar los *.war.

2. **WAS** provee una fábrica de implementaciones empresariales pesadas (JMS/MQ para colas de mensajes, EJBs o implementaciones específicas de JPA). Si el código utiliza dependencias de este tipo asumiendo que el servidor las proveerá, Tomcat lanzará errores de `ClassNotFoundException`.  
Fix: Agregar estas librerías de forma manual al directorio `lib/` de tu Tomcat local.

3. **Recursos JNDI**: las conexiones a bases de datos (DataSources) en WAS se configuran a nivel de consola y se exponen por JNDI (e.g. `jdbc/Application_DS`). En Tomcat se tendrá que replicar esta configuración creando un archivo `context.xml` en tu entorno local (o dentro del directorio `META-INF/` del proyecto) para que la persistencia logre conectarse a la(s) BD(s) del proyecto.

# Dos Escenarios de Despliegue

## 1. Escenario Híbrido:

Levantar la interfaz gráfica (JavaFX) desde Eclipse Oxygen en la computadora local, y esta interfaz se comunicaba por red con el backend (`ApplicationService.war`) ya desplegado en el WAS de un servidor de desarrollo RHEL/WAS.

En Eclipse Oxygen, crear un **"Run Configuration" (Java Application)** apuntando a la clase `Main` del proyecto de JavaFX.

La aplicación JavaFX usa librerías como `resteasy-client` o `httpClient` para hacer peticiones HTTP/REST a una URL configurada.

Así, el desarrollador no tiene que lidiar con levantar servidores pesados, configurar DataSources locales ni instalar un WebSphere en su Windows. Solamente compilar la vista, ejecutar al instante y **consumir los datos del servidor de desarrollo**.

## 2. 100% Local con Eclipse (menos probable, pero posible)

Si se requiere hacer cambios tanto en las pantallas como en las consultas a la BD, entonces se necesita levantar todo en el entorno local.

Eclipse Oxygen incluye la perspectiva (view) "Java EE" y la pestaña "Servers" (**Web Tools Platform - WTP**). Desde ahí, podemos agregar un servidor local, por ejemplo, un **WAS Liberty Profile (WLP)** (la versión ligera del WAS) o simplemente un **Tomcat configurado a mano**.

Dat click derecho al proyecto backend -> Run as -> Run on Server. Una vez que el backend responda en `localhost:8080`, apuntar la aplicación JavaFX a ese `localhost`.