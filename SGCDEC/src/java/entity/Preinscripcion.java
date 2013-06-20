/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Statement;
import java.sql.ResultSet;


/**
 *
 * @author Benjamin Aguirre Garcia
 */
public class Preinscripcion extends utilerias.Conexion {
    
    private int idModulo, idProfesor, idCurso, idPrograma, idProducto, idPeriodo, idAlumno, idsProductos[], idsModulos[], idsCursos[];
    private String nombreModulo, nombreProfesor, nombreProducto;    
    public boolean creado;
    private String nombresModulos[], nombresProductos[], data[];
    
    
    public Preinscripcion() {
        this.idModulo = -1;
        this.idProfesor = -1;
        this.idCurso = -1;
        this.idPrograma = -1;
        this.idProducto = -1;
        this.idPeriodo = -1;
        this.idAlumno = -1;
        this.nombreModulo = null;
        this.nombreProfesor = null;
        this.nombreProducto = null;
        this.creado = false;
    }

    public void setIds(int prod, int modu, int prof, int curs) {
        idCurso = curs;
        idProfesor = prof;
        idProducto = prod;
        idModulo = modu;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }
    
    public void getProductos() {
        String query = "SELECT prog_id_programa, prod_nombre FROM producto AS pro"
                + " JOIN programa AS p ON (p.prog_id_producto=pro.prod_id_producto);";
        try {
            conectar();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            String nombres[] = new String[40];
            int ids[] = new int[40];
            for (int i = 0; rs.next(); i++){
                nombres[i] = rs.getString(2);
                ids[i] = rs.getInt(1);
            }
            nombresProductos = nombres;
            idsProductos = ids;
            desconectar();
        } catch (Exception e){
        }
    }

    public int getidCurso (int idProducto) {
        String query = "SELECT curs_id_curso FROM curso WHERE curs_id_programa=" + idProducto + ";";
        try {
            conectar();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            desconectar();
            return rs.getInt(1);
        } catch (Exception e){
            return 0;
        }
    }    
    
    public void getLastData(int idPrograma) {
        
        String query = "SELECT cu.curs_id_curso, per.pers_nombre, per.pers_apaterno, per.pers_amaterno, pe.peri_fec_ini, pe.peri_fec_fin "
                + " FROM curso AS cu JOIN profesor AS pro ON (cu.curs_id_profesor=pro.prof_id_profesor)"
                + " JOIN persona AS per ON (pro.prof_id_persona=per.pers_id_persona)"
                + " JOIN periodo AS pe ON (cu.curs_id_periodo=pe.peri_id_periodo)"
                + " WHERE cu.curs_id_programa=" + idPrograma + ";";
        try {
            
            conectar();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            String data[] = new String[40];
            int ids[] = new int[40];
            for (int i = 0; rs.next(); i++){
                 ids[i] = rs.getInt(1);
                 String temp[] = rs.getString(5).split("-");
                 String temp2[] = rs.getString(6).split("-");
                 data[i] = rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4) + "(" + temp[0] + "-" + temp2[0] + ")";
            }
            this.data = data;
            idsCursos = ids;
            desconectar();
        } catch (Exception e){
        }
    }      
    
    
    public String[] getNombresModulos() {
        return nombresModulos;
    }

    public int[] getidsModulos() {
        return idsModulos;
    }    
    
    public int[] getidsProductos() {
        return idsProductos;
    }
    
    public String[] getNombresProductos() {
        return nombresProductos;
    }
    
    public String[] getData() {
        return data;
    }
    
    public int[] getidsCursos() {
        return idsCursos;
    }
    
    public String getNombreProducto(int idProducto){
        String query = "SELECT prod_nombre FROM producto AS pro JOIN programa AS p ON (pro.prod_id_producto=p.prog_id_producto) WHERE p.prog_id_programa=" + idProducto + ";";
        try {
            conectar();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            desconectar();
            rs.next();
            return rs.getString(1);
        } catch (Exception e){
            return null;
        }
    }
    
    public String getNombreModulo(int idModulo){
        String query = "SELECT modu_nombre FROM modulo WHERE modu_id_modulo=" + idModulo + ";";
        
        try {
            conectar();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            desconectar();
            rs.next();
            return rs.getString(1);
        } catch (Exception e){
            return null;
        }        
    }
    
    public String getNombreProfesor (int idCurso) {
        String query = "SELECT per.pers_nombre, per.pers_apaterno, per.pers_amaterno FROM "
                + " curso AS cu JOIN profesor AS pro ON (cu.curs_id_profesor=pro.prof_id_profesor)"
                + " JOIN persona AS per ON (pro.prof_id_persona=per.pers_id_persona)"
                + "WHERE cu.curs_id_curso=" + idCurso + ";";
                
        try {
            conectar();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            desconectar();
            rs.next();
            return rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3);
        } catch (Exception e){
            return null;
        }    
    }
    
    public String getPeriodoConcat (int idCurso) {
        String query = "SELECT pe.peri_fec_ini, pe.peri_fec_fin FROM "
                + "curso AS cu JOIN periodo AS pe ON (cu.curs_id_periodo=pe.peri_id_periodo)"
                + "WHERE cu.curs_id_curso=" + idCurso + ";";
        try {
            conectar();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            desconectar();
            rs.next();
            return rs.getString(1) + " - " + rs.getString(2);
        } catch (Exception e){
            return null;
        }        
    }
    
    public boolean preInscribir (int idCurso, int idPersona) {
        String query = "SELECT a.alum_alumno_id FROM persona AS p JOIN  alumno AS a ON (a.alum_id_persona=p.pers_id_persona) where p.pers_id_persona=" + idPersona + ";";
        try {
            conectar();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            int idAlumno = rs.getInt(1);
            String insert = "INSERT INTO inscripcion (insc_alumno_id, insc_fec_preinscripcion) VALUES ("+ idAlumno + ",'"+ new java.util.Date() +"') RETURNING insc_id_inscripcion;";
            System.out.println(insert);
            rs = st.executeQuery(insert);
            rs.next();
            String insert2 = "INSERT INTO inscripcion_curso (incu_inscripcion_id, incu_id_curso) VALUES ("+ rs.getInt(1) + ","+ idCurso +");";
            System.out.println(insert2);            
            st.executeUpdate(insert2);
            desconectar();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
