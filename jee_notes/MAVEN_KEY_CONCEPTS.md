# Maven Key Concepts


Maven is a build tool mainly developed for Java.

- Dependency Management
- Artifact Repository System
- Plugins provide the most functionality. Plugins are also
dependencies that need to be downloaded to be used.
- Conventions/best practices
- Standard Directory Structure:
Maven expects to find your code under:

```app_root/src/main/java/```

- POM = Project Object Model (xml file)

The pom.xml file defines the project's **metadata** and
the project's **Maven coordinates**, which is how Maven
uniquely identifies components:

\< Group_Name, Artifact_Name, Version \>

The POM file also defines other settings like the Java JRE
version or the final name of the artifact generated.

- Super POM (Parent POM that defines default settings that
will be automatically 'inherited' by all Maven projects)

You declare your dependencies (libraries) in your pom.xml
file, and Maven takes care of downloading the appropriate
files from the **mvn repository**, including transitive
dependencies.

Local Maven Repository (.m2 hidden directory)
