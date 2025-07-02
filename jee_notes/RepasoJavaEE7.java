public class RepasoJavaEE7 {
    public static void main(String[] args) {
        System.out.println("Hello, Java EE 7");
    }

    // CAP 6 PONIENDO TODO JUNTO
    /*
    CAP 1 COMENZANDO CON JEE
    WEBAPP = {servlets, JSP, HTML, *.class, *.jar}

    User <-> View <-> Controller <-> Facade <-> DAOs <-> Database

    CAP 2 = COMENZANDO CON JEE. Diseño de aplicación por capas

        Backend > se encarga de procesar los datos que han sido ingresados por el usuario,
        y en función a éstos, responder. 
        Facade > punto de contacto entre el front y el resto del back, donde reside la lógica
        de negocio (MODEL)
        Capa de acceso a los datos > DAOs = Data Access Object: encapsulan las operaciones y
        transacciones SQL. Se tiene un DAO por cada tabla.

    CAP 2. CAPA DE PRESENTACIÓN

        MVC = Model View Controller
        Model = lógica de negocio (backend)
        View = vistas/pantallas (JSP)
        Controller = Servlets que tienen interacción directa con el facade.

        VIEW <-> CONTROLLER <-> facade <-> MODEL

    CAP 3. JNDI E INYECCIÓN DE DEPENDENCIAS.

        JNDI = Java Naming and Directory Interface
        CDI  = Contexts and Dependency Injection (@)

        El JEE container utiliza al servicio JNDI como un repositorio para publicar allí los
        recursos que debe poner a disposición de los componentes. Nosotros como programadores
        podemos acceder a estos recursos por medio de la JNDI API. Asimismo, podemos tener acceso
        a estos recursos a través de la inyección de dependencias que implementa CDI mediante el
        uso de annotations como @Resource.

    CAP 4. CAPA DEL NEGOCIO. Enterprise JavaBeans (EJB)

        Enterprise JavaBeans > Componentes cuya responsabilidad es encapsular y resolver la
        lógica de negocio de la aplicación. El container JEE administra su ciclo de vida y les
        provee servicios de transacciones y seguridad.
        
        Tenemos [ampliar sobre Beans]


    CAP 5. CAPA DE DATOS. JPA = Java Persistence API 

    */


    /*
    Creando la estructrura de carpetas del proyecto:
    /Projects/jee-inscripciones/src/main/java/com/rafa/...
        /{dao, datasource, dto, modelo, seguridad, service, servlets}


    */

}