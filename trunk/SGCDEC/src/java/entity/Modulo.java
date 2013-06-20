package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Mariana
 */
public class Modulo extends utilerias.Conexion {
    private int idModulo, idCurso;
    private String idProducto, duracion, nombre, objetivo, temario;
    
    public Modulo (int idModulo, String idProducto, String duracion, String objetivo, String temario, String nombre){
        this.idModulo = idModulo;
        this.idProducto = idProducto;
        this.duracion = duracion;
        this.idProducto = idProducto;
        this.objetivo = objetivo;
        this.temario = temario;
        this.nombre = nombre;
    }
    
    
     /* Autor: Lalo
     * Descripción: Lo uso para crear modulos de los cuales solo mostrare información relevante al inscrito
     * Fecha: 13 - 05 - 2012
     */
    public Modulo (String duracion, String objetivo, String temario, String nombre){
        this.duracion = duracion;
        this.objetivo = objetivo;
        this.temario = temario;
        this.nombre = nombre;
    }    
    
    public Modulo (){
    }
    
    public Modulo (String nombre, int idCurso){
        this.nombre = nombre;
        this.idCurso = idCurso;
    }
    
    public Modulo[] buscarModulosProf(int idProfesor){
        try {
            conectar ();
            String query = "SELECT modu_nombre, curs_id_curso FROM modulo, curso, profesor "
                    + "WHERE curs_id_modulo = modu_id_modulo  AND curs_id_profesor =  prof_id_profesor "
                    + "AND curs_id_profesor = " + idProfesor;
            
            Statement st = conexion.createStatement();//Orden a la bd
            ResultSet rs = st.executeQuery(query); //Ejecución del query y retorna una consulya  ResultSet.--Modificar, insertar y borrar executeUpdate

            ArrayList<Modulo> modulos = new ArrayList<Modulo>();
            
            while (rs.next()){
                nombre = (String) rs.getString("modu_nombre");
                idCurso = Integer.parseInt( rs.getString("curs_id_curso"));
                modulos.add(new Modulo (nombre, idCurso));
                return modulos.toArray(new Modulo[modulos.size()]);
            }
            
        } catch (Exception e) {
            return null;
        }finally {
            try {
                desconectar();
            } catch (SQLException ex) {
            }
        }
        return null;
    }
    
    public Modulo[] buscarModulosPrograma(int idProfesor){
        try {
            conectar ();
            String query = "SELECT modu_nombre, curs_id_curso FROM modulo, curso, profesor "
                    + "WHERE curs_id_modulo = modu_id_modulo  AND curs_id_profesor =  prof_id_profesor "
                    + "AND curs_id_profesor = " + idProfesor;
            
            Statement st = conexion.createStatement();//Orden a la bd
            ResultSet rs = st.executeQuery(query); //Ejecución del query y retorna una consulya  ResultSet.--Modificar, insertar y borrar executeUpdate

            ArrayList<Modulo> modulos = new ArrayList<Modulo>();
            
            while (rs.next()){
                nombre = (String) rs.getString("modu_nombre");
                idCurso = Integer.parseInt( rs.getString("curs_id_curso"));
                modulos.add(new Modulo (nombre, idCurso));
                return modulos.toArray(new Modulo[modulos.size()]);
            }
            
        } catch (Exception e) {
            return null;
        }finally {
            try {
                desconectar();
            } catch (SQLException ex) {
            }
        }
        return null;
    }    

    /**
     * @return the idModulo
     */
    public int getIdModulo() {
        return idModulo;
    }

    /**
     * @param idModulo the idModulo to set
     */
    public void setIdModulo(int idModulo) {
        this.idModulo = idModulo;
    }

    /**
     * @return the idProducto
     */
    public String getIdProducto() {
        return idProducto;
    }

    /**
     * @param idProducto the idProducto to set
     */
    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * @return the duracion
     */
    public String getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the objetivo
     */
    public String getObjetivo() {
        return objetivo;
    }

    /**
     * @param objetivo the objetivo to set
     */
    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    /**
     * @return the temario
     */
    public String getTemario() {
        return temario;
    }

    /**
     * @param temario the temario to set
     */
    public void setTemario(String temario) {
        this.temario = temario;
    }

    /**
     * @return the idCurso
     */
    public int getIdCurso() {
        return idCurso;
    }

    /**
     * @param idCurso the idCurso to set
     */
    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }
}