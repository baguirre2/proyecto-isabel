/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import entity.Persona;
import entity.Preinscripcion;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author benjamin
 */
public class ControlPreinscripcion extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        response.setContentType("text/html;charset=UTF-8");
        int idRol;
        boolean preinscrito = true;
        HttpSession sesion = request.getSession();
        if (sesion.getAttribute("idRol") != null) {
            idRol = (Integer) sesion.getAttribute("idRol");
            try {
                int idPrograma = 0, idCurso = 0; 
                if (request.getParameter("idProducto") != null) { idPrograma = (int) Integer.parseInt(request.getParameter("idProducto")); } 
                
                if ("Preinscribir".equals(request.getParameter("Preinscribir"))) {
                    Preinscripcion preinscribir = new Preinscripcion();
                    Persona temp = (Persona) sesion.getAttribute("Persona");
                    if (preinscribir.preInscribir(preinscribir.getidCurso(idPrograma), temp.getidPersona())) {
                        preinscrito = true;
                    } else {
                        preinscrito = false;
                    }
                }
                
                if (!"Preinscribir".equals(request.getParameter("Preinscribir")) || !preinscrito) {

                    String mostrar = "<div align='left'><form id='frmPreIns'>";
                    mostrar += getProductos(idPrograma);
                    if (idPrograma != 0) {

                        if(request.getParameter("Preinscribir") != null && ("Continuar".equals(request.getParameter("Preinscribir")))) {

                        } else {
                            mostrar += setButton();
                            if (!preinscrito) {
                                mostrar += "<tr> <td colspan='3'>  Hubo un error y no se pudo Preinscribir al Alumno";
                            }
                        }
                    }
                    mostrar += "</table>";
                    if(request.getParameter("Preinscribir") != null && ("Continuar".equals(request.getParameter("Preinscribir"))) && idPrograma != 0) {
                        Preinscripcion pre = new Preinscripcion();
                        String nombrePrograma = pre.getNombreProducto(idPrograma);
                        String Preinscribir = "";
                        Preinscribir += "<br><br><table>"
                                + "<tr> <th colspan='3'> Datos de Preinscripcion </td></tr>"
                                + "<tr><td> Alumno:  </td><td>:</td><td> Benjamin Aguirre Garcia </td>"                        
                                + "<tr><td> Programa  </td><td>: </td> <td>  " + nombrePrograma + "</td>" 
                                + "";
                        mostrar += Preinscribir;
                        mostrar += "<tr> <td colspan=3><input type='button' value='Preinscribir' name='Preinscribir' onclick=\"Enviar2('ControlPreinscripcion','contenido','frmPreIns')\"> <br> ";                                
                    }
                    out.println(mostrar + "</form></div>");
                } else {
                    out.println("<table> <tr> <th colspan='2'> Información <tr><td> Alumno Preinscrio Correctamente");
                }
            } finally {            
                out.close();
            }
        } else {
            String login = ""
                    + "<form id='frmIniSesion'>"
                    + "<table> <tr> <th> Error de Autenticación"
                    + "<tr> <td> Usted, no ha iniciado Sesion"
                    + "<tr> <td> <input type='button' value='Ir a Login' onclick=\"cargarPagina('IniciarSesion.jsp', 'contenido')\">"
                    + "</form>";
            out.println(login);
            HttpSession session = request.getSession();
            session.setAttribute("redirect", "ControlPreinscripcion");
        }
    }

    public String getProductos(int id) {
        
        String productos = "<table> "
                + "<tr> <th  colspan='3'>Preinscripcion</th> "
                + "<tr> <td>Selecciona un Curso </td><td>: </td> "
                + "<td><select id='idProducto' name='idProducto' onChange=\"Enviar2('ControlPreinscripcion','contenido','frmPreIns')\">";
        Preinscripcion pre = new Preinscripcion();
        pre.getProductos();
        String nombres[] = pre.getNombresProductos();
        int ids[] = pre.getidsProductos(), count = 0;
        productos += "<option value=0> ------- </option>";
        if (nombres != null) {
            while(nombres[count] != null) {
                productos += "<option value=" + ids[count] ;
                if (ids[count] == id) {
                    productos += " selected";
                }
                productos += "> " + nombres[count] + "</option>";
                count++;
            }
        }
        productos += "</select> </td> </tr>";
        return productos;
    }
    
    public String getCurso(int id, int idModulo) {
        String curso = "<tr> <td>"
                + "Selecciona Profesor - Periodo </td><td>: </td> <td><select id='idCurso' name='idCurso' onChange=\"Enviar2('ControlPreinscripcion','contenido','frmPreIns')\">";
        Preinscripcion pre = new Preinscripcion();
        pre.getLastData(idModulo);
        String data[] = pre.getData();
        int ids[] = pre.getidsCursos(), count = 0;
        curso += "<option value=0> ------- </option>";
        if (data != null && idModulo != 0) {
            while(data[count] != null) {
                curso += "<option value=" + ids[count] ;
                if (ids[count] == id) {
                    curso += " selected";
                }
                curso += "> " + data[count] + "</option>";
                count++;
            }
        }
        curso += "</select></td></tr>";
        return curso;
    }
    
    public String setButton() {
        return "<tr align='center'><td colspan='3'><input type='button' value='Continuar' name='Preinscribir' onclick=\"Enviar2('ControlPreinscripcion','contenido','frmPreIns')\">";
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
