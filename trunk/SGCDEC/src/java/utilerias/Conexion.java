package utilerias;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public Connection conexion;

    protected void conectar () throws SQLException, ClassNotFoundException {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SGCDEC", "benjamin", "Pumas123");
    }

    protected void desconectar () throws SQLException {
            conexion.close();            
    } 
    public static void main(String[] args) {
        try {
            new Conexion().conectar();
            System.out.println("HECHO");
        } catch (Exception e) {
            System.out.println("FALLO");
        }
    }    
    /*public static void main(String[] args) {
        if ("Mufa1sa".matches("[A-z]*")) {
            System.out.println("VERDADERO");
            
        } else {
            System.out.println("FALSO");
        }
    }*/
    /*public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Conexion conn = new Conexion();

        Class.forName("org.postgresql.Driver");
        conn.conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/SGCDEC", "USUARIO", "AQUI VA LA CONTRASEÑA");

        System.out.println("HECHO");

                String sql = "SELECT prod_nombre, prog_precio, prog_fec_ini, prog_fec_limite_preinscripcion, prod_duracion, prog_id_programa "
                        + "FROM PROGRAMA, PRODUCTO WHERE prod_id_producto=prog_id_producto AND "
                        + "(prod_nombre LIKE '%Fiscal%' OR prog_precio <= 4000 OR prod_duracion <= 12)";

                Statement st = conn.conexion.createStatement();
                ResultSet rs = st.executeQuery(sql);

                ArrayList cursos = new ArrayList();
                
                int eCont = 0;
                
                while (rs.next()) {
                    eCont++;
                }
                
                System.out.println(eCont);
                
                while (rs.next()) {
                    int idPrograma = Integer.parseInt((String) rs.getString("prog_id_programa"));
                    int duracion = Integer.parseInt((String) rs.getString("prod_duracion"));
                    float precio = Float.parseFloat((String) rs.getString("prod_duracion"));
                    String nombre = (String) rs.getString("prod_nombre");
                    String fechaInicio = (String) rs.getString("prog_fec_ini");
                    String fechaLimPreInscr = (String) rs.getString("prog_fec_limite_preinscripcion");

                    cursos.add(new Curso(idPrograma, precio, nombre, duracion, fechaLimPreInscr, fechaInicio));
                    System.out.println(nombre);
                }
    }*/
}
