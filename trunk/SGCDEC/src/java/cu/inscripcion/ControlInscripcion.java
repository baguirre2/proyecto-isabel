/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.inscripcion;

import entity.Curso;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControlInscripcion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            int eOpc = Integer.parseInt((String)request.getParameter("opcMenu"));
            String resp = "<h1>ERROR</h1>";
            HttpSession sesion = request.getSession(true);
            
            switch (eOpc) {
                case 1: resp = "<table border='0'>"
                                    + "<tbody>"
                                        + "<tr>"
                                            + "<td>"
                                                + "<form id='frmBuscarGrupos'>"
                                                        + "Nombre: <input type='text' name='nombre' id='nombre' name='nombre' value='' size='30' "
                                                            + "onkeyUp='Enviar(\"CtlConsulGrup\", \"Respuesta_Hello\", \"frmBuscarGrupos\");'/><br>"
                                                        + "Con un precio menor de: <input type='text' id='precio' name='precio' value='0' size='30' "
                                                            + "onkeyUp='Enviar(\"CtlConsulGrup\", \"Respuesta_Hello\", \"frmBuscarGrupos\");'/><br>"
                                                        + "Con una duración menor de: <input type='text' id='duracion' name='duracion' value='0' size='30' "
                                                            + "onkeyUp='Enviar(\"CtlConsulGrup\", \"Respuesta_Hello\", \"frmBuscarGrupos\");'/><br>"
                                                + "</form>"
                                                + "<input type='submit' value='Buscar' onClick='Enviar(\"CtlConsulGrup\", \"Respuesta_Hello\", \"frmBuscarGrupos\");'/>"
                                            + "</td>"
                                        + "</tr>"
                                    + "</tbody>"
                                + "</table><br><br>"
                                + "<div id='Respuesta_Hello'></div>";
                    break;
                    
                case 2: resp = verGruposAlumno((Integer) sesion.getAttribute("idAlum"));
                    break;
                    
                case 3: int idPrograma = Integer.parseInt((String)request.getParameter("idPrograma"));
                        Integer idAlumno = (Integer) sesion.getAttribute("idAlum");
                        if (inscribir(idPrograma, idAlumno)) {
                            resp = "<h1>La inscripción ha se realizado exitosamente</h1>";
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
    
    public boolean inscribir (int idPrograma, int idAlumno) {
        return new entity.Inscripcion().inscribir(idPrograma, idAlumno);
    }
    
    public String verGruposAlumno(int idAlumno) {
        entity.Inscripcion[] inscripciones = new entity.Inscripcion().listarIncripcionDeAlumno(idAlumno);

        try {
            if (inscripciones != null || inscripciones.length != 0) {
                String cad = "<table border='1'>"
                        + "<thead>"
                        + "<tr>"
                        + "<th></th>"
                        + "<th>Nombre del Curso</th>"
                        + "<th>Calificación</th>"
                        + "</tr>"
                        + "</thead><tbody>";

                String cal = "";

                for (int i = 0; i < inscripciones.length; i++) {
                    cal = (inscripciones[i].getCalificacion() < 0) ? "SinCal" : "" + inscripciones[i].getCalificacion();

                    cad = cad + "<tr>"
                            + "<td>" + (i + 1) + "</td>"
                            + "<td>" + inscripciones[i].getNombrePrograma() + "</td>"
                            + "<td>" + cal + "</td>"
                            + "</tr>";
                }
                return cad + "</tbody>" + "</table>";
            } else {
                return "<h1>No estas inscrito a ningun grupo</h1>";
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