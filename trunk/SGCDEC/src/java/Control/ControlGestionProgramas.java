/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.Programa;
import entity.Producto;
import entity.Profesor;
import java.io.IOException;
import java.util.Date;
/**
 *
 * @author Benjamin Aguirre G
 */
@WebServlet(name = "ControlGestionProgramas", urlPatterns = {"/CtrlGestProg"})
public class ControlGestionProgramas extends HttpServlet {

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
        String accion = (String) request.getParameter("Accion");
        try {
 
            if (accion.equals("frmRegistro")){
                out.println(getfrmRegistro());
            }
            
            if (accion.equals("Registrar")) {
                String 
                nombreProd = (String) request.getParameter("NombreProducto"),
                minimoAl = (String) request.getParameter("MinimoAlumnos"),
                estado = (String) request.getParameter("Estado"),
                fechaIni = (String) request.getParameter("FechaInicio"),
                fechaFin = (String) request.getParameter("FechaFin"),
                fechaLimite = (String) request.getParameter("FechaLimite"),
                publicado = (String) request.getParameter("Publicado"),
                programado = (String) request.getParameter("Programado"),
                confirmado = (String) request.getParameter("Confirmado"),
                precio = (String) request.getParameter("Precio"),
                nombreProf = (String) request.getParameter("Costo");
                if (nombreProd == null || nombreProf == null || minimoAl == null || estado == null || fechaIni == null || fechaFin == null || fechaLimite == null || publicado == null || programado == null || confirmado == null || precio == null) {
                    out.println("Debes de Completar Todos los Campos");
                } else {
                  out.println(addPrograma(nombreProd , minimoAl , estado, fechaIni,  fechaFin, fechaLimite, publicado , programado, confirmado, precio, nombreProf));
                }
            }   

           
            if (accion.equals("Eliminar")) {
                out.println(eliminarPrograma((String) request.getParameter("rfcProfesor"), (String) request.getParameter("NombreProducto")));
            }
            
            if (accion.equals("frmConsultaEliminar")){
                out.println( getfrmEliminar());
            }
            
        } finally {            
            out.close();
        }
    }

    public String getfrmRegistro () {
        return "<div><form id='Producto'> "
            + "<table>"
                + "<tr> "
                    + "<td> Nombre del Producto  </td> "
                    + "<td> <input type='text' name='NombreProducto' > </td>"
                + "</tr> "
                + "<tr> "
                    + "<td>  Costo </td>"
                    + "<td> <input type='text' name='Costo' > </td> "
                + "</tr>"
                + "<tr>"
                    + "<td> Minimo de Alumnos   </td>"
                    + "<td> <input type='text' name='MinimoAlumnos' > </td>"
                + "</tr>"
                + "<tr> "
                    + "<td> Estado  </td> "
                    + "<td> <input type='radio' name='Estado' value='Si'> Disponible"
                + "<input type='radio' name='Estado' value'no'> NO Disponible"  
                + "</tr>"
                + "<tr>"
                    + "<td> Fecha de Inicio (dd-mm-aaaa)<br> Utiliza guiones para separar la fecha  </td>"
                    + "<td> <input type='text' name='FechaInicio' > </td>"
                + "</tr>"
                + "<tr>"
                    + "<td> Fecha de Fin (dd-mm-aaaa)<br> Utiliza guiones para separar la fecha  </td>"
                    + "<td> <input type='text' name='FechaFin' > </td>"
                + "</tr>"
                + "<tr>"
                    + "<td> Fecha de Limite (dd-mm-aaaa)<br> Utiliza guiones para separar la fecha  </td>"
                    + "<td> <input type='text' name='FechaLimite' > </td>"
                + "</tr> "               
                + "<tr>"
                    + "<td> Precio </td>"
                    + "<td> <input type='text' name='Precio' > </td>"
                + "</tr>"
                + "<tr>"
                    + "<td> Nombre Profesor: </td>"
                    + "<td> <input type='text' name='nombre' > </td>"
                + "</tr>"
                + "<tr> "
                    + "<td> Publicado  </td> "
                    + "<td> <input type='radio' name='Publicado' value='Si'> Si"
                + "<input type='radio' name='Publicado' value'no'> No "  
                + "</tr>"
                + "<tr> "
                    + "<td> Programado  </td> "
                    + "<td> <input type='radio' name='Programado' value='Si'> Si"
                + "<input type='radio' name='Programado' value'no'> No"  
                + "</tr>"
                + "<tr> "
                    + "<td> Confirmado</td> "
                    + "<td> <input type='radio' name='Confirmado' value='Si'> Si"
                + "<input type='radio' name='Confirmado' value'no'> No"  
                + "</tr>"                
                + "<tr> "
                + "<input type='hidden' name='Accion' value='Registrar'>"
                   + "<td> <input type='button' value='Agregar Producto' onclick=\"Enviar('CtrlGestProg','Agregado','Producto')\"> "
            + "</table>"
        + "</form> </div>"
        +"<div id=\"Agregado\"> </div>";
    }
    
    
    public String getfrmEliminar () {
        return "<div><form id='Eliminar'>"
        + "<table>"
                + "<tr colspan='2'> <td> Teclea el Nombre del Programa a Eliminar "
           + "<tr> <input type='hidden' name='Accion' value='Eliminar'>"
                + "<td>Nombre del Producto</td>"
                + "<td><input type='text' name='NombreProducto' </td>"
                + "<tr> <td>RFC Profesor</td>"
                + "<td><input type='text' name='rfcProfesor' </td>"
           + "<tr> "
                + "<td><input type='button' value='Eliminar Producto' onclick=\"Enviar('CtrlGestProg','RespuestaEliminar','Eliminar')\">"
                + "</td>"                
        + "</table>"
    + "</form></div>"
    + "<div id='RespuestaEliminar'></div>";
        
    }
    
    public String addPrograma (String nombreProducto , String minimo,String estado, String fechaIni,String fechaFin, String fechaLimite, String publicado, String programado,String confirmado, String precio, String rfcProf) {
        boolean bestado, bpublicado, bprogramado, bconfirmado;
        float fPrecio;
        int eMinimo, idProd = -1, idProf = -1;
        
        Producto verifProducto = new Producto();
        if (verifProducto.verificarProducto(nombreProducto)) {
            verifProducto.setNombre(nombreProducto);
            verifProducto.setIdProducto();
            idProd = verifProducto.getIdProducto();
        }
        
        Profesor prof = new Profesor();
        if (prof.verificarProfesor(rfcProf)) {
            prof.setIdProfesor(rfcProf);
            idProf = prof.getIdProfesor();
        }
        
        if (new Programa().verificarPrograma(verifProducto.getIdProducto(), prof.getIdProfesor())) {
            return "El Prodcuto Ya existe";
        }
        
        try {
           eMinimo = Integer.parseInt(minimo);
        } catch (NumberFormatException nfe) {
            return "Error en Minimo de Alumnos, verifica que sean Digitos sin letras ni espacios";
        }
        
        try {
            fPrecio = Float.parseFloat(precio);
        } catch (NumberFormatException nfe) {
            return "Error en Costo o Precios, verifica que sean Digitos sin letras ni espacios puedes usar el punto para decimal";
        }
        
        if (estado.equals("Si")) { bestado = true; } else { bestado = false;}
        if (publicado.equals("Si")) { bpublicado = true; } else { bpublicado = false; }
        if (confirmado.equals("Si")) { bconfirmado = true; } else { bconfirmado = false; }
        if (programado.equals("Si")) { bprogramado = true; } else { bprogramado = false; }

        Programa nuevoPrograma = new Programa(idProd , eMinimo, bestado, fechaIni, fechaFin, fechaLimite, bpublicado ,bconfirmado, bprogramado, fPrecio, idProf);
        boolean agregado = nuevoPrograma.agregarPrograma();
        if (agregado) {
            return "Producto Agregado Con Éxito";
        } else {
            return "Imposible Agregar Producto - Error Interno";
        }
    }
    
    public String eliminarPrograma (String rfcProf, String nombreProd) {
        
        int idProf, idProd;
        Profesor temp = new Profesor();
        if (temp.verificarProfesor(rfcProf)) {
            temp.setIdProfesor(rfcProf);
           idProf = temp.getIdProfesor();
        } else {
            return "No existe el profesor con el RFC ingresado";
        }
        
        Producto tempProg = new Producto();
        if (tempProg.verificarProducto(nombreProd)) {
            tempProg.setNombre(nombreProd);
            tempProg.setIdProducto();
            idProd = tempProg.getIdProducto();
        } else {
            return "No existe el Producto con el nombre ingresado";
        }        
                
        boolean eliminar = new Programa().eliminarPrograma(idProf, idProd);
            if (eliminar) {
                return "El programa Asociado con el nombre de Producto" + nombreProd + " "
                        + "<br> y con el Profesor con RFC = " + rfcProf + " ha sido eliminado";
            } else {
                return "No se ha podido encontrar el nombre del producto";
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
