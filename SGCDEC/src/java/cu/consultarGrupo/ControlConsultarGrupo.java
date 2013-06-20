package cu.consultarGrupo;

import entity.Curso;
import entity.Inscripcion;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControlConsultarGrupo extends HttpServlet {

    private String nombre;
    private String precio;
    private String duracion;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        int idRol;
        HttpSession login = request.getSession();
        if (login.getAttribute("idRol") != null) {        
            try {
                try {
                    //Si idPrograma NO existe pasamos al siguiente TRY
                    int idPrograma = Integer.parseInt((String) request.getParameter("idPrograma"));
                    //out.println(recuperarDatosPrograma(idPrograma));// + "</td>" + "</table><input type='submit' value='Inscribirse' onClick=\"Enviar('CtlInscrip', 'Respuesta_Hello', 'frmInscripcion');\"/></form>"

                    HttpSession sesion = request.getSession(true);
                    if (sesion.getAttribute("idAlum") != null) {
                        out.println("</td>" + "<input type='submit' value='Inscribirse' onClick=\"Enviar('ControlInscripcion', 'Respuesta_Hello', 'datosCurso');\"/>");
                    }

                } catch (Exception e) {

                    //Bloque para inscribir a x alumno y recuperar a los alumnso inscritos a X curso
                    try {
                        //Recuperamos el ID del Alumno
                        int idInscrip = Integer.parseInt((String) request.getParameter("idInscr"));

                        try {

                            int folio = Integer.parseInt((String) request.getParameter("folio"));

                            //Si idInscrip es Diferente de 0, incribimos al alumnos, de lo contrario solo mostramos la lista de Alumnos
                            if (folio != 0) {

                                if (new entity.Inscripcion().validarInscripcion(idInscrip, folio)) {
                                    out.println("<h4>Alumno Inscrito</h4>");
                                } else {
                                    out.println("<h4>No se pudo inscribir el alumno</h4>");
                                }

                            } else {
                                out.println("<h4>Ingrese el folio</h4>");
                            }

                        } catch (Exception excp) {
                            out.println("<h4>El Número de folio tiene un formato erroneo</h4>");
                        }

                        //Si idCurso NO existe pasamos al siguiente bloque (lo que vienen inmediatamente despues del catch)
                        int idCurso = Integer.parseInt((String) request.getParameter("idCurso"));
                        out.println(buscarAlumnos(idCurso));

                    } catch (Exception x) {

                        //Bloque para recuperar a los alumnso inscritos a X curso
                        try {

                            //Si idCurso NO existe pasamos al siguiente bloque (lo que vienen inmediatamente despues del catch)
                            int idCurso = Integer.parseInt((String) request.getParameter("idCurso"));

                            out.println(buscarAlumnos(idCurso));


                            //Bloque para buscar los grupos
                        } catch (Exception ex) {

                            nombre = (String) request.getParameter("nombre");
                            precio = ((String) request.getParameter("precio"));
                            duracion = ((String) request.getParameter("duracion"));

                            if (validarDatos()) {
                                out.println(buscarCursos(nombre, Float.parseFloat(precio), Integer.parseInt(duracion)));
                                //out.println("TRUE");
                            } else {
                                //out.println("ERROR");
                                out.println("FALSO");
                            }
                        }
                    }
                }
            } finally {
                //out.println("EXCEPTION");
                out.close();
            }
        } else {
            String strLogin = ""
                    + "<form id='frmIniSesion'>"
                    + "<table> <tr> <th> Error de Autenticación"
                    + "<tr> <td> Usted, no ha iniciado Sesion"
                    + "<tr> <td> <input type='button' value='Ir a Login' onclick=\"cargarPagina('IniciarSesion.jsp', 'contenido')\">"
                    + "</form>";
            out.println(strLogin);
            login.setAttribute("redirect", "CtlConsulGrup");            
        }
    }

    public boolean validarDatos() {

        if (nombre == null) {
            nombre = "";
        } else {
            if (!nombre.matches("[A-z]*")) {
                return false;
            }
        }
        if (precio == null || precio.equals("")) {
            precio = "0";
        } else {
            if (!precio.matches("[0-9]*.[0-9]*")) {
                return false;
            }
        }
        if (duracion == null || duracion.equals("")) {
            duracion = "0";
        } else {
            if (!duracion.matches("[0-9]*")) {
                return false;
            }
        }
        return true;
    }

    public String buscarCursos(String nombre, float precio, int duracion) {

        Curso[] cursos = new Curso().buscarAlgunos(nombre, precio, duracion);

        if (cursos != null || cursos.length != 0) {
            String cad = "<h3>Lista de Grupos Abierto</h3><table>"
                    + "<thead>"
                    + "<tr>"
                    + "<th>Nombre</th>"
                    + "<th>Fecha de Inicio</th>"
                    + "<th>Fecha limite para Incripción</th>"
                    + "<th>Precio del curso</th>"
                    + "<th>Duración</th>"
                    + "<th></th>"
                    + "</tr>"
                    + "</thead><tbody>";

            for (int i = 0; i < cursos.length; i++) {
                cad = cad + "<tr>"
                        + "<td>" + (i + 1) + ".- " + cursos[i].getNombre() + "</td>"
                        + "<td>" + cursos[i].getFechaInicio() + "</td>"
                        + "<td>" + cursos[i].getFechaLimPreInscr() + "</td>"
                        + "<td>" + cursos[i].getPrecio() + "</td>"
                        + "<td>" + cursos[i].getDuracion() + "</td>"
                        + "<td><input type='button' name='idPrograma' id='idPrograma' value='Ver Alumnos' onclick=\"obtenerAlumnos('" + cursos[i].getIdPrograma() + "');\"/></td>"
                        + "</tr>";
            }
            return cad + "</tbody>";
        } else {
            return "<h1>Ningun curso coincide con los criterios de busqueda</h1>";
        }
    }

    /*
     * Autor: Lalo Descripción: Recupera los Alumnos que estan Preincritos a x
     * Curso Fecha: 13 - 05 - 2013
     */
    public String buscarAlumnos(int idCurso) {

        //Cadena que retornara la estructura HTML
        String cad = "";

        //Variable que Almacenara la lista de los alumnos preinscritos
        entity.Inscripcion listaPreInsc[] = new entity.Inscripcion().buscarAlumnosPreincritos(idCurso);

        if (listaPreInsc != null) {
            cad = "<h3>Pre-Inscritos</h3><table><tr>"
                    + "<th>Nombre </th>"
                    + "<th>Fecha de Pre-Incripción</th>"
                    + "<th>No. Folio</th><th></th></tr>";

            for (int i = 0; i < listaPreInsc.length; i++) {
                cad = cad
                        + "<tr><td>" + listaPreInsc[i].getNombreAlumno() + "</td>"
                        + "<td>" + listaPreInsc[i].getFecPreinsc() + "</td>"
                        + "<td><input type='number' name='folio" + (i + 1) + "' id='folio" + (i + 1) + "' value='0'/></td>"
                        + "<td><input type='button' name='idPrograma' id='idPrograma' value='Inscribir Alumno' onclick=\"inscrbirAlumno('" + idCurso + "', " + listaPreInsc[i].getIdInscripcion() + ", 'folio" + (i + 1) + "');\"/></td></tr>";
            }
            cad = cad + "</table>";
        } else {
            return "<h1>Algun Error al recuperar los Preinscritos</h1>";
        }

        //Variable que Almacenara la lista de los alumnos preinscritos
        entity.Inscripcion listaInsc[] = new entity.Inscripcion().buscarAlumnosInscritos(idCurso);

        if (listaInsc != null) {
            cad = cad + "<br><br><h3>Inscritos</h3><table><tr>"
                    + "<th>Nombre </th>"
                    + "<th>Fecha de Incripción</th>"
                    + "<th>No. Folio</th></tr>";

            for (int i = 0; i < listaInsc.length; i++) {
                cad = cad
                        + "<tr><td>" + listaInsc[i].getNombreAlumno() + "</td>"
                        + "<td>" + listaInsc[i].getFecInscr() + "</td>"
                        + "<td>" + listaInsc[i].getFolio() + "</td></tr>";
            }
            cad = cad + "</table>";
        } else {
            return "<h1>Algun Error al recuperar los Inscritos</h1>";
        }

        return cad;
    }

    /*
     * Autor: Lalo Descripción: Mostrara la información al alumno del programa
     * Fecha: 13 - 05 - 2013
     */
    public String recuperarDatosPrograma(int idPrograma) {
        entity.Programa programa = new entity.Programa().recuperarDatos(idPrograma);

        if (programa != null) {
            String cad = "<form id=\"datosCurso\">"
                    + "<input type='hidden' name='idPrograma' value='" + programa.getId_programa() + "'/>"
                    + "<input type='hidden' value='3' name='opcMenu'>"
                    + "Nombre:&nbsp; " + programa.getNombre() + "<br>"
                    + "Fecha de Inicio:&nbsp; " + programa.getFec_fin() + "<br>"
                    + "Fecha limite de Pre-Incripción:&nbsp; " + programa.getFec_lim_preinscrip() + "<br>"
                    + "Precio del curso:&nbsp; " + programa.getPrecio() + "<br>"
                    + "Duración:&nbsp;" + programa.getDuracion() + "<br>"
                    + "Dirigido:&nbsp;" + programa.getDirigido() + "<br>"
                    + "Objetivo:&nbsp;" + programa.getObjetivo() + "<br>"
                    + "Duración:&nbsp;" + programa.getDuracion() + "<br>"
                    + "Temario:&nbsp;" + programa.getTemario() + "<br>"
                    + "Requisitos:&nbsp;" + programa.getRequisitos() + "<br><h1>MODULOS</h1>";

            for (int i = 0; i < programa.getModulos().length; i++) {
                cad = cad
                        + "<p>No. de Modulo:&nbsp; " + (i + 1) + "<br>"
                        + "Nombre del modulo:&nbsp; " + programa.getModulos()[i].getNombre() + "<br>"
                        + "Objetivo del modulo:&nbsp; " + programa.getModulos()[i].getObjetivo() + "<br>"
                        + "Temario del modulo:&nbsp; " + programa.getModulos()[i].getTemario() + "<br>"
                        + "Duracion del modulo:&nbsp; " + programa.getModulos()[i].getDuracion() + " horas<br><p></form>";
            }
            return cad;
        } else {
            return "<h1>Algun Error</h1>";
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Clase Control del Caso Consultar Grupo";
    }
}