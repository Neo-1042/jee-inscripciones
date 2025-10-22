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

For instance, the **jar lifecycle** includes the goals:
compile, test and package.

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
is <u>all</u> you need to build a Maven project.

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

\* You may override the POM.xml file if you **really** 
need a different directory structure.

### Important Note About Empty Folders and GIT

By default, **git** does not keep track of empty folders.
To circumvent this, you might want to create hidden dummy
files inside your empty folders.

For example:

```bash
touch src/main/java/.gitkeep
touch src/main/resources/.gitkeep
touch src/test/java/.gitkeep 
touch src/test/resources/.gitkeep
```

# Integrate your Java main() source code
## \<project_root\>/src/main/**java**

Java Class = clinic.programming.training.Application.java

```bash
cd src/main/java/
mkdir -p clinic/programming/training
cd clinic/programming/training
touch Application.java
code Application.java # 'mate' or whatever code editor

# Go back to <root project>
cd ../../..
cd ../../..
pwd # /Users/rafael1642/GIT/Notes/mvn-quick-start
```

# Run (mvn package) the project

```bash
mvn package
```

## Bare Minimum Java Application + Maven

```
# This is the root of where our Java source code should START
cd /Users/rafael1642/GIT/Notes/mvn-quick-start/src/main/java
mvn-quick-start % tree
.
├── pom.xml
├── README.md
├── src
│   ├── main
│   │   ├── java
│   │   │   └── clinic
│   │   │       └── programming
│   │   │           └── training
│   │   │               └── Application.java
│   │   └── resources
│   └── test
│       ├── java
│       └── resources
└── target
    ├── classes
    │   └── clinic
    │       └── programming
    │           └── training
    │               └── Application.class
    └── test-classes
```

# Exploring the target/ directory

## Running app with 'java -cp \<jar\> \<class\>'

```bash
cd /Users/rafael1642/GIT/Notes/mvn-quick-start/target
ls -ltr
# maven-quick-start-1.0.jar
# -cp = CLASSPATH
# java -cp <jar_file> <class_to_run>
java -cp maven-quick-start-1.0.jar clinic.programming.training.Application
```

# Removing the Previous Build Results



```bash
# 'clean' is a phase that is part of the CLEAN lifecycle
# Different from the JAR lifecycle
mvn clean
```