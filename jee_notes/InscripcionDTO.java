public class InscripcionDTO {
    
    private int idInsc;

    private String nombre;

    private String telefono;

    private int idCurso;

    private int idFormaPago;

    public int getIdInsc() {
        return idInsc;
    }

    public void setIdInsc(int idInsc) {
        this.idInsc = idInsc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdFormaPago() {
        return idFormaPago;
    }

    public void setIdFormaPago(int idFormaPago) {
        this.idFormaPago = idFormaPago;
    }

    public InscripcionDTO(int idInsc, String nombre, String telefono, int idCurso, int idFormaPago) {
        this.idInsc = idInsc;
        this.nombre = nombre;
        this.telefono = telefono;
        this.idCurso = idCurso;
        this.idFormaPago = idFormaPago;
    }

    @Override
    public String toString() {
        return "InscripcionDTO [idInsc=" + idInsc + ", nombre=" + nombre + ", telefono=" + telefono + ", idCurso="
                + idCurso + ", idFormaPago=" + idFormaPago + ", hashCode()=" + hashCode() + "]";
    }

    
}
