<%-- 
    Document   : AdmProdcuto
    Created on : 5/06/2012, 11:19:59 PM
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
        <h1>Administracion de Productos</h1>
        <div id="menu">
            <ul>
                <form id="menu5"> <input type="hidden" name="Accion" value="frmRegistro"></form>
                <form id="menu6"> <input type="hidden" name="Accion" value="frmConsultaEliminar"></form>
                <li><a href="javascript:void(0)" onclick="Enviar('CtrlGestProd','body5','menu5')"> Registro </a></li>
                <li><a href="javascript:void(0)" onclick="Enviar('CtrlGestProd','body5','menu6')"> Consulta y Eliminación </a></li>
            </ul>
        </div>
        <div id="body5"> </div>
    </body>
    

</html>
