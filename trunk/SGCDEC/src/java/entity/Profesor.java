/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Benjamin Aguirre G Clase que hereda de Persona y permite registrar u
 * obtener Profesores.
 */
public class Profesor extends Persona {

    private String nTrabajador;
    private boolean estado;
    private int idProfesor;

    public Profesor(String nTrabajador, boolean estado, String nombre, String aPaterno, String aMaterno, String RFC) {
        super(nombre, aPaterno, aMaterno, RFC);
        this.nTrabajador = nTrabajador;
        this.estado = estado;
    }

    public Profesor() {
    }

    public Profesor(int idProfesor, String nombre, String apePat, String apeMat, String RFC) {
        super(nombre, apePat, apeMat, RFC);
        this.idProfesor = idProfesor;
    }

    public boolean agregarProfesor() {
        try {
            conectar();
            Statement st = conexion.createStatement();
            String query = "INSERT INTO profesor (prof_id_persona, prof_estado, prof_num_trab_unam) VALUES (";
            setidPersona();
            if (!verificarPersona(getRFC())) {
                if (agregarPersona()) {
                    query = query + getidPersona() + ",'" + getEstado() + "','" + getNtrabajador() + "')";
                    st.executeUpdate(query);
                } else {
                    return false;
                }
            } else {
                query = query + getidPersona() + ",'" + getEstado() + "','" + getNtrabajador() + "')";
                st.execute(query);
            }
        } catch (Exception e) {
            return false;
        }
        try {
            desconectar();
            return true;
        } catch (SQLException sqle) {
            return false;
        }
    }

    public boolean getEstado() {
        return estado;
    }

    public String getNtrabajador() {
        return nTrabajador;
    }

    public int getIdProfesor() {
        return idProfesor;
    }
    
    public void setIdProfesor(String rfc) {
        idProfesor = recuperarIDProfesor(rfc);
    }

    public void setIdProfesor(int idProfesor) {
        this.idProfesor = idProfesor;
    }

    public Profesor[] getProfesor(String nombre) {

        try {
            conectar();
            String query = "SELECT * FROM profesona JOIN persona ON (persona.pers_id_persona = profesor.prof_id_persona) WHERE "
                    + "persona.pers_nombre ILIKE '%" + nombre + "%' OR persona.pers_apaterno ILIKE '%" + nombre
                    + "%' OR persona.pers_amaterno ILIKE '%" + nombre + "%' OR persona.pers_rfc ILIKE '%" + nombre + "%'";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            ArrayList<Profesor> profesores = new ArrayList<Profesor>();
            rs.next();
            rs.getString("pers_nombre");
            if (!rs.wasNull()) {
                do {
                    String nom, apep, apem, rfc, nTrab;
                    Boolean est;
                    int id;
                    nom = (String) rs.getString("pers_nombre");
                    apep = (String) rs.getString("pers_apaterno");
                    apem = (String) rs.getString("pers_amaterno");
                    rfc = (String) rs.getString("pers_rfc");
                    id = Integer.parseInt((String) rs.getString("pers_id_persona"));
                    est = Boolean.parseBoolean((String) rs.getString("prof_estado"));
                    nTrab = (String) rs.getString("prof_num_trab_unam");
                    profesores.add(new Profesor(nTrab, est, nom, apep, apem, rfc));
                } while (rs.next());
                return profesores.toArray(new Profesor[profesores.size()]);
            } else {
                return null;
            }

        } catch (SQLException sqle) {
            return null;
        } catch (ClassNotFoundException cnfe) {
            return null;
        }
    }

    public int recuperarIDProfesor (String rfc) {
        try {
            conectar();
            this.setRFC(rfc);
            this.setidPersona();
            String query = "SELECT * FROM profesor WHERE prof_id_persona = " + this.getidPersona();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            return (Integer) rs.getInt(1);            
            
        } catch(SQLException sqle) {
            return -1;
        } catch(ClassNotFoundException cnfe) {
            return 1;
        }
    }
    
    
    public boolean verificarProfesor(String rfc) {
        try {
            conectar();
            this.setRFC(rfc);
            this.setidPersona();
            String query = "SELECT * FROM profesor WHERE prof_id_persona = " + this.getidPersona();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String temp = rs.getString(1);
            return true;
        } catch (SQLException sqle) {
            return false;
        } catch (ClassNotFoundException cnfe) {
            return false;
        }
    }

    public boolean eliminarProfesor(String rfc) {
        try {
            conectar();
            Statement st = conexion.createStatement();
            if (verificarPersona(rfc)) {
                this.setRFC(rfc);
                this.setidPersona();
                String query = "DELETE FROM profesor WHERE prof_id_persona = " + this.getidPersona();
                st.executeUpdate(query);
                return true;
            } else {
                return false;
            }
        } catch (SQLException sqle) {
            return false;
        } catch (ClassNotFoundException cnfe) {
            return false;
        }
    }

    public Profesor iniciarSesion(int idPersonaRol) {

        if (idPersonaRol > 0) {
            try {

                conectar();

                String query = "SELECT prof_id_profesor, pers_id_persona, pers_nombre, pers_aPaterno, pers_aMaterno, pers_RFC "
                        + "FROM persona, profesor, persona_rol WHERE prof_id_persona = pers_id_persona "
                        + "AND pero_id_persona = pers_id_persona AND pero_id_persona_rol = " + idPersonaRol;

                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(query);

                if (rs.next()) {
                    return new Profesor(
                            Integer.parseInt(rs.getString("prof_id_profesor")), rs.getString("pers_nombre"),
                            rs.getString("pers_aPaterno"), rs.getString("pers_aMaterno"), rs.getString("pers_RFC"));
                } else {
                    return null;
                }

            } catch (Exception e) {
                return null;
            } finally {
                try {
                    desconectar();
                } catch (Exception e) {
                }
            }
        } else {
            return null;
        }
    }
}