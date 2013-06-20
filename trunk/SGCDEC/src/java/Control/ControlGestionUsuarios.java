/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import entity.Alumno;
import entity.Persona;
import entity.Profesor;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.catalina.Session;
import org.apache.catalina.connector.Request;

/**
 *
 * @author Benjamin Aguirre G
 */
public class ControlGestionUsuarios extends HttpServlet {
    
    HttpSession session;

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
        String accion;
        String opcion;
        session =  request.getSession();
        
        if (session.getAttribute("idRol") != null) {
        int sRol = (Integer) session.getAttribute("idRol");        
            if (request.getParameter("accion") != null) { accion = (String) request.getParameter("accion"); session.setAttribute("accion", accion);} else { accion = ""; }
            if (request.getParameter("opcion") != null) { opcion = (String) request.getParameter("opcion"); } else { opcion = ""; }

            try {
                if (opcion.equals("Registrar")) {

                    String tipo =  (String) request.getParameter("Tipo");
                    String Form =  (String) request.getParameter("PASS");
                    String nombre =  (String) request.getParameter("Nombre");
                    String ApeP =  (String) request.getParameter("aPaterno");
                    String ApeM =  (String) request.getParameter("aMaterno");
                    String rfc =  (String) request.getParameter("rfc");
                    String form = "<table>"
                                + "<tr> "
                                + "<td>Nombre</td>" 
                                + "<td><input type='text' id='Nombre' name='Nombre'></td> "
                                + "</tr>"
                                + "<tr> "
                                + "<td>Apellido Paterno</td>"
                                + " <td><input type='text' id='aPaterno' name='aPaterno'></td> "
                                + "</tr>"
                                + "<tr> "
                                + "<td> Apellido Materno </td>"
                                + "<td> <input type='text' id='aMaterno' name='aMaterno'> </td> </tr>"
                                + "<tr>"
                                + "<td> RFC </td>"
                                + "<td> <input type='text' id='rfc' name='rfc'> </td> </tr>";
                    if(tipo.equals("Persona")) {
                        if (!Form.equals("Pers")) {
                            form = form + "<tr>"
                                    + "<input type='hidden' name='PASS' value='Pers'> "
                                    + "</table>";
                            out.println(form);
                        } else {
                                if (nombre == null || ApeP == null || ApeM == null || rfc == null) {
                                    out.println("DEBES COMPLETAR TODOS LOS CAMPOS PARA PODER CONTINUAR");
                                } else {
                                    out.println(addPersona(nombre, ApeP, ApeM, rfc));
                                }
                        }
                    } 

                    if (tipo.equals("Profesor")) {
                            String nTrabajador =  (String) request.getParameter("nTrabajador");
                            String estado =  (String) request.getParameter("estado");
                            if (!Form.equals("Prof")) {
                                form = form + "<tr> <td> Numero de Trabajador </td> "
                                        + "<td> <input type='text' id='nTrabajador' name='nTrabajador' > </td>"
                                        + "<tr> <td> Estado </td> "
                                        + "<td> <input type='radio' name='estado' value='Disponible'> Disponible "
                                        + "<input type='radio' name ='estado' value='No Disponible'> No Disponible </td>"
                                        + "<tr>"
                                        + "<input type='hidden' name='PASS' value='Prof'> "
                                        + "</table>";
                                out.println(form);
                            } else {
                                if (nombre == null || ApeP == null || ApeM == null || rfc == null || nTrabajador == null || estado == null) {
                                    out.println("DEBES COMPLETAR TODOS LOS CAMPOS PARA PODER CONTINUAR");
                                } else {
                                    out.println(addProfesor(nombre, ApeP, ApeM, rfc, nTrabajador, estado));
                                }
                            }
                        }

                        if (tipo.equals("Alumno")) {
                            String diplomado =  (String) request.getParameter("diplomado");
                            String conferencias =  (String) request.getParameter("conferencias");                        
                            String seminarios = (String) request.getParameter("seminarios");
                            if (!Form.equals("Alm")) {
                                form = form
                                        + "<tr> <td> Diplomados </td> "
                                        + "<td> <input type='radio' name='diplomado' value='Si'> Si "
                                        + "<input type='radio' name ='diplomado' value='No'> No </td>"
                                        + "<tr> <td> Conferencias </td> "
                                        + "<td> <input type='radio' name='seminarios' value='Si'> Si "
                                        + "<input type='radio' name ='seminarios' value='No'> No </td>"
                                        + "<tr> <td> Seminarios </td> "
                                        + "<td> <input type='radio' name='conferencias' value='Si'> Si "
                                        + "<input type='radio' name ='conferencias' value='No'> No </td>"                                    
                                        + "<tr>"
                                        + "<input type='hidden' name='PASS' value='Alm'> "
                                        + "</table>";
                                out.println(form);
                            } else {
                                if (nombre == null || ApeP == null || ApeM == null || rfc == null || diplomado == null || conferencias == null || seminarios == null) {
                                    out.println("DEBES COMPLETAR TODOS LOS CAMPOS PARA PODER CONTINUAR");
                                } else {
                                    out.println(addAlumno(nombre, ApeP, ApeM, rfc, diplomado, seminarios, conferencias));
                                }
                            }
                        }
                    }

                    if (accion.equals("Consulta") || accion.equals("Eliminar") || accion.equals("RegistrarUsuario")) {
                    out.println(frmConsulta());
                    }

                    if (accion.equals("Registro")) {
                        out.println(frmRegistro()); 
                    } 

                    if (opcion.equals("Busqueda")) {
                        out.println(getUsuarios((String) request.getParameter("Nombre"),(String) request.getParameter("Tipo")));
                    }

                    if (opcion.equals("Eliminar")) {
                        out.println(eliminarUsuario((int) Integer.parseInt(request.getParameter("idPersona"))));
                    }
                    if (opcion.equals("RegistrarU")) {
                        out.println(frmLoginPass(Integer.parseInt(request.getParameter("idPersona")), Integer.parseInt(request.getParameter("tipo"))));
                    }              
                    
                    if (opcion.equals("regUsuario")) {
                        System.out.println("entro");
                        String login, pass, repass;
                        if (request.getParameter("login") != null) { login = request.getParameter("login"); } else { login = "";}
                        if (request.getParameter("Pass") != null) { pass = request.getParameter("Pass"); } else { pass = ""; }
                        if (request.getParameter("repPass") != null) { repass = request.getParameter("repPass"); } else { repass = ""; }
                        
                        if (!pass.equals(repass) || "".equals(login)) {
                            out.println("Las contraseñas no coinciden o falta el Login" + frmLoginPass(Integer.parseInt(request.getParameter("idPersona")), Integer.parseInt(request.getParameter("idTipo"))));
                        } else {
                            Persona persona = new Persona();
                            System.out.println(request.getParameter("idPersona"));
                            int idPersona = Integer.parseInt(request.getParameter("idPersona"));
                            int idRol = Integer.parseInt(request.getParameter("idTipo"));
                            if (!persona.verificarLogin(login)) {
                                persona.agregarUsuario(login, pass, idPersona, idRol);
                            } else {
                                out.println("El login que intentas registrar ya existe");
                            }
                        }
                    }

                } catch (Exception e) {

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
            session.setAttribute("redirect", "CtrlGestUsr");
        }
    }
    
