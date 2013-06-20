<%-- 
    Document   : menuAlumno.jsp
    Created on : 3/06/2012, 02:37:44 PM
    Author     : lalo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    </head>
    <body>
        <form id="frmVerGrupos">
            <input type="hidden" value="1" name="opcMenu">
            <input type="submit" value="Ver Grupos" onclick="return Enviar ('CtlInscrip', 'respMenuAlumno', 'frmVerGrupos')">
        </form>
        <form id="frmVerInscripciones">
            <input type="hidden" value="2" name="opcMenu">
            <input type="submit" value="Grupos Incritos" onclick="return Enviar ('CtlInscrip', 'respMenuAlumno', 'frmVerInscripciones')">
        </form>
        <div id="respMenuAlumno">
        </div>
    </body>
</html>
