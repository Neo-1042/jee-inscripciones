import java.sql.Connection;

import javax.sql.DataSource;

public class InscripcionDAO {
    

    public int insertar(InscripcionDTO dto) {

        // SQL to get sequence
        StringBuilder s1 = new StringBuilder();

        s1.append("SELECT MAX(id_inscripcion) + 1");
        s1.append(" FROM tbl_inscripcion");

        String sql1 = s1.toString();

        // SQL to INSERT
        StringBuilder s2 = new StringBuilder();

        s2.append("INSERT INTO tbl_inscripcion ( ");
        s2.append("     id_insc");
        s2.append("     ,nombre");
        s2.append("     ,telefono");
        s2.append("     ,id_curso");
        s2.append("     ,id_forma_pago) ");
        s2.append("VALUES (?,?,?,?,?) ");
        
        String sql2 = s2.toString();
        
        // try with resources
        try (Connection con = UConnection.getConnection()
            ; PreparedStatement ps1 = con.prepareStatement(sql1)
            ; PreparedStatement ps2 = con.prepareStatement(sql2)
        )
    }
}