    public String frmLoginPass (int idPersona, int idTipo) {
        String frm = "<form id='frmUsuario' name='frmUsuario'>"
                + "<table> <tr> <th colspan='2'> Registrar Usuario"
                + "<tr> <td> Login <td>: <td> <input type='text' name='login'>"
                + "<tr> <td> Password <td>: <td> <input type='password' name='Pass'>"
                + "<tr> <td>Repite Password <td>: <td> <input type='password' name='repPass'>"
                + "<tr> <td><input type=\"button\" value='Registrar' onclick=\"Enviar2('CtrlGestUsr','RBusqueda','frmUsuario')\"> </table>"
                + "<input type='hidden' name='opcion' value='regUsuario'>"
                + "<input type='hidden' name='idPersona' value='"+idPersona+"'>"
                + "<input type='hidden' name='idTipo' value='"+idTipo+"'></form>";
        return frm;
    }
    
    public String addPersona (String nombre, String Apep,String Apem, String rfc) {
        Persona nuevaPersona = new Persona(nombre,Apep, Apem, rfc);
        if (nuevaPersona.verificarPersona(rfc)) {
            if (nuevaPersona.agregarPersona()) {
                return "Persona Agregada con RFC: " + rfc;
            } else {
                return "Imposible agregar a la perasona";
            }
        } else {
            return "La persona con RFC: " + rfc + " ya existe";
        }
    }
    
