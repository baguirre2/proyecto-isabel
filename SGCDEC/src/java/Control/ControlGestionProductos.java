/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import entity.Persona;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import entity.Producto;

/**
 *
 * @author Benjamin Aguirre G
 */
@WebServlet(name = "ControlGestionProductos", urlPatterns = {"/CtrlGestProd"})
public class ControlGestionProductos extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String accion =(String) request.getParameter("Accion");
        try {
            if (accion.equals("frmRegistro")){
                out.println(getfrmRegistro());
            }
            
            if (accion.equals("Registrar")) {
                String 
                nombre = (String) request.getParameter("Nombre"),
                objetivo = (String) request.getParameter("Objetivo"),
                dirigido = (String) request.getParameter("Dirigido"),
                requisitos = (String) request.getParameter("Requisitos"),
                temario = (String) request.getParameter("Temario"),
                modulos = (String) request.getParameter("Modulos"),
                duracion = (String) request.getParameter("Duracion");
                if (nombre == null || objetivo == null || dirigido == null || requisitos == null || temario == null || modulos == null || duracion == null) {
                    out.println("Debes de Completar Todos los Campos");
                } else {
                    out.println(addProducto(nombre, objetivo, dirigido, requisitos, temario, modulos, duracion));
                }
            }

            if (accion.equals("Consultar")) {
                out.println(consultaProductos((String) request.getParameter("Nombre")));
            }            
            
            if (accion.equals("Eliminar")) {
                out.println(eliminarProducto((String) request.getParameter("Nombre")));
            }
            
            if (accion.equals("frmConsultaEliminar")){
                out.println(getfrmConsulta() + "<br>" + getfrmEliminar());
            }
            
        } finally {            
            out.close();
        }
    }

    public String getfrmRegistro () {
        return "<div><form id='Producto'> "
            + "<table>"
                + "<tr> "
                    + "<td> Nombre  </td> "
                    + "<td> <input type='text' name='Nombre' > </td>"
                + "</tr> "
                + "<tr> "
                    + "<td>  Objetivo </td>"
                    + "<td> <input type='text' name='Objetivo' > </td> "
                + "</tr>"
                + "<tr>"
                    + "<td> A quien va dirigido:   </td>"
                    + "<td> <input type='text' name='Dirigido' > </td>"
                + "</tr>"
                + "<tr>"
                    + "<td> Requisitos:   </td>"
                    + "<td> <input type='text' name='Requisitos' > </td>"
                + "</tr>"
                + "<tr> "
                    + "<td> No de Modulos:  </td> "
                    + "<td> <input type='text' name='Modulos' pattern=\"[0-9]{1,2}\" title=\"Recuerda Usar Solo Dígitos\"> </td>"   
                + "</tr>"
                + "<tr>"
                    + "<td> Temario   </td>"
                    + "<td> <input type='text' name='Temario' > </td>"
                + "</tr>"
                + "<tr>"
                    + "<td> Duracion en Horas  </td>"
                    + "<td> <input type='text' name='Duracion' pattern=\"[0-9]{1,3}\"> </td>"
                + "</tr>"
                + "<tr> "
                + "<input type='hidden' name='Accion' value='Registrar'>"
                   + "<td> <input type='button' value='Agregar Producto' onclick=\"Enviar('CtrlGestProd','Agregado','Producto')\"> "
            + "</table>"
        + "</form> </div>"
        +"<div id=\"Agregado\"> </div>";
    }
    
    public String getfrmConsulta () {
        return "<div><form id='Consulta'>"
        + "<table>"
                + "<tr> <td> Teclea el Nombre del Producto "
           + "<tr> <input type='hidden' name='Accion' value='Consultar'>"
                + "<td>Nombre del Producto</td>"
                + "<td><input type='text' name='Nombre' onkeyup=\"Enviar('CtrlGestProd','RespuestaConsulta', 'Consulta')\"></td>"
        + "</table>"
    + "</form> </div>"
    + "<div id=\"RespuestaConsulta\"></div>";
    }
    
    public String getfrmEliminar () {
        return "<div><form id='Eliminar'>"
        + "<table>"
                + "<tr colspan='2'> <td> Teclea el Nombre del Producto a Eliminar "
           + "<tr> <input type='hidden' name='Accion' value='Eliminar'>"
                + "<td>Nombre del Producto</td>"
                + "<td><input type='text' name='Nombre' </td>"
           + "<tr> "
                + "<td><input type='button' value='Eliminar Producto' onclick=\"Enviar('CtrlGestProd','RespuestaEliminar','Eliminar')\">"
                + "</td>"                
        + "</table>"
    + "</form></div>"
    + "<div id='RespuestaEliminar'></div>";
        
    }
    
    public String addProducto (String nombre,String objetivo,String dirigido,String requisitos,String temario,String modulos,String duracion) {
        
        if (new Producto().verificarProducto(nombre)) {
            return "El Prodcuto Ya existe";
        }
        
        try {
            Integer.parseInt(duracion); 
            Integer.parseInt(modulos);
        } catch (NumberFormatException nfe) {
            return "Error en el Tipo de Dato de Duración o Modulos, verifica que sean Digitos sin letras ni espacios";
        }
        Producto nuevoProd = new Producto(nombre, dirigido, objetivo, requisitos, temario, Integer.parseInt(duracion), Integer.parseInt(modulos));
        if (nuevoProd.agregarProducto()) {
            return "Producto Agregado Con Éxito";
        } else {
            return "Imposible Agregar Producto - Error Interno";
        }
        
    }
    
    public String consultaProductos(String nombre) {
           try {
            Producto [] productos = new Producto().obtenerProductos(nombre);                
            if (productos != null || productos.length != 0) {
                String cad = "<table border='1'>" + 
                                    "<thead>" + 
                                        "<tr>" +
                                            "<th>Nombre</th>" +
                                            "<th>No Modulos</th>" +
                                            "<th>Duracion </th>" +
                                            "<th>Fecha de Registro </th>" +
                                        "</tr>" +
                                    "</thead><tbody>";

                for (int i = 0; i < productos.length; i++) {
                    cad = cad + "<tr>"
                            + "<td>" + productos[i].getNombre() + "</td>"
                            + "<td>" + productos[i].getModulos()+ "</td>"
                            + "<td>" + productos[i].getDuracion()+ "</td>"
                            + "<td>" + productos[i].getFechaReg() + "</td>"
                            + "</tr>";
                }
                return cad + "</tbody>" + "</table>";
            } else {
                return "<h3>Ningun producto coincide con el criterio proporcionado</h3>";  
            }
            } catch(NullPointerException npe) {
                return "<h3>Ninguna producto coincide con el criterio proporcionado</h3>";  
            }            
        }        
    
    
    public String eliminarProducto (String nombre) {
        
            boolean eliminar = new Producto().eliminarProducto(nombre);
            if (eliminar) {
                return "El Producto con Nombre: " + nombre + " ha sido eliminado";
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
