<%-- 
    Document   : frmUno
    Created on : 19/04/2012, 03:07:04 PM
    Author     : lalo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <script language="Javascript" src="funciones.js">
    </script>        
    <body>
        
        <form action="Prueba">
            Nombre: <input type="text" name="nombre1" id="nombre1" value="" /> <br>
            Apellido Meterno: <input type="text" id="apMat" name="apMat" value=""/> <br> 
            Apellido Paterno: <input type="text"  id ="apPat" name="apPat" value="" />
        </form>        
        <input type="submit" value="enviar" onClick="Enviar(3);"/>
        
        <div id="Respuesta_Hello"></div>
    </body>
</html>