    public String addAlumno(String nombre,String ApeP,String ApeM,String rfc,String diplomado,String seminarios,String conferencias) {

        if (new Alumno().verificarAlumno(rfc)) {
            return "El Alumno Ya existe";
        }
        
        if (!new Alumno().verificarAlumno(rfc)) {
            return "El Alumno Ya existe";
        }
        
        boolean bdiplomado, bseminarios, bconferencias;
        if (diplomado.equals("Si")) {
            bdiplomado = true;
        } else {
            bdiplomado = false;
        }
        if (seminarios.equals("Si")) {
            bseminarios = true;
        } else {
            bseminarios = false;
        }        
        if (conferencias.equals("Si")) {
            bconferencias = true;
        } else {
            bconferencias = false;
        }        
        Alumno nuevoAlumno = new Alumno(nombre, ApeP, ApeM, rfc, bdiplomado, bconferencias, bseminarios);
        boolean bAgregado;
        if (!nuevoAlumno.verificarPersona(nuevoAlumno.getRFC())) {
             bAgregado = nuevoAlumno.agregarAlumno();
            if (bAgregado) { return "Registro Completado"; } else { return "Imposible Agregar Profesor Error en BD";}
        } else {
            bAgregado = nuevoAlumno.agregarAlumno();
            if (bAgregado) { return "Registro Completado <br>"
                    + "La persona ya existe, se agregó con los siquientes datos:<br> <table>  <tr> "
                    + "<tr> <th> Nombre </th> "
                    + "<td>" + nuevoAlumno.getNombre() + "</td>"
                    + "<tr> <th> Apellido </td>"
                    + "<td> " + nuevoAlumno.getApePat() + " " + nuevoAlumno.getApeMat() 
                    + "<tr> <th> RFC" + nuevoAlumno.getRFC(); 
            } else { return "Imposible Agregar Alumno Error En los datos";}            
        }
    }
    
    public String addProfesor(String nombre, String apeP, String apeM, String rfc, String nTrabajador, String estado) {

        if (new Profesor().verificarProfesor(rfc)) {
            return "El Profesor Ya existe";
        }        
        boolean bEstado, bAgregado;

        if (estado.equals("Disponible")) {
           bEstado = true;
        } else {
            bEstado = false;
        }
        Profesor nuevoProf = new Profesor(nTrabajador, bEstado , nombre, apeP, apeP, rfc);
        
        if (!nuevoProf.verificarPersona(nuevoProf.getRFC())) {
             bAgregado = nuevoProf.agregarProfesor();
            if (bAgregado) { return "Registro Completado"; } else { return "Imposible Agregar Profesor Error en BD";}
        } else {
            bAgregado = nuevoProf.agregarProfesor();
            if (bAgregado) { return "Registro Completado <br>"
                    + "La persona ya existe, se agregó con los siquientes datos:<br> <table>  <tr> "
                    + "<tr> <th> Nombre </th> "
                    + "<td>" + nuevoProf.getNombre() + "</td>"
                    + "<tr> <th> Apellido </td>"
                    + "<td> " + nuevoProf.getApePat() + " " + nuevoProf.getApeMat() 
                    + "<tr> <th> RFC" + nuevoProf.getRFC(); 
            } else { return "Imposible Agregar Profesor Error en BD";}            
        }
    }

    public String frmConsulta() {
        
        String frm;
        frm =     "<form id='frmGestionUsuarios'>"
                + "<table>"
                + "<tr><th colspan=2> Busqueda de Usuarios </th>"
                + " <tr> " + getFormUso()
                + "<tr><td> Nombre, Apellidos o RFC:"
                + "<td><input type=\"text\" name=\"Nombre\" id=\"Nombre\" onkeyup=\"Enviar2('CtrlGestUsr','RBusqueda','frmGestionUsuarios')\">"
                + "<input type='hidden' name='opcion' value='Busqueda'>"
                + "</table>"
                
                + "</form>"                
                + "<div id='RBusqueda'></div>";
       
        return frm; 
    }
    
