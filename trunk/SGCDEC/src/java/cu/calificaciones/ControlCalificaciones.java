package cu.calificaciones;

import entity.Inscripcion;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControlCalificaciones extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private String calificacion;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            int idInscripcion = 0;
            int eOpc = Integer.parseInt((String)request.getParameter("opcMenu"));
            String resp = "<h1>ERROR</h1>";
            HttpSession sesion = request.getSession(true);
            
            switch (eOpc) {
                case 1: resp = obtenerAlumnosParaCalificar((Integer) sesion.getAttribute("idProf"));
                    break;
                    
                case 2: idInscripcion = Integer.parseInt((String)request.getParameter("idInscripcion"));
                        resp = "<form id='frmCalificacion'>"
                                + "<input type='hidden' value='3' name='opcMenu'>"
                                + "<input type='hidden' value='" + idInscripcion + "' name='idInscripcion' id='idInscripcion'>"
                                + "Calificación: <input type='text' name='cal' id='cal' value='0.0' size='4' /><br>"
                                + "<input type='submit' value='Calificar' onClick='Enviar(\"CtlCalificaciones\", \"calificar\", \"frmCalificacion\");'/>"
                            + "</form>";
                    break;                
                    
                case 3: 
                    idInscripcion = Integer.parseInt((String)request.getParameter("idInscripcion"));
                    calificacion = (String)request.getParameter("cal");
                    if (validarCalificacion()) {
                        float cali = Float.parseFloat(calificacion);
                        if (cali >= 0 && cali <= 10) {
                            if (calificar(idInscripcion, cali)) resp = "Se ha calificado exitosamente";
                        } else {
                            resp = "<h1>La calificacion debe ser entre 0 o 10</h1>";
                        }
                    } else {
                        resp = "<h1>La calificacion debe ser un valor numerico</h1>";
                    }
                    break;
                
                default:  
                    resp = "<h1>ERROR</h1>";
            }
            
            out.println(resp);
            
            
        } finally {            
            out.close();
        }
    }
    
    public boolean calificar (int idInscripcion, float cali) {
        return new Inscripcion().modificarCalificacion(idInscripcion, cali);
    }
    
    public boolean validarCalificacion() {

        if (calificacion == null || calificacion.equals("")) {
            calificacion = "0";
        } else {
            if (!calificacion.matches("[0-9.]*")) {
                return false;
            }
        }
        return true;
    }
    
    public String obtenerAlumnosParaCalificar (int idProfesor){
        entity.Inscripcion[] inscripciones = new entity.Inscripcion().listarIncripcionDeProfesor(idProfesor);

        try {
            if (inscripciones != null || inscripciones.length != 0) {
                String cad = "<div id='calificar'></div><br><br><table border='1'>"
                        + "<form id=\"calificarAlumno\">"
                        + "<input type='hidden' value='2' name='opcMenu'>"
                        + "<thead>"
                        + "<tr>"
                        + "<th></th>"
                        + "<th>Nombre del Alumno</th>"
                        + "<th>Curso que se le imparte</th>"
                        + "<th>Calificación</th>"
                        + "</tr>"
                        + "</thead><tbody>";

                String cal = "";

                for (int i = 0; i < inscripciones.length; i++) {
                    cal = (inscripciones[i].getCalificacion() < 0) ? "SinCal" : "" + inscripciones[i].getCalificacion();

                    cad = cad + "<tr>"
                            + "<td><input type='radio' name='idInscripcion' id='idInscripcion' value='" + inscripciones[i].getIdInscripcion() + "'>" + (i + 1) + "</td>"
                            + "<td>" + inscripciones[i].getNombreAlumno() + "</td>"
                            + "<td>" + inscripciones[i].getNombrePrograma() + "</td>"
                            + "<td>" + cal + "</td>"
                            + "</tr>";
                }
                return cad + "</tbody>" + "</table><br><br><input type='submit' value='Calificar Alumno Seleccionado' onClick=\"Enviar('CtlCalificaciones', 'calificar', 'calificarAlumno');\"/></form>";
            } else {
                return "<h1>No tiene alumnos asignados</h1>";
            }
        } catch (Exception e) {
            return "<h1>EXCEPCION</h1>";
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}