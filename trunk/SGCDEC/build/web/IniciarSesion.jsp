<form id='frmIniSesion'>
    <input type='hidden' name='tipoIniSesi' value='1'>    
    <table> 
        <tr>
            <th colspan="3"> Inicio de Sesión </th>
        </tr>
        <tr>
            <td> Login </td>
            <td> : </td>
            <td align="left"> <input type='text' name='login'></td>
        </tr>
        <tr>
            <td>Password</td>
            <td>:</td>
            <td align="left"><input type='password' name='pass'><br></td>
        </tr>
        <tr>
            <td colspan="3" align="center"><input type='button' value='Iniciar Sesion' onclick="return Enviar2('ControlIniciarSesion','contenido','frmIniSesion')"></td>
        </tr>
    </table>
</form>