    public String frmRegistro() {
        return  " <form id='frmGestionUsuarios'>"
                + "<table> <tr> <th> Registro de Usuarios + <tr> "
                + getFormUso()
                + "<div id='CargarForm'></div> "
                + "<input type='hidden' name='PASS' value='First'> "
                + "<input type='hidden' name='opcion' value='Registrar'>"
                + "<td> <input type='button' value='Agregar Usuario' onclick=\"Enviar2('CtrlGestUsr','Respuesta','frmGestionUsuarios')\"></td> </tr>"
                + "</table></form> "
                + "<div id=\"Respuesta\"> </div>";
    }

    public String getFormUso () {
        String form;
        
        form = ""
                + "<td><input type='radio' name ='Tipo' value='Profesor'>  Profesor"
                + "<td><input type='radio' name ='Tipo' value='Persona'> Persona"
                + "<td><input type='radio' name ='Tipo' value='Alumno'> Alumno";
        return form; 
    }
    
    public String getUsuarios (String nombre, String tipo) {
        int id = 0;
        if (tipo.equals("Profesor")) {
         id = 2;    
        }
        if (tipo.equals("Persona")) {
         id = 4;
        }
        if (tipo.equals("Alumno")) {
         id = 1;   
        }
       
        try {
                    
        String accio = (String) session.getAttribute("accion");
        Persona [] personas;
        if (accio.equals("RegistrarUsuario")){
             personas = new Persona().buscarUsuarios(nombre, -1);
        } else {
            personas = new Persona().buscarUsuarios(nombre, id);
        }


            if (personas != null && personas.length != 0) {
                String cad = "<form id='frmUsuarios'><table border='1'>" + 
                             "<thead>" + 
                             "<tr>";
                String accion = (String) session.getAttribute("accion");                
                if ("Eliminar".equals(accion) || "RegistrarUsuario".equals(accion)) { 
                    cad += "<th>";
                }
                cad +=  "<th>Nombre</th>" +
                        "<th>Apellido Paterno: </th>" +
                        "<th>Apellido Materno: </th>" +
                        "<th>RFC: </th>" +
                        "</tr>" +
                        "</thead><tbody>";

                for (int i = 0; i < personas.length; i++) {
                    cad = cad + "<tr>";
                            if ("Eliminar".equals(accion) || "RegistrarUsuario".equals(accion)) { 
                                cad += "<td><input type='checkbox' name='idPersona' value="+personas[i].getidPersona()+"></td>";
                            }                                                
                            cad += "<td>" + personas[i].getNombre() + "</td>"
                            + "<td>" + personas[i].getApePat() + "</td>"
                            + "<td>" + personas[i].getApeMat() + "</td>"
                            + "<td>" + personas[i].getRFC() + "</td>"
                            + "</tr>";
                }
                cad += "<tr> <td colspan='7'> ";
                if ("Eliminar".equals(accion)) {
                        cad += "<input type='hidden' id='tipo' name='tipo' value='"+id+"'>"
                        + "<input type='hidden' id='opcion' name='opcion' value='Eliminar'>"
                        + "<input type=\"button\" value='Eliminar' onclick=\"Enviar2('CtrlGestUsr','RBusqueda','frmUsuarios')\">";
                }
                if ("RegistrarUsuario".equals(accion)) {
                        cad += "<input type='hidden' id='tipo' name='tipo' value='"+id+"'>"
                        + "<input type='hidden' id='opcion' name='opcion' value='RegistrarU'>"
                        + "<input type=\"button\" value='Agregar' onclick=\"Enviar2('CtrlGestUsr','RBusqueda','frmUsuarios')\">";
                }                
                return cad += " </tbody> </table></form>";
            } else {
                return "<h3>Ningun(a) "+ tipo +" coincide con el criterio proporcionado</h3>";  
            }
            } catch(NullPointerException npe) {
                return "<h3>Ningun(a) "+ tipo +" coincide con el criterio proporcionado</h3>";  
            }            
    }
    
    public String eliminarUsuario(int idPersonaRol) {
        
        boolean eliminar;
        
        eliminar = new Persona().eliminarUsuario(idPersonaRol);
        if (eliminar) {
                return "Se ha eliminado el Usuario correctamente";
        } else {
             return "Hubo un error y no pudismo eliminar nada";
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