# Bienvenido a mi proyecto 'jee-inscripciones' con Java EE 7
## RRHG
### 2025/09/03

```java
  System.out.println("STEM RULES!");
```

La presente web app tiene la siguiente configuración:

- Front end web con estructura MVC
- Única página JSP para implementar todas las vistas
- Servlets (controllers) para controlar el proceso web e
invocar al FACADE
- FACADE implementado como un Session Bean, cuyos métodos
transaccionales invocarán a los DAO.
- DAOs implementados también como Session Beans, cuyos
métodos usarán JPA para resolver los accesos a las tablas
de la base de datos.