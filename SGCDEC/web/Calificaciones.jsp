<%-- 
    Document   : Calificaciones
    Created on : 03-may-2012, 14:47:57
    Author     : Mariana
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <script language="javascript" src="funciones.js">
    </script>  
    <body>
        <form id="formulario">
            <input type="hidden" name="idProfesor" id="idProfesor" value="111"> 
        </form>
        <input type="submit" value="Calificaciones" name="calificaciones" onclick ="Enviar('CtlCalificaciones', 'muestraGrupos', 'formulario');"/>
        <input> 
        <div id="muestraGrupos"></div>
    </body>
</html>
