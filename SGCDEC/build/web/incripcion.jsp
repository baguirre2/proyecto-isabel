<%-- 
    Document   : index
    Created on : 25/03/2012, 11:18:28 AM
    Author     : lalo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inscripción</title>
    </head>
    <script language="Javascript" src="funciones.js">
    </script>    
    <body>
        <table border="0">
            <tbody>
                <tr>
                    <td>
                        <form id="frmBuscarGrupos">
                            Nombre: <input type="text" name="nombre" id="nombre" name="nombre" value="" size="30" onkeyUp="Enviar('CtlConsulGrup', 'Respuesta_Hello', 'frmBuscarGrupos');"/><br>
                            Con un precio menor de: <input type="text" id="precio" name="precio" value="0" size="30" onkeyUp="Enviar('CtlConsulGrup', 'Respuesta_Hello', 'frmBuscarGrupos');"/><br>
                            Con una duración menor de: <input type="text" id="duracion" name="duracion" value="0" size="30" onkeyUp="Enviar('CtlConsulGrup', 'Respuesta_Hello', 'frmBuscarGrupos');"/><br>
                        </form>
                        <input type="submit" value="Buscar" onClick='Enviar("CtlConsulGrup", "Respuesta_Hello", "frmBuscarGrupos");'/>
                    </td>
                </tr>
            </tbody>
        </table><br><br>
        <div id="Respuesta_Hello"></div>
    </body>
</html>