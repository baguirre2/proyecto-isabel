<%-- 
    Document   : index
    Created on : 2/05/2012, 02:33:11 PM
    Author     : Benjamin Aguirre G
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <style type="text/css">
            
            #Selection {
                float: left 50%;
            }
            
            #Respuesta {
                float: right 50%;
            }
            
            #Menu {
                float: top;
            }
            
            #body {
                float:next 30%;
            }
        </style>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestion de Usuarios</title>
        <script language="Javascript" src="funciones.js"> </script>
                    
    </head>
    <body>
        <h1>ADMINISTRACION DE USUARIOS</h1>
        <div id="Menu">
            <ul>
                <form id="menu"><input type="hidden" name="opcion" value="Consulta">
                <li><a href="javascript:void(0)" onclick="Enviar('CtrlGestUsr', 'body', 'menu')">Consulta y Eliminación</a></li></form>
                <form id="menu1"><input type="hidden" name="opcion" value="frmReg">
                <li><a href="javascript:void(0)" onclick="Enviar('CtrlGestUsr', 'body', 'menu1')">Registro</a></li></form>
            </ul>
        </div>
        <div id='body'>
        </div>
    </body>
</html>
