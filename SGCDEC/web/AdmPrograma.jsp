<%-- 
    Document   : AdmPrograma
    Created on : 6/06/2012, 03:45:46 AM
    Author     : Benjamin Aguirre G
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="funciones.js"></script>
    </head>
    <body>
        <h1>Administracion de Programas</h1>
        <div id="menu">
            <ul>
                <form id="menu8"> <input type="hidden" name="Accion" value="frmRegistro"></form>
                <form id="menu9"> <input type="hidden" name="Accion" value="frmConsultaEliminar"></form>
                <li><a href="javascript:void(0)" onclick="Enviar('CtrlGestProg','body8','menu8')"> Registro </a></li>
                <li><a href="javascript:void(0)" onclick="Enviar('CtrlGestProg','body8','menu9')"> Eliminación </a></li>
            </ul>
        </div>
        <div id="body8"> </div>
    </body>
</html>
