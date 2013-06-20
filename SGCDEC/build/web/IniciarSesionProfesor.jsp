<%-- 
    Document   : IniciarSesionProfesor
    Created on : 03-may-2012, 8:51:16
    Author     : Mariana
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
        <form id="frmIniSesionProf">
            Login: <input type="text" name="login"><br>
            Password: <input type="password" name="pass"><br>
        </form>
        <input type="submit" value="Iniciar Sesion" onclick="Enviar ('CtlIniSesion', 'DatosAlumno', 'frmIniSesion');">
    </body>
    <div id="DatosAlumno">Enviar2('CtlIniSesion', 'DatosAlumno', 'frmIniSesion')</div>    
</html>