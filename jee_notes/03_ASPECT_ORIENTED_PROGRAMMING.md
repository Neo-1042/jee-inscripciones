# AOP = Aspect Oriented Programming

Se le llama _Aspect Oriented Programming_ a la técnica en la
 cual se utilizan _interceptors_ para
intervenir en la llamada a un método con el fin de modificar
los valores de los parámetros o manipular su valor de retorno.

Ejemplo: @Interceptors

```java
public interface HolaMundo {

    String saludar(String nombre);
}
```

```java
@Stateless
public class HolaMundoBean implements HolaMundo {

    @Override
    @Interceptors(HolaMundoInterceptor.class)
    public String saludar(String nombre) {
        return "Hola, " + nombre;
    }
}
```

Con la annotation ```@AroundInvoke``` indicaremos cuál de los
métodos de "HolaMundoInterceptor" será el responsable de
procesar la intercepción del método ```saludar()```

```java
public class HolaMundoInterceptor {

    @AroundInvoke
    public Object interceptaSaludar(InvocationContext ic)
        throws Exception {
            // Obtener los parámetros del método que será interceptado
            Object parametros[] = ic.getParameters();

            // Se toma el primer elemento
            String nombre = (String) parametros[0];

            // Manipular el parámetro interceptado
            nombre = nombre.toUpperCase();

            // Guardamos el parámetro actualizado al arreglo
            parametros[0] = nombre;

            // Ejecuto y obtengo el valor original
            String valorDeRetorno = (String)ic.proceed();

            // Se retorna el valor modificado:
            
        }
}
```