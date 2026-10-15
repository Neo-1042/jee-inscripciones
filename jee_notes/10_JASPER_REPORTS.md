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