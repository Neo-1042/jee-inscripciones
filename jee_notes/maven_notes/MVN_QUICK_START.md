# Maven Quick Start

Example project:

<https://github.com/programming-clinic>

Please, go to my other GitHub repository:

<https://github.com/Neo-1042/mvn-quick-start>

maven-quick-start-course/minimal/pom.xml

# Maven: Building Blocks

Three main groups:

1. Goals ('task' for Ant)
2. Phases (these map to goals: compile, test,  etc.)
3. Lifecycles > Collection of related phases 
    (compile, test, package, package, install, deploy)

Goal example: packet a jar file
Plugin + goal name = compiler:compile

## Jar Lifecycle Example

1. compile
2. test
3. package
4. install
5. deploy

If you choose 4 (install), all previous phases will be
executed first.

Different kind of applications will have different
lifecycles
- Enterprise Application vs simple JAR app.

A pom.xml like the one in ```maven-quick-start-course/minimal/pom.xml```
is all you need to build a Maven project.

Try running ```mvn compile``` from the root of the project.

# Standard Directory Template

Maven will look for a specific folder structure.

For example, Maven will look for your main source code in:
\<project root \>/src/main/java

Example Standard Directory Template
```
<project root>
    |__ src
      |__ main
        |__ java
        |__ webapp
        |__ resources # Configuration files
      |__ test
        |__ java
        |__ resources
```

\* You may override the POM.xml file if you really need 
a different directory structure.

