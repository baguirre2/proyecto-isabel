package cu.iniciarSesion;

import entity.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControlIniciarSesionV1 extends HttpServlet {

    String respHtml;
    entity.Persona persona = null;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {

            String login = (String) request.getParameter("login");
            String pass = (String) request.getParameter("pass");
            String cerrar = request.getParameter("cerrar");

            if (!"logout".equals(cerrar)) {
                out.println(iniciarSesion(login, pass, request.getSession(true)));
            } else {
                out.println("<h1>SESION CERRADA</h1>");
            }
        } finally {
            out.close();
        }
    }

    public String iniciarSesion(String login, String pass, HttpSession sesion) {

        persona = new Persona().recuperarPersonaUsandoSuRol(login, pass);

        if (persona != null) {

            if (persona instanceof entity.Alumno) {
                sesion.setAttribute("idAlum", ((entity.Alumno) persona).getIdAlumno());
                return "<h1>Bienvenido Alumno " + persona.getNombre() + " " + persona.getApePat() + " " + persona.getApeMat() + "</h1>"
                        + "<form id='frmVerGrupos'>"
                            + "<input type='hidden' value='1' name='opcMenu'>"
                            + "<input type='submit' value='Consultar Grupos para inscripcion' onclick='return Enviar (\"ControlInscripcion\", \"respMenuAlumno\", \"frmVerGrupos\")'>"
                        + "</form>"
                        + "<form id='frmVerInscripciones'>"
                            + "<input type='hidden' value='2' name='opcMenu'>"
                            + "<input type='submit' value='Ver Grupos Incritos' onclick='return Enviar (\"ControlInscripcion\", \"respMenuAlumno\", \"frmVerInscripciones\")'>"
                        + "</form><br><br><br>"
                        + "<div id='respMenuAlumno'>"
                        + "</div>"
                        + "<br><form id='logout'>"
                        + "<input type='hidden' value='logout' name='cerrar'>"
                            + "<input type='submit' value='Cerrar Sesión' onClick='logout(\"CtlIniSesion\", \"DatosAlumno\", \"logout\");'/>"
                        + "</form>";

            } else if (persona instanceof entity.Profesor) {
                
                sesion.setAttribute("idProf", ((entity.Profesor) persona).getIdProfesor());
                return "<h1>Bienvenido Profesor " + persona.getNombre() + " " + persona.getApePat() + " " + persona.getApeMat() + "</h1>"
                        + "<form id='frmCalificaciones'>"
                            + "<input type='hidden' value='1' name='opcMenu'>"
                            + "<input type='submit' value='Ver sus alumnos' onclick='return Enviar (\"CtlCalificaciones\", \"respMenuProfesor\", \"frmCalificaciones\")'>"
                        + "</form><br><br><br><br>"
                        + "<div id='respMenuProfesor'>"
                        + "</div>"
                        + "<br><form id='logout'>"
                        + "<input type='hidden' value='logout' name='cerrar'>"
                            + "<input type='submit' value='Cerrar Sesión' onClick='logout(\"CtlIniSesion\", \"DatosAlumno\", \"logout\");'/>"
                        + "</form>";
            } else if (persona instanceof entity.Persona) {
                
                sesion.setAttribute("idProf", persona.getidPersona());
                return "<h1>Bienvenido Administrador " + persona.getNombre() + " " + persona.getApePat() + " " + persona.getApeMat() + "</h1>"
                        + "<h4>Administracion de Usuarios</h4>"
                        + "<div id='Menu'>"
                        + "<ul>"
                        + "<form id='menu'><input type='hidden' name='opcion' value='Consulta'>"
                        + "<li><a href='javascript:void(0)' onclick=\"Enviar('CtrlGestUsr', 'body', 'menu')\">Consulta y Eliminación</a></li></form>"
                        + "<form id='menu1'><input type='hidden' name='opcion' value='frmReg'>"
                        + "<li><a href='javascript:void(0)' onclick=\"Enviar('CtrlGestUsr', 'body', 'menu1')\">Registro</a></li></form>"
                        + "</ul>"
                        + "</div>"
                        + "<div id='body'>"
                        + "</div>"
                        + "<h4>Administracion de Productos</h4>"
                        + "<div id='menu'>"
                        + "<ul>"
                        + "<form id='menu5'> <input type='hidden' name='Accion' value='frmRegistro'></form>"
                        + "<form id='menu6'> <input type='hidden' name='Accion' value='frmConsultaEliminar'></form>"
                        + "<li><a href='javascript:void(0)' onclick=\"Enviar('CtrlGestProd','body5','menu5')\"> Registro </a></li>"
                        + "<li><a href='javascript:void(0)' onclick=\"Enviar('CtrlGestProd','body5','menu6')\"> Consulta y Eliminación </a></li>"
                        + "</ul>"
                        + "</div><div id='body5'> </div>"
                        + "<h4>Administracion de Programas</h4>"
                        + "<div id='menu'>"
                        + "<ul>"
                        + "<form id='menu8'> <input type='hidden' name='Accion' value='frmRegistro'></form>"
                        + "<form id='menu9'> <input type='hidden' name='Accion' value='frmConsultaEliminar'></form>"
                        + "<li><a href='javascript:void(0)' onclick=\"Enviar('CtrlGestProg','body8','menu8')\"> Registro </a></li>"
                        + "<li><a href='javascript:void(0)' onclick=\"Enviar('CtrlGestProg','body8','menu9')\"> Eliminación </a></li>"
                        + "</ul>"
                        + "</div>"
                        + "<div id='body8'> </div>"
                        + "<br><form id='logout'>"
                        + "<input type='hidden' value='logout' name='cerrar'>"
                            + "<input type='submit' value='Cerrar Sesión' onClick='logout(\"CtlIniSesion\", \"DatosAlumno\", \"logout\");'/>"
                        + "</form>";
            } else {
                sesion.invalidate();
                return "Datos\\ Incorrectos";
            }
        } else {
            sesion.invalidate();
            return "Datos\\ Incorrectos";
        }
    }

    /*
     * public String iniciarSesion (String login, String pass) { alum = new
     * entity.Alumno().iniciarSesion(login, pass);
     *
     * if (alum != null) { return "<h1>Bienvenido " + alum.getNombre() + " " +
     * alum.getApePat() + " " + alum.getApeMat() + "<br>Tu RFC es: " +
     * alum.getRFC() + "<br>Tu ID de Persona es: " + alum.getIDPersona() +
     * "<br>Tu ID de Alumno es: " + alum.getIdAlumno() + "</h1>"; } else {
     * return "<form id='frmIniSesion'>" + "Login: <input type='text'
     * name='login'><br>" + "<input type='hidden' name='tipoIniSesi' value='1'>"
     * + "Password: <input type='password' name='pass'><br>" + "<input
     * type='submit' value='Iniciar Sesion' onclick=\"return Enviar
     * ('CtlIniSesion', 'DatosAlumno', 'frmIniSesion')\">" + "</form>" + "<h1>
     * El login o el password son incorrectos<h1>"; }
    }
     */
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