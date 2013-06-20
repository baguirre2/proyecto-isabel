package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Persona extends utilerias.Conexion {

    private String nombre, apePat, apeMat, RFC;
    private int idPersona, idPersonaRol;
    

    public Persona(String nombre, String apePat, String apeMat, String RFC) {
        this.nombre = nombre;
        this.apePat = apePat;
        this.apeMat = apeMat;
        this.RFC = RFC;
    }
    
    public Persona(int idPersona, String nombre, String apePat, String apeMat, String RFC) {
        this.nombre = nombre;
        this.apePat = apePat;
        this.apeMat = apeMat;
        this.RFC = RFC;
        this.idPersona = idPersona;
    }    
    
    public Persona(int idPersona, String nombre, String apePat, String apeMat, int idRol) {
        this.nombre = nombre;
        this.apePat = apePat;
        this.apeMat = apeMat;
        this.idPersona = idPersona;
        this.idPersonaRol = idRol;
    }    
    
    public Persona () {
        
    }
    
    /* Autor: Lalo
     * Fecha Creación: 2 / 06 / 2013
     * Objetivo: se le manda el login y password de cualquier usuario y retorna el ID de esa cuenta (Tabla pero_id_persona_rol)
     * Parametros: 2 cadenas el login (nombre de usuario) y password
     * Retorno: Retorna el id del ese usario (pero_id_persona_rol) o cero '0', si es que no existe la cuenta, no coinciden el login
     *          y password con ningun renglon de la tabla.
     */
    public  int recuperarIDPersonaRol (String login, String pass) {
        
        try {
            conectar();
            
            String query = "SELECT pero_id_persona_rol FROM persona_rol WHERE pero_login = '" + login + "' AND pero_password = '" + pass + "'";
            
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()) {
                return Integer.parseInt(rs.getString("pero_id_persona_rol"));
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        } finally {
            try {
                desconectar();
            } catch (Exception e) {
            }
        }
    }
    
    /* Autor: Lalo
     * Fecha Creación: 4 / 06 / 2013
     * Objetivo: se le manda el login y password de cualquier usuario y retorna la persna (objeto) al que pertencen ese login y password
     * Parametros: 2 cadenas el login (nombre de usuario) y password
     * Retorno: Retorna una referencia de tipo Persona pero el objeto es Alumno o Persona, o retorno null, si es que no existe la cuenta, o no coinciden el login
     *          y password con ningun renglon de la tabla.
     */
    public Persona recuperarPersonaUsandoSuRol (String login, String pass) {
        
        try {
            conectar();
            
            String query = "SELECT pero_id_persona_rol, pero_id_rol FROM persona_rol WHERE pero_login = '" + login + "' AND pero_password = '" + pass + "'";
            
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            if (rs.next()) {
                switch (Integer.parseInt(rs.getString("pero_id_rol"))) {
                    case 17: return new entity.Alumno().iniciarSesion(Integer.parseInt(rs.getString("pero_id_persona_rol")));
                        
                    case 21: return new entity.Profesor().iniciarSesion(Integer.parseInt(rs.getString("pero_id_persona_rol")));
                        
                    case 20: return new entity.Persona().iniciarSesion(Integer.parseInt(rs.getString("pero_id_persona_rol")));
                    
                    default:
                        return null;
                }
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
    }
    
    public Persona iniciarSesion(int idPersonaRol) {

        if (idPersonaRol > 0) {
            try {

                conectar();

                String query = "SELECT pers_id_persona, pers_nombre, pers_aPaterno, pers_aMaterno, pers_RFC FROM persona, persona_rol "
                        + "WHERE pero_id_persona = pers_id_persona AND pero_id_persona_rol = " + idPersonaRol;

                Statement st = conexion.createStatement();
                ResultSet rs = st.executeQuery(query);
                
                if (rs.next()) {
                    return new Persona(Integer.parseInt(rs.getString("pers_id_persona")), rs.getString("pers_nombre"), rs.getString("pers_aPaterno"), 
                            rs.getString("pers_aMaterno"), rs.getString("pers_RFC"));
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

    public String getRFC() {
        return RFC;
    }

    public void setRFC(String RFC) {
        this.RFC = RFC;
    }

    public String getApeMat() {
        return apeMat;
    }

    public void setApeMat(String apeMat) {
        this.apeMat = apeMat;
    }

    public String getApePat() {
        return apePat;
    }
    
    public int getidPersonaRol () {
        return idPersonaRol;
    }

    public void setApePat(String apePat) {
        this.apePat = apePat;
    }

    public String getNombre() {
        return nombre;
    }
    
    public void setidPersona () {
        idPersona = recuperarID();
    }
    
    public int getidPersona () {
        return idPersona;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public boolean agregarPersona () {
        try {
            String query_persona = "INSERT INTO persona (pers_nombre, pers_apaterno, pers_amaterno, pers_rfc) ";
            query_persona = query_persona + "VALUES ('" + getNombre() + "','" + getApePat() + "','" + getApeMat() + "','" + getRFC() + "')";
            Statement st = conexion.createStatement();
            st.executeUpdate(query_persona);
            setidPersona();
            return true;
        } catch(SQLException sqle) {
            return false;
        } finally {
            try {
                desconectar();
            } catch (SQLException sqle) {
                
            }
        }
    }
    
    public int  recuperarID(){
            try  {
                conectar();
                Statement st = conexion.createStatement();
                String query = "SELECT * FROM persona WHERE pers_rfc = '" + getRFC() + "'";
                ResultSet rs = st.executeQuery(query);
                rs.next();
                int idPers = Integer.parseInt((String) rs.getString("pers_id_persona"));
                return idPers;
            } catch (Exception e) {
                return -1;
            }
        }
     
    public Persona[] buscarUsuarios (String n_buscado, int id){
    
        ArrayList<Persona> personas = new ArrayList<Persona>();
        try {
            String query;
            
            if (id == -1 ) {
            query = "SELECT * FROM persona"
                    + " WHERE pers_nombre ILIKE '"+ n_buscado + "%' OR pers_apaterno ILIKE '" + n_buscado
                    + "%' OR pers_amaterno ILIKE '" + n_buscado + "%' OR pers_rfc ILIKE '" + n_buscado + "%';";
            } else {
            
            query = "SELECT * FROM persona AS p JOIN persona_rol AS pr ON (p.pers_id_persona=pr.pero_id_persona) "
                    + "WHERE (p.pers_nombre ILIKE '"+ n_buscado + "%' OR p.pers_apaterno ILIKE '" + n_buscado
                    + "%' OR p.pers_amaterno ILIKE '" + n_buscado + "%' OR p.pers_rfc ILIKE '" + n_buscado + "%') AND pr.pero_id_rol="+ id +";";                
            }

            conectar();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                nombre = (String) rs.getString("pers_nombre");
                apePat = (String) rs.getString("pers_apaterno");
                apeMat = (String) rs.getString("pers_amaterno");
                RFC = (String) rs.getString("pers_rfc");
                if (id == -1) {
                    idPersona = (int) rs.getInt("pers_id_persona");
                } else {
                    idPersona = (int) rs.getInt("pero_id_persona_rol");
                }
                personas.add(new Persona(idPersona, nombre, apePat, apeMat, RFC));
            }
        }catch (Exception e) {
            personas = null;
        }
        try {
            desconectar();    
        } catch(SQLException sqle){
            
        }
        if (personas != null) {
            return personas.toArray(new Persona[personas.size()]);
        } else {
            return null;
        }
    }
    
    public boolean verificarPersona(String RFC) {
        try {
            conectar();
            String query = "SELECT * FROM persona WHERE pers_rfc = '" + RFC + "'";
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

    public boolean eliminarUsuario (int idPersonaRol) {
        try  {
            conectar();
            Statement st = conexion.createStatement();
            String query = "DELETE FROM persona_rol WHERE pero_id_persona_rol = '" + idPersonaRol + "'";
            st.executeUpdate(query);
            return true; 
        } catch (SQLException sqle) {
            return false;
        } catch(ClassNotFoundException cnfe) {
            return false;
        }
    }
    
    public Persona buscarPersona (String login, String pass) {
        try {
            conectar();
            

            String query = "SELECT pero_id_rol, pers_nombre, pers_apaterno, pers_amaterno, pers_id_persona FROM persona AS p"
                    + " JOIN persona_rol AS prol ON (p.pers_id_persona=prol.pero_id_persona)"
                    + " WHERE pero_login = '" + login + "' AND pero_password = '" + pass + "'";
            
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                return new Persona(rs.getInt(5), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(1));
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public boolean agregarUsuario(String login, String pass, int idPersona, int idRol) {
        String query = "INSERT INTO persona_rol (pero_id_persona, pero_id_rol, pero_login, pero_password)"
                + " VALUES ("+ idPersona +","+idRol+",'"+login+"','"+pass+"');";
        System.out.println(query);
        try {
            conectar();
            Statement st = conexion.createStatement();
            st.executeUpdate(query);
            return true;
        }catch (Exception e) {
            return false;
        }
    }
    
    public boolean verificarLogin(String login) {
        
        String query = "SELECT pero_login FROM persona_rol WHERE pero_id_login='"+ login +"';";
        
        try {
            conectar();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            if (rs.next()) {
                if (rs.getString(1).equals(login)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

}