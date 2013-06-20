package entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Inscripcion extends utilerias.Conexion {
    
    int idInscripcion;
    String nombrePrograma, nombreAlumno, nombreProfesor, fecPreinsc, fecInscr;
    float calificacion;
    int folio;

    public Inscripcion() {
    }

    public Inscripcion(String nombrePrograma, String nombreProfesor, float calificacion) {
        this.nombrePrograma = nombrePrograma;
        this.calificacion = calificacion;
        this.nombreProfesor = nombreProfesor;
    }
    
    //Constructor hecho para mostrar la lista de alumnos inscritos o pre-inscritos a x grupo
    public Inscripcion(int idInscrp, String nombreAlumno, String fecha, int folio) {
        this.nombreAlumno = nombreAlumno;
        this.fecPreinsc = fecha;
        this.fecInscr = fecha;
        this.idInscripcion = idInscrp;
        this.folio = folio;
    }
    
    public Inscripcion(int idInscripcion, String nombreAlumno, float calificacion, String nombrePrograma) {
        this.idInscripcion = idInscripcion;
        this.nombrePrograma = nombrePrograma;
        this.calificacion = calificacion;
        this.nombreAlumno = nombreAlumno;
    }
    
    public boolean modificarCalificacion (int idInscripcion, float calificacion) {
        int eResul = 0;

        try {

            conectar();
            String query_persona = "UPDATE inscripcione SET insc_calificacion = " + calificacion + " WHERE insc_id_incripcione = " + idInscripcion;

            Statement st = conexion.createStatement();
            
            eResul = st.executeUpdate(query_persona);

            if (eResul > 0) return true;
            
        } catch (Exception e) {
            return false;
        }
        try {
            desconectar();
            return (eResul > 0);
        } catch (SQLException sqle) {
            return false;
        }        
    }

    public boolean preInscribir(int idAlumno, int idCurso, int idFormaDifusion) {
        return false;
    }

    public Inscripcion[] buscarAlumnosInscritos(int idCurso) {
        try {
            conectar();
            
            //Query que recupera la lista de Alumno INSCRITOS (los que aun no tienen fecha de INSCRIPCION) al grupo
            String query = "SELECT pers_nombre, pers_apaterno, pers_amaterno, insc_fec_inscripcion, insc_folio FROM persona, alumno, inscripcion, inscripcion_curso, curso, programa "
                    + "WHERE prog_id_programa=curs_id_programa AND curs_id_curso=incu_id_curso AND insc_id_inscripcion=incu_inscripcion_id "
                    + "AND insc_alumno_id=alum_alumno_id AND alum_id_persona=pers_id_persona AND insc_fec_inscripcion!='1900-01-01' AND prog_id_programa=" + idCurso;
            
            //Creamos el Statement y lo mandamos a la BD
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            //Creamos un ArrayList de tipo Inscripción, que almacenara el nombre del alumno y la fecha de Preinscripción
            ArrayList<Inscripcion> alumnos = new ArrayList<Inscripcion>();

            while (rs.next()) {
                nombreAlumno = rs.getString("pers_nombre") + " " + rs.getString("pers_apaterno") + " " + rs.getString("pers_amaterno");
                fecInscr = rs.getString("insc_fec_inscripcion");
                folio = rs.getInt("insc_folio");

                alumnos.add(new Inscripcion(0, nombreAlumno, fecInscr, folio)); //cREAR UNa inscripcion y agregarlo al arrayList
            }
            
            return alumnos.toArray(new Inscripcion[alumnos.size()]);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                desconectar();
            } catch (Exception e) {
            }
        }
    }
    
    public Inscripcion[] buscarAlumnosPreincritos(int idCurso) {
        try {
            conectar();
            
            //Query que recupera la lista de Alumno PREINSCRITOS (los que aun no tienen fecha de INSCRIPCION) al grupo
            String query = "SELECT insc_id_inscripcion, pers_nombre, pers_apaterno, pers_amaterno, insc_fec_preinscripcion FROM persona, alumno, inscripcion, inscripcion_curso, curso, programa "
                    + "WHERE prog_id_programa=curs_id_programa AND curs_id_curso=incu_id_curso AND insc_id_inscripcion=incu_inscripcion_id "
                    + "AND insc_alumno_id=alum_alumno_id AND alum_id_persona=pers_id_persona AND insc_fec_inscripcion='1900-01-01' AND prog_id_programa=" + idCurso;
            
            //Creamos el Statement y lo mandamos a la BD
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            //Creamos un ArrayList de tipo Inscripción, que almacenara el nombre del alumno y la fecha de Preinscripción
            ArrayList<Inscripcion> alumnos = new ArrayList<Inscripcion>();

            while (rs.next()) {
                nombreAlumno = rs.getString("pers_nombre") + " " + rs.getString("pers_apaterno") + " " + rs.getString("pers_amaterno");
                fecPreinsc = rs.getString("insc_fec_preinscripcion");
                idInscripcion = rs.getInt("insc_id_inscripcion");

                alumnos.add(new Inscripcion(idInscripcion, nombreAlumno, fecPreinsc, 0)); //cREAR UNa inscripcion y agregarlo al arrayList
            }
            
            return alumnos.toArray(new Inscripcion[alumnos.size()]);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                desconectar();
            } catch (Exception e) {
            }
        }
    }

    public boolean validarInscripcion(int idInscrpcion, int folio) {
        int eResul = 0;

        try {

            conectar();
            String query_persona = "UPDATE inscripcion SET insc_folio=" + folio + ", insc_fec_inscripcion=now() WHERE insc_id_inscripcion=" + idInscrpcion;

            Statement st = conexion.createStatement();
            
            eResul = st.executeUpdate(query_persona);

            if (eResul > 0) return true;
            
        } catch (Exception e) {
            return false;
        }
        try {
            desconectar();
            return (eResul > 0);
        } catch (SQLException sqle) {
            return false;
        }
    }

    public boolean inscribir(int idPrograma, int idAlumno) {
        
        int eResul = 0;

        try {

            conectar();
            String query_persona = "INSERT INTO inscripcione(insc_id_alumno, insc_id_programa, insc_calificacion) VALUES (" + idAlumno + ", " + idPrograma + ", -1)";

            Statement st = conexion.createStatement();
            
            eResul = st.executeUpdate(query_persona);

            if (eResul > 0) return true;
            
        } catch (Exception e) {
            return false;
        }
        try {
            desconectar();
            return (eResul > 0);
        } catch (SQLException sqle) {
            return false;
        }
    }
    
    public Inscripcion[] listarIncripcionDeAlumno (int idAlumno) {
        try {
            conectar();
            
            String query = "SELECT pers_nombre, pers_apaterno, pers_amaterno, prod_nombre, insc_calificacion "
                    + "FROM programa, producto, profesor, inscripcione, persona "
                    + "WHERE insc_id_programa = prog_id_programa AND prog_id_profesor = prof_id_profesor AND prof_id_persona = pers_id_persona "
                    + "AND prog_id_producto = prod_id_producto AND insc_id_alumno = " + idAlumno;
            
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            ArrayList<Inscripcion> incripcines = new ArrayList<Inscripcion>();

            while (rs.next()) {
                nombreProfesor = rs.getString("pers_nombre") + " " + rs.getString("pers_apaterno") + " " + rs.getString("pers_amaterno");
                nombrePrograma = rs.getString("prod_nombre");
                calificacion = Float.parseFloat(rs.getString("insc_calificacion"));

                incripcines.add(new Inscripcion(nombrePrograma, nombreProfesor, calificacion)); //cREAR UNa nuevoa incripcion y agregarlo al arrayList
            }
            
            return incripcines.toArray(new Inscripcion[incripcines.size()]);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                desconectar();
            } catch (Exception e) {
            }
        }
    } 
    
    public Inscripcion[] listarIncripcionDeProfesor (int idProfesor) {
        try {
            conectar();
            
            String query = "SELECT prod_nombre, insc_id_incripcione, pers_nombre, pers_apaterno, pers_amaterno, prod_nombre, insc_calificacion "
                    + "FROM programa, producto, alumno, inscripcione, persona "
                    + "WHERE insc_id_programa = prog_id_programa AND insc_id_alumno = alum_alumno_id "
                    + "AND alum_id_persona = pers_id_persona AND prog_id_producto = prod_id_producto AND prog_id_profesor = " + idProfesor;
            
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            ArrayList<Inscripcion> incripcines = new ArrayList<Inscripcion>();

            while (rs.next()) {
                nombreAlumno = rs.getString("pers_nombre") + " " + rs.getString("pers_apaterno") + " " + rs.getString("pers_amaterno");
                calificacion = Float.parseFloat(rs.getString("insc_calificacion"));
                nombrePrograma = rs.getString("prod_nombre");

                incripcines.add(new Inscripcion(Integer.parseInt(rs.getString("insc_id_incripcione")), nombreAlumno, calificacion, nombrePrograma)); //cREAR UNa inscripcion y agregarlo al arrayList
            }
            
            return incripcines.toArray(new Inscripcion[incripcines.size()]);
        } catch (Exception e) {
            return null;
        } finally {
            try {
                desconectar();
            } catch (Exception e) {
            }
        }
    }

    public float getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public void setNombreAlumno(String nombreAlumno) {
        this.nombreAlumno = nombreAlumno;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = nombreProfesor;
    }

    public String getNombrePrograma() {
        return nombrePrograma;
    }

    public void setNombrePrograma(String nombrePrograma) {
        this.nombrePrograma = nombrePrograma;
    }

    public String getFecInscr() {
        return fecInscr;
    }

    public void setFecInscr(String fecInscr) {
        this.fecInscr = fecInscr;
    }

    public String getFecPreinsc() {
        return fecPreinsc;
    }

    public void setFecPreinsc(String fecPreinsc) {
        this.fecPreinsc = fecPreinsc;
    }

    public int getFolio() {
        return folio;
    }

    public void setFolio(int folio) {
        this.folio = folio;
    }
}