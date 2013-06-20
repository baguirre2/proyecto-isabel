package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Programa extends utilerias.Conexion{
    private int id_programa, id_producto, minimoAlumnos, idProf, duracion;
    private float costo, precio;
    private String nombreProdm, fechaini, fechafin, fechalim, fechaIni, fechaFin, fechaLimite;
    private boolean publicado, confirmado, estado, programado;
    private String fec_ini, fec_lim_preinscrip, fec_fin, tipo_producto, nombre, fec_registro, objetivo, dirigido, requisitos, temario;
    private Modulo modulos [];    

  public Programa(int idProd , int minimo, boolean estado, String fechaIni,String fechaFin, String fechaLimite, boolean publicado ,boolean confirmado, boolean programado, float precio, int idProf) {
        this.id_programa = id_programa;
        this.id_producto = id_producto;
        this.precio = precio;
        this.fechaini = fechaini;
        this.fechalim = fechaLimite;
        this.fechaFin = fechafin;
        this.estado = estado;
        this.confirmado = confirmado;
        this.programado = programado;
        this.publicado = publicado;
        this.minimoAlumnos = minimo;
        this.id_producto = idProd;
        this.idProf = idProf;
    }
    
    public Programa(int id_programa, int id_producto, int duracion, float precio, String fec_ini, String fec_lim_preinscrip, String fec_fin, String nombre, String objetivo, String dirigido, String requisitos, String temario, Modulo modulos[]) {
        this.id_programa = id_programa;
        this.id_producto = id_producto;
        this.duracion = duracion;
        this.precio = precio;
        this.fec_ini = fec_ini;
        this.fec_lim_preinscrip = fec_lim_preinscrip;
        this.fec_fin = fec_fin;
        this.nombre = nombre;
        this.objetivo = objetivo;
        this.dirigido = dirigido;
        this.requisitos = requisitos;
        this.temario = temario;
        this.modulos = modulos;
    }    
    
    
  public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public String getDirigido() {
        return dirigido;
    }

    public void setDirigido(String dirigido) {
        this.dirigido = dirigido;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public String getFec_fin() {
        return fec_fin;
    }

    public void setFec_fin(String fec_fin) {
        this.fec_fin = fec_fin;
    }

    public String getFec_ini() {
        return fec_ini;
    }

    public void setFec_ini(String fec_ini) {
        this.fec_ini = fec_ini;
    }

    public String getFec_lim_preinscrip() {
        return fec_lim_preinscrip;
    }

    public void setFec_lim_preinscrip(String fec_lim_preinscrip) {
        this.fec_lim_preinscrip = fec_lim_preinscrip;
    }

    public String getFec_registro() {
        return fec_registro;
    }

    public void setFec_registro(String fec_registro) {
        this.fec_registro = fec_registro;
    }

    public Modulo[] getModulos() {
        return modulos;
    }

    public void setModulos(Modulo[] modulos) {
        this.modulos = modulos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getRequisitos() {
        return requisitos;
    }

    public void setRequisitos(String requisitos) {
        this.requisitos = requisitos;
    }

    public String getTemario() {
        return temario;
    }

    public void setTemario(String temario) {
        this.temario = temario;
    }

    public String getTipo_producto() {
        return tipo_producto;
    }

    public void setTipo_producto(String tipo_producto) {
        this.tipo_producto = tipo_producto;
    }    

  public boolean isConfirmado() {
        return confirmado;
    }

    public void setConfirmado(boolean confirmado) {
        this.confirmado = confirmado;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getFechaIni() {
        return fechaIni;
    }

    public void setFechaIni(String fechaIni) {
        this.fechaIni = fechaIni;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public String getFechaini() {
        return fechaini;
    }

    public void setFechaini(String fechaini) {
        this.fechaini = fechaini;
    }

    public String getFechalim() {
        return fechalim;
    }

    public void setFechalim(String fechalim) {
        this.fechalim = fechalim;
    }

    public int getIdProf() {
        return idProf;
    }

    public void setIdProf(int idProf) {
        this.idProf = idProf;
    }

    public String getNombreProdm() {
        return nombreProdm;
    }

    public void setNombreProdm(String nombreProdm) {
        this.nombreProdm = nombreProdm;
    }

    public boolean isProgramado() {
        return programado;
    }

    public void setProgramado(boolean programado) {
        this.programado = programado;
    }

    public boolean isPublicado() {
        return publicado;
    }

    public void setPublicado(boolean publicado) {
        this.publicado = publicado;
    }    
    
    
   public Programa recuperarDatos (int idPrograma) {
        
        String query = "SELECT prod_id_producto, prog_id_programa, prod_nombre, prod_duracion, prog_precio, prod_objetivo, prod_dirigido, "
                + "prod_requisitos, prod_temario, prog_fec_ini, prog_fec_fin, prog_fec_limite_preinscripcion "
                + "FROM producto, programa WHERE prog_id_programa = " + idPrograma + " AND prog_id_producto =  prod_id_producto";
        
        try {
            conectar();
            
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            while (rs.next()) {
                id_programa = Integer.parseInt((String) rs.getString("prog_id_programa"));
                nombre = (String) rs.getString("prod_nombre");
                duracion = Integer.parseInt((String) rs.getString("prod_duracion"));
                precio = Float.parseFloat((String) rs.getString("prog_precio"));
                objetivo = (String) rs.getString("prod_objetivo");
                dirigido = (String) rs.getString("prod_dirigido");
                requisitos = (String) rs.getString("prod_requisitos");
                temario = (String) rs.getString("prod_temario");
                fec_ini = (String) rs.getString("prog_fec_ini");
                fec_lim_preinscrip = (String) rs.getString("prog_fec_limite_preinscripcion");
                fec_fin = (String) rs.getString("prog_fec_fin");
            }
            
            query = "SELECT modulo.* FROM producto, programa, modulo "
                    + "WHERE prog_id_producto = prod_id_producto AND modu_id_producto = prod_id_producto AND "
                    + "prog_id_programa = " + idPrograma;
            
            st = conexion.createStatement();
            rs = st.executeQuery(query);
            
            ArrayList<Modulo> modulos = new ArrayList<Modulo>();
            
            while (rs.next()){
                modulos.add(new Modulo ((String) rs.getString("modu_duracion"), ((String) rs.getString("modu_objetivo")), ((String) rs.getString("modu_temario")), ((String) rs.getString("modu_nombre"))));
            }
            
            return new Programa(id_programa, 0, duracion, precio, fec_ini, fec_lim_preinscrip, fec_fin, nombre, 
                    objetivo, dirigido, requisitos, temario, modulos.toArray(new Modulo[modulos.size()]));
            
        } catch (Exception e) {
            return null;
        } finally {
            try {
                desconectar();
            } catch (Exception e) {
                return null;
            }
        }
    }

    public Programa() {
    }

    public int getMinimoAlumnos() {
        return minimoAlumnos;
    }

    public void setMinimoAlumnos(int minimoAlumnos) {
        this.minimoAlumnos = minimoAlumnos;
    }    
    
    public int getId_programa() {
        return id_programa;
    }

    public void setId_programa(int i_programa) {
        this.id_programa = i_programa;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    
    public boolean verificarPrograma(int idProducto, int idProfesor) {
        try {
            conectar();
            String query = "SELECT * FROM programa WHERE prog_id_producto = " + idProducto + " AND prog_id_profesor = " + idProfesor;
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
    
    public boolean agregarPrograma () {
            String fecha = "19-08-2001";
        try {
            conectar();
            String query = "INSERT INTO programa (prog_id_producto, prog_minimo_alumnos, prog_fec_registro,"
                            + "prog_estado,  prog_fec_ini,  prog_fec_limite_preinscripcion ,  prog_fec_fin,"
                            + "prog_publicado ,  prog_programado,  prog_confirmado, prog_precio,  prog_id_profesor) VALUES ("
                    + getId_producto() + "," + getMinimoAlumnos() + ",'" + fecha + "','" + isEstado() + "','"
                    + fecha + "','" + fecha + "','" + fecha + "','" + isPublicado() + "','" + isProgramado() + 
                    "','" + isConfirmado() + "'," + getPrecio() + "," + getIdProf() + ")";
            Statement st = conexion.createStatement();
            st.executeUpdate(query);
            return true;
        } catch (ClassNotFoundException cnfe) {
            return false;
        } catch(SQLException sqle) {
            return false;
        }
    }

    
    public boolean eliminarPrograma(int idProf, int idProd) {
            try  {
            conectar();
            Statement st = conexion.createStatement();
            if (verificarPrograma(idProd, idProf)) {
                String query = "DELETE FROM programa WHERE prog_id_producto = " + idProd + " AND prog_id_profesor = " + idProf;
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