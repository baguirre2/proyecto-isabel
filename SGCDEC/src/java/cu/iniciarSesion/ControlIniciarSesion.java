/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cu.iniciarSesion;

import entity.Persona;
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
public class ControlIniciarSesion extends HttpServlet {

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
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /*
             * TODO output your page here. You may use following sample code.
             */
            String login;
            String pass;
            String Redirect;
            HttpSession session = request.getSession();
            
            if (request.getParameter("login") != null) {

                login = request.getParameter("login");
                pass = request.getParameter("pass");
                Persona persona = new Persona();
                persona = persona.buscarPersona(login, pass);
                if (persona != null) {
                    String strLogin = "<table> <tr> <th colspan='2'> Inicio de Sesion <tr> <td> Bienvenido : <td> " + persona.getNombre() + " " + persona.getApePat();
                    if (session.getAttribute("redirect") != null) {
                        Redirect = (String) session.getAttribute("redirect");
                        strLogin += "<tr> <td colspan='2'><input type='button' value='Continuar' onclick=\"cargarPagina('" + Redirect + "','contenido')\">";
                    } 
                    out.println(strLogin);
                }
                session.setAttribute("idRol", persona.getidPersonaRol());
                session.setAttribute("Persona", persona);
            } else {
                out.println("<table> <tr> <th colspan='2'> Inicio de Sesion <tr> <td> Vuelve Pronto "
                        + "<input type='button' value='Continuar' onclick=\"cargarPagina('IniciarSesion.jsp','contenido')\">");
                session.invalidate();
            } 
        } finally {            
            out.close();
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
