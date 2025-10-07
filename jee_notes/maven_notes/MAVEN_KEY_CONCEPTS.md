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

## Tool Installation

1. git installation
2. JDK installation

File: ~/.bashrc
```bash
export JAVA_HOME=`/usr/libexec/java_home`
```

## Maven installation on MacOS X

http://maven.apache.org

- Extract **Binary Tarball** (tar.gz) archive on
/Development (for example)

```bash
cd /
mkdir Development
cd Development
tar -xvzf apache-maven-download.tar.gz
```

Create a symbolic link to the Maven directory:
```bash
ln -s apache-maven-folder maven
```

Create MAVEN_HOME environment variable:
```bash
export MAVEN_HOME=/Development/maven
export PATH="${PATH}:${MAVEN_HOME}/bin"
```

Verify Maven is installed:
```bash
mvn -version
```

The Maven instructor Jason Taylor likes to include the source
of **.bashrc** inside the **.bash_profile** file.

File: ~/.bash_profile
```bash
    if [ -f ~/.bashrc ] ; then
        source ~/.bashrc
    fi
```

### Bonus Notes: Windows, git bash, Notepad++

- Append ```C:\Program Files\Notepad++ ;``` to the ```PATH```
environment variable, separating paths with a semi-colon (;)

- Create alias for Notepad++ with git bash:
```bash
notepad++ ~/.bash_profile
# Add the following new line to .bash_profile
alias npp='notepad++ -multilnst -nosession'
```
\* Older versions of Git ( < 2.0) use the ```.bashrc``` file
instead.

- Make Notepad++ the default editor in git:
```bash
git config --global core.editor "notepad++ -multiInst -nosession"
# Test this:
git config --global -e
```

### JDK installation validation on Windows

1. Set environment variable ```JAVA_HOME``` to 

```C:\Program Files\Java\jdk1.8.0_102```

2. Append ```;%JAVA_HOME%\bin``` to the ```PATH```variable.

This allows any of the Java tools to be accessible from anywhere
in the Windows system.

### Maven Manual Installation on Windows

1. Download the binary *.zip file from <https://maven.apache.org/install.html>

2. Extract the file into ```C:\DevTools\``` 

3. Control Panel > Advanced System Settings > 
Environment Variables > New System Variable

Variable name = ```M2_HOME``` 
(named like this for historical reasons)

Variable Value = ```C:\DevTools\apache-maven-3.3.9 ```

4. Append ```;%M2_HOME%\bin``` to the ```PATH```variable.

EOF