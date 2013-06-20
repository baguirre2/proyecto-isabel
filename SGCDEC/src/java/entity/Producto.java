/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Benjamin Aguirre G
 */
public class Producto extends utilerias.Conexion {
    
    private String nombre, objetivo, dirigido, requisitos, temario;
    private int modulos, idProducto, duracion;
    private Date fechaReg;

    public Producto () {
        
    }
    
    public void setFechaReg() {
        this.fechaReg = new Date();
    }

    public Date getFechaReg() {
        return fechaReg;
    }

    public void setDirigido(String dirigido) {
        this.dirigido = dirigido;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public void setIdProducto() {
        this.idProducto = recuperarID();
    }

    public void setModulos(int modulos) {
        this.modulos = modulos;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public void setTemario(String temario) {
        this.temario = temario;
    }

    public String getDirigido() {
        return dirigido;
    }

    public int getDuracion() {
        return duracion;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getModulos() {
        return modulos;
    }

    public String getNombre() {
        return nombre;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public String getTemario() {
        return temario;
    }
    
    public void setOldDate(Date d) {
        fechaReg = d;
    }
   
    public Producto(String nombre, String dirigido, String objetivo, String requisitos, String temario, int duracion, int modulos) {
        this.duracion = duracion;
        this.dirigido = dirigido;
        this.objetivo = objetivo;
        this.nombre = nombre;
        this.requisitos = requisitos;
        this.modulos = modulos;
        this.temario = temario;
    }
    
    public Producto(String nombre, String dirigido, String objetivo, String requisitos, String temario, int duracion, int modulos, Date fechaReg) {
        this(nombre, dirigido, objetivo, requisitos, temario, duracion, modulos);
        this.fechaReg = fechaReg;
    }
    
    public boolean agregarProducto() {
       try {
           conectar();
            String query_persona = "INSERT INTO producto (prod_nombre, prod_objetivo, prod_dirigido, prod_requisitos, prod_num_modulos, prod_temario, prod_duracion) ";
            query_persona = query_persona + "VALUES ('" + getNombre() + "','" + getObjetivo() + "','" + getDirigido() + "','" + getRequisitos() + "',"
                    + getModulos() + ",'"+ getTemario() + "'," + getDuracion()+ ")";
            Statement st = conexion.createStatement();
            st.executeUpdate(query_persona);
            setIdProducto();
            return true;
        } catch(SQLException sqle) {
            return false;
        } catch (ClassNotFoundException cnfe) {
            return false;
        }
    
    }
    
    public Producto[] obtenerProductos (String n_buscado) {
        ArrayList<Producto> productos = new ArrayList<Producto>();
        try {
            String query = "SELECT * FROM producto WHERE prod_nombre ILIKE '%" + n_buscado + "%'";
            conectar();
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            rs.next();
            rs.getString(1);
           do {
                nombre = (String) rs.getString("prod_nombre");
                objetivo = (String) rs.getString("prod_objetivo");
                dirigido = (String) rs.getString("prod_dirigido");
                requisitos = (String) rs.getString("prod_requisitos");
                temario = (String) rs.getString("prod_temario");
                modulos =(Integer) rs.getInt("prod_num_modulos");
                duracion = (Integer) rs.getInt("prod_duracion");
                fechaReg = (Date) rs.getDate("prod_fec_registro");
                productos.add(new Producto(nombre, dirigido, objetivo, requisitos,  temario, duracion, modulos, fechaReg));
            }while (rs.next());
           return productos.toArray(new Producto[productos.size()]);
        }catch (Exception e) {
            return null;
        }
    }
    
    public boolean verificarProducto(String nombre) {
        try {
            conectar();
            String query = "SELECT * FROM producto WHERE prod_nombre = '" + nombre + "'";
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
    
    public int  recuperarID(){
            try  {
                conectar();
                Statement st = conexion.createStatement();
                String query = "SELECT * FROM producto WHERE prod_nombre = '" + getNombre() + "'";
                ResultSet rs = st.executeQuery(query);
                rs.next();
                int idPers = (Integer) rs.getInt(1);
                return idPers;
            } catch (Exception e) {
                return -1;
            }
        }
    
    public boolean eliminarProducto(String nombre) {
            try  {
            conectar();
            Statement st = conexion.createStatement();
            if (verificarProducto(nombre)) {
                String query = "DELETE FROM producto WHERE prod_nombre = '" + nombre + "'";
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

}
