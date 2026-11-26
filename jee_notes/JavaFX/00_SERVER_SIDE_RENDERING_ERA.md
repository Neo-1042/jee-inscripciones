# Server-Side Rendering (SSR) Era

Java enterprise systems originally delivered user interfaces over the web before rich client-side and desktop GUI approaches became standard.

## JavaServer Pages (JSP) and SSR

In server-side rendering (SSR), the web or application server processes an incoming HTTP request, retrieves or computes data, merges that data into a template, and returns a complete HTML document to the browser. JSP is a Java EE (now "Jakarta EE") technology that lets developers author **HTML markup interwoven with dynamic Java expressions**, Expression Language (EL) and **tag libraries** (such as **JSTL**).

Under the hood, a **JSP file** is not interpreted line-by-line like a static text file; the servlet container translates the *.jsp page into a Java servlet class, compiles it, and executes it to stream HTML back to the client.

Over time, architectures moved away from embedding raw Java scriptlets inside JSPs toward **MVC patterns** where **servlets** or **controllers** *prepare model data and forward to JSP views for presentation*.

![image info](../images/SSR_Workflow.png)