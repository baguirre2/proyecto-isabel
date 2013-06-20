package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Alumno extends Persona {
    
    boolean diplomado, conferencias, seminarios;
    int idAlumno;
    
    public Alumno(String nombre, String apePat, String apeMat, String RFC, boolean diplomado, boolean conferencias, boolean seminarios) {
        super(nombre, apePat, apeMat, RFC);
        this.diplomado = diplomado;
        this.conferencias = conferencias;
        this.seminarios = seminarios;
    }

    public Alumno(int idAlumno, String nombre, String apePat, String apeMat, String RFC) {
        super(nombre, apePat, apeMat, RFC);
        this.idAlumno = idAlumno;
    }
    
    public void setIDAlumno(int alm) {
        idAlumno = alm;
    }
    
    public boolean agregarAlumno () {
        try  {
            conectar();
            Statement st = conexion.createStatement();
            String query = "INSERT INTO alumno (alum_id_persona, alum_diplomados, alum_seminarios, alum_conferencias) VALUES";
                       
            if (!verificarPersona(getRFC())) {
                if (agregarPersona()) {
                    setidPersona();
                    query = query + " (" + getidPersona() + ",'" + getDiplomado() + "','" + getSeminario() + "','" + getConferencias() + "')";
                    st.executeUpdate(query);
                    return true;
                } else { 
                    return false;
                }
            } else {
                setidPersona();
                query = query + " (" + getidPersona() + ",'" + getDiplomado() + "','" + getSeminario() + "','" + getConferencias() + "')";
                st.executeUpdate(query);
                return true;
            }
        } catch (SQLException sqle) {
            return false;
        } catch(ClassNotFoundException cnfe) {
            return false;
        }
    }
    
    public Alumno () {
        super ();
    }
    
    public int recuperarIDAlumno() {
        try {
            conectar();
            Statement st = conexion.createStatement();
            String query = "SELECT * FROM alumno, persona WHERE persona.pers_id_persona = alumno.alum_id_persona";
            ResultSet rs = st.executeQuery(query);
            rs.next();
            return Integer.parseInt((String) rs.getString("alum_alumno_id"));
        } catch(SQLException sqle) {
            return 0;
        } catch(ClassNotFoundException cnfe) {
            return 0;
        } 
    }
    
    public boolean getConferencias() {
        return conferencias;
    }
    
    public boolean getDiplomado () {
        return diplomado;
    }
    
    public boolean getSeminario() {
        return seminarios;
    }
    
    public int getIdAlumno () {
        return idAlumno;
    }
    
    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }
    
    public Alumno iniciarSesion(int idPersonaRol) {

        if (idPersonaRol > 0) {
            try {

                conectar();

                String query = "SELECT alum_alumno_id, pers_id_persona, pers_nombre, pers_aPaterno, pers_aMaterno, pers_RFC FROM persona, alumno, persona_rol "
                        + "WHERE alum_id_persona = pers_id_persona AND pero_id_persona = pers_id_persona AND pero_id_persona_rol = " + idPersonaRol;

                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if (rs.next()) {
                    return new Alumno( 
                        Integer.parseInt(rs.getString("alum_alumno_id")), rs.getString("pers_nombre"),
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

    public Alumno[] getAlumno(String nombre) {
 
        try {
            conectar();
            String query = "SELECT * FROM alumno JOIN persona ON (persona.pers_id_persona = alumno.alum_id_persona) WHERE "
                    + "persona.pers_nombre ILIKE '%" + nombre + "%' OR persona.pers_apaterno ILIKE '%" + nombre  
                    + "%' OR persona.pers_amaterno ILIKE '%" + nombre + "%' OR persona.pers_rfc ILIKE '%" + nombre + "%'";
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            ArrayList<Alumno> alumnos = new ArrayList<Alumno>();
            rs.next();
            rs.getString("pers_nombre");
                do {
                    String nom, apep, apem, rfc;
                    Boolean seminarios, conferencias, diplomados;
                    nom =(String) rs.getString("pers_nombre");
                    apep = (String) rs.getString("pers_apaterno");
                    apem = (String) rs.getString("pers_amaterno");
                    rfc = (String) rs.getString("pers_rfc");
                    seminarios = (boolean) rs.getBoolean("alum_diplomados");
                    conferencias = (boolean) rs.getBoolean("alum_conferencias");
                    diplomados = (boolean) rs.getBoolean("alum_diplomados");
                    alumnos.add(new Alumno(nom, apep , apem, rfc, diplomados, conferencias, seminarios));
                } while (rs.next());
                return alumnos.toArray(new Alumno[alumnos.size()]);
        } catch (SQLException sqle) {
            return null;
        } catch (ClassNotFoundException cnfe) {
            return null;
        }
    }
    
    public boolean eliminarAlumno(String rfc) {
            try  {
            conectar();
            Statement st = conexion.createStatement();
            if (verificarPersona(rfc)) {
                this.setRFC(rfc);
                this.setidPersona();
                String query = "DELETE FROM alumno WHERE alum_id_persona = " + this.getidPersona();
                st.executeUpdate(query);
                return true;
            } else {
                return false;
            }
        } catch (SQLException sqle) {
            return false;
        } catch(ClassNotFoundException cnfe) {
            return false;
        }
    }
    
    public boolean verificarAlumno(String rfc) {
        try {
            conectar();
            this.setRFC(rfc);
            this.setidPersona();
            String query = "SELECT * FROM alumno WHERE alum_id_persona = " + this.getidPersona();            
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String temp =  rs.getString(1);
            return true;
        } catch(SQLException sqle) {
            return false;
        } catch (ClassNotFoundException cnfe) {
            return false;
        }
    }    

}