# Jasper Reports

JasperReports is an open source Java reporting engine. JasperReports has the ability to deliver rich content onto the screen, to the printer, or into files, such as:
- PDF
- HTML
- XLS
- RTF
- ODT
- CSV
- TXT
- XML

JasperReports can be used when you need to:
- Add text or graphic data
- Generate watermarks
- Generate subreports

JasperReports is not a standalone tool. Rather, it is a class library embedded into Java applications by including it in the application's `CLASSPATH`.

All of the JasperReports' functionalities are gathered in a single JAR file: `jasperreports-x.x.x.jar`.
The downloadable \*.zip file contains the JasperReports *.jar file, along with the JasperReports source code (\*.jrxml) and dependent JARs.

# *.jrxml vs *.jasper Files

JasperReports uses a two-stage template pipeline:
```
[*.jrxml (source file)] ---> Compile (Jaspersoft / JavaAPI)
    ---> [*.jasper (Binary)]
```

1. **\*.jrxml** ---> XML specification that defines the report layout, SQL query, parameter declarations, fields, styles and bands (Title, Page Header, Detail, Summary, Page Footer).

2. **\*.jasper** ---> Compiled binary. It is a compiled, serialized Java object (`net.sf.jasperreports.engine.JasperReport`). This is the file that the Java application actually loads at **runtime** for maximum performance.

# Anatomy of a *.jrxml File

```xml
<?xml version="1.0" encoding="UTF-8"?>
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports"
              name="SampleReport"
              pageWidth="595" pageHeight="842"
              columnWidth="555" leftMargin="20" rightMargin="20"
              topMargin="20" bottomMargin="20"
              language="java">

    <!-- 1. Parameters passed from Spring JDBC / Java controller -->
    <parameter name="ACCOUNT_ID" class="java.lang.String"/>
    <parameter name="SUBREPORT_DIR" class="java.lang.String" isForPrompting="false">
        <defaultValueExpression><![CDATA["/AppA/reports/"]]></defaultValueExpression>
    </parameter>

    <!-- 2. Main Query (Informix SQL) -->
    <queryString><![CDATA[
        SELECT txn_id, txn_date, amount 
        FROM transactions 
        WHERE account_id = $P{ACCOUNT_ID}
    ]]></queryString>

    <!-- 3. Query Fields mapping -->
    <field name="txn_id" class="java.lang.Long"/>
    <field name="txn_date" class="java.sql.Timestamp"/>
    <field name="amount" class="java.math.BigDecimal"/>

    <!-- 4. Layout Bands -->
    <title>
        <band height="50">
            <staticText>
                <reportElement x="0" y="10" width="200" height="30"/>
                <text><![CDATA[Transaction Statement]]></text>
            </staticText>
        </band>
    </title>

    <detail>
        <band height="20">
            <textField>
                <reportElement x="0" y="0" width="100" height="20"/>
                <textFieldExpression><![CDATA[$F{txn_id}]]></textFieldExpression>
            </textField>
            <textField pattern="$#,##0.00">
                <reportElement x="100" y="0" width="100" height="20"/>
                <textFieldExpression><![CDATA[$F{amount}]]></textFieldExpression>
            </textField>
        </band>
    </detail>
</jasperReport>
```

## Tooling

- JasperSoft Studio (Eclipse-based GUI)
    - Saving a \*.jrxml file automatically compiles it into a \*.jasper file
    - DB2 Adapter: Driver = `com.ibm.db2.jcc.DB2Driver` (requires `db2jcc4.jar`)
    - Informix Adapter: Driver = `com.informix.jdbc.IfxDriver` (requires ifxjdbc.jar).

## Deploying Modified Reports

If the reports live outside the EAR/WAR installation on AIX, deploying new Jasper files does not require rebuilding or redeploying the WebSphere application unless report objects are cached permanently in JVM memory.

## Step-By-Step Deployment

1. **Compile locally** (jrxml --> jasper)
2. **Upload to AIX**: Transfer both *.jrxml and *.jasper to:  
    `/AppA/jasperReports/` using SCP or SFTP:

```bash
scp my_report.jrxml rrhg@aix-server:/AppA/reports/
scp my_report.jasper rrhg@aix-server:/AppA/reports/
```
3. **Verify Permissions**: ensure the WebSphere process user (e.g. `wasadmin`) has read permissions on AIX:

```bash
chmod 644 /AppA/reports/my_report.jasper
chown wasadmin:wasgroup /AppA/reports/my_report.jasper
```

4. **Manage Application Caching**
    - If code loads from the file stream dynamically on each request: the updated report takes effect immediately, without an application restart needed.
    - If code caches `JasperReport` instances in a Spring Singleton or Map: Restart the `AppA` enterprise application or server instance via the **WAS ND Administrative Console**:

