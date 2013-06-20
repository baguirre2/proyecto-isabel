package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Curso extends utilerias.Conexion {

    private int idPrograma, duracion;
    private float precio;
    private String nombre, fechaLimPreInscr, fechaInicio;

    public Curso(int idPrograma, float precio, String nombre, int duracion, String fechaPreInscr, String fechaInicio) {
        this.idPrograma = idPrograma;
        this.precio = precio;
        this.nombre = nombre;
        this.duracion = duracion;
        this.fechaLimPreInscr = fechaPreInscr;
        this.fechaInicio = fechaInicio;
    }
    public int getDuracion() {
        return duracion;
    }
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }
    public String getFechaInicio() {
        return fechaInicio;
    }
    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public String getFechaLimPreInscr() {
        return fechaLimPreInscr;
    }
    public void setFechaLimPreInscr(String fechaLimPreInscr) {
        this.fechaLimPreInscr = fechaLimPreInscr;
    }
    public int getIdPrograma() {
        return idPrograma;
    }
    public void setIdPrograma(int idPrograma) {
        this.idPrograma = idPrograma;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public float getPrecio() {
        return precio;
    }
    public void setPrecio(float precio) {
        this.precio = precio;
    }
    public Curso () {
    }
    public Curso[] buscarCursosDeAlumno(int idAlumno) {
        return null;
    }
    public Curso recuperarDatos(int idCurso) {
        return null;
    }
    public Curso[] cursosPublicados() {
        return null;
    }
    public Curso[] buscarAlgunos(String nombre, float precio, int duracion) {
        String query = "SELECT prod_nombre, prog_precio, prog_fec_ini, prog_fec_limite_preinscripcion, prod_duracion, prog_id_programa "
                    + "FROM PROGRAMA, PRODUCTO WHERE prod_id_producto = prog_id_producto AND prog_publicado = TRUE";
        
        if (nombre == null && precio != 0 && duracion != 0) {
            query = query + " AND (prog_precio <= " + precio + " AND prod_duracion <= " + duracion + ")";
        
        } else if (nombre == null && precio == 0 && duracion != 0) {
            query = query + " AND (prod_duracion <= " + duracion + ")";
        
        } else if (nombre != null && precio == 0 && duracion != 0) {
            query = query + " AND (prod_nombre ILIKE '%" + nombre + "%' AND prod_duracion <= " + duracion + ")";
        
        } else if (nombre != null && precio != 0 && duracion == 0) {
            query = query + " AND (prod_nombre ILIKE '%" + nombre + "%' AND prog_precio <= " + precio + ")";
        
        } else if (nombre != null && precio == 0 && duracion == 0) {
            query = query + " AND (prod_nombre ILIKE '%" + nombre + "%')";
            
        } else if (nombre == null && precio != 0 && duracion == 0) {
            query = query + " AND (prog_precio <= " + precio + ")";
        
        } else if (nombre != null && precio != 0 && duracion != 0) {
            query = query + " AND (prod_nombre ILIKE '%" + nombre + "%' AND prod_duracion <= " + duracion + " AND prog_precio <= " + precio + ")";
        }
        try {
            conectar();

            Statement st = conexion.createStatement();//Orden a la bd
            ResultSet rs = st.executeQuery(query); //Ejecución del query y retorna una consulya  ResultSet.--Modificar, insertar y borrar executeUpdate

            ArrayList<Curso> cursos = new ArrayList<Curso>();

            while (rs.next()) {
                idPrograma = Integer.parseInt((String) rs.getString("prog_id_programa"));
                duracion = Integer.parseInt((String) rs.getString("prod_duracion"));
                precio = Float.parseFloat((String) rs.getString("prog_precio"));
                nombre = (String) rs.getString("prod_nombre");
                fechaInicio = (String) rs.getString("prog_fec_ini");
                fechaLimPreInscr = (String) rs.getString("prog_fec_limite_preinscripcion");

                cursos.add(new Curso(idPrograma, precio, nombre, duracion, fechaLimPreInscr, fechaInicio)); //cREAR UN nuevo curso y agregarlo al arrayList
            }
            return cursos.toArray(new Curso[cursos.size()]);//Conv array a Arreglo
        } catch (Exception e) {
            return null;
        } finally {
            try {
                desconectar();
            } catch (SQLException ex) {
                return null;
            }
        }
    }
}