WAS Console: Applications > Application Types > WebSphere Enterprise Applications > AppA > Restart.

# Monitoring Checklist

1. Inspect WAS Log Output.
2. `FileNotFoundException` Incorrect file system path reference
in the Java code.
3. `JRException: Class not found`: Version mismatch between local Jaspersoft Studio compiler and the `jasperreports.jar` inside the WAR/EAR library.
4. `SQLException`/`Connection Timeout`: the AIX server lacking firewall network access to the external IBM Informix DB host/port.
5. Version Mismatching (Library vs Jaspersoft Studio): check the exact version of `jasperreports-x.x.x.jar` inside `/AppA.war/WEB-INF/lib/`: if your WAR uses **JasperReports 6.18.0**, configure Jaspersoft Studio to compile with **Compatibility Mode = 6.18.0** (Window > Preferences > Jaspersoft Studio > Compatibility).
6. **AIX Font Rendering Issues**: If a report uses `Arial` and exports to PDF on AIX, the text will align incorrectly or fail with missing font exceptions.  
<u>Solution:</u> Package custom fonts as a **Jasper Font Extension JAR** and include inside `/AppA.war/WEB-INF/lib/`, or use standard logical fonts like SansSerif, Serif, Monospaced.

7. **Dual Database Parameter Handling**: When queries span both local DB2 (for parameters) and external Informix (for core transactional data):
    - Verify whether Spring JDBC passes pre-fetched DB2 parameters as a Java `Map<String, Objects>` into `JasperFillManager.fillReport(jasperReport, parametersMap, InformixConnection)`.
    - Ensure parameter data types match across databases (e.g. DB2 `VARCHAR` vs Informix `CHAR` or handling date conversions explicitly in Java).

# iReport vs Crystal Reports

**\*Warning**: Crystal Reports is not compatible with our current structure, since it uses the **SAP Crystal Reports Engine** and produces a propietary binary file format (\*.rpt).

Thus, you must go with **iReport** (legacy version of Jaspersoft Studio). iReport Designer was officially retired by Jaspersoft at version 5.6.0 back in 2014.

## iReport requires Java 7 or 8

iReport desktop UI is built on the old NetBeans RCP framework and will fail to open or immediately crash if executed with JDK 11, 17 or newer. Fix = Edit `<iReport-Install-Dir>/etc/ireport.conf` and explicitly point `jdkhome` to a local Java 8 installation:
```properties
jdkhome="/path/to/jdk1.8.0_361"
```

## Match the Compiled Version to your Application JAR

If these versions don't match, WebSphere will throw runtime deserialization or `Unknown tag / attribute` exceptions when loading the report.

1. Check the version of 
```
/.../AppA.war/WEB-INF/lib/jasperreports-x.x.x.jar
```
2. Set iReport / Jaspersoft Studio compatibility or source version in the settings: `Tools > Options > iReport > General > Compatibility` to match that version.

<u>Question</u>: how must we configure our DB connections to Informix and DB2?

# Configuring DB Connections

iReport establishes its own direct JDBC connection to preview reports, it requires this information to be entered explicitly into its own datasource configuration.

1. `host`
2. `port`
3. `DB name` 
4. `username`
5. `password`

### Extract the Connection Info from Aqua Data Studio

1. Right-click the DB2 connection in Aqua Data Studio's `Servers` tree -> `Properties`.

### Retrieve or Reset the Password

- **ADS Connection Files**: Aqua Data Studio stores connection definitions in configuration files (typically under `%USERPROFILE%\.datastudio\connections` on Windows). Inside these XML files, passwords are generally encrypted or obfuscated. 

- **Recommended approach**: request the DB2 application password from your DBA/lead, or ask for a personal read-only database account for development and report testing. 

- **Check Local Config Files**.

# Setting up JDBC Connections in iReport

To run queries inside iReport, you must register the DB drivers and create a Data Source.

## Step 1: Add JDBC Drivers to iReport Classpath:

Before creating a connection, iReports needs the `*.jar` driver diles:

- In iReport, go to **Tools** > **Options** > **iReport** tab > **Classpath** sub-tab.
- Click "**Add JAR**" and select your JDBC driver files:
    - `db2jcc4.jar` or `db2jcc.jar`
    - `ifxjdbc.jar`
- Click "**Apply**" and "**OK**".

## Step 2: Create the DB Connection

- Click the **Report Datasources** icon in the main toolbar (or go to **Data** > **Report** > **Datasources**)
- Click **New**.
- Select **Database JDBC connection** and click **Next**.
- Configure parameters.

### DB2 JDBC

`jdbc:db2://<HOST>:<PORT>/<DATABASE>`

### Informix JDBC

`jdbc:informix-sqli://<HOST>:<PORT>/<DATABASE_NAME>:INFORMIXSERVER=<SERVER_NAME>`