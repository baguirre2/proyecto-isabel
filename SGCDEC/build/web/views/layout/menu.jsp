<script type="text/javascript">
function cambiarValor(valor) {
	document.frmMenu.strAccion.value = valor;
}
function optAceptar(valor) {
	document.frmSoftware.opBaja.value = valor;
}
function cambiarOpcion(valor) {
	document.frmMenu.accion.value = valor;
}
</script>
<form name="frmMenu" id="frmMenu" method="GET">
	<input type="hidden" id="strAccion" name="strAccion" value="">
        <input type="hidden" id="accion" name="accion" value="">
</form>
<div id="encabezado">

	<!--<div id="logo"></div>-->
    <img id="logo" src="webroot/images/Logotipo.png" alt="" />
	<div id="menu">
	  <ul>
        <li class="active"><a href="#" >USUARIOS</a>
            <ul>
                
                <li><a href='#' onclick="cambiarOpcion('RegistrarUsuario');Enviar2('CtrlGestUsr', 'contenido', 'frmMenu');">Registrar</a></li>
                <li><a href='#' onclick="cambiarOpcion('Eliminar');Enviar2('CtrlGestUsr', 'contenido', 'frmMenu');">Eliminar</a></li>
                <li><a href='#' onclick="cambiarOpcion('Consulta');Enviar2('CtrlGestUsr', 'contenido', 'frmMenu');">Consultar</a></li>
            </ul>
        </li>
        <li><a href="#">ALUMNOS</a>
            <ul>
                <li><a href='#'>Buscar</a></li>
                <li><a href='#'>Aprobar Registro</a></li>
                <li><a href='#'>Listar</a></li>
            </ul>
        </li>
        <li><a href="#">INSCRIPCI&oacute;N</a>
            <ul>
                <li><a href='#'>Ver Grupos</a></li>
                <li><a href='#' onclick="Enviar(4);">Aprobar Inscripci&oacute;n</a></li>
                <li><a href='#' onclick="cargarPagina('ControlPreinscripcion','contenido')">Pre-inscribir</a></li>
                <li><a href='#'>Modificar</a></li>
                <li><a href='#'>Dar de baja</a></li>
            </ul>
        </li>
        <li><a href="#">PROGRAMAS</a>
            <ul>
                <li><a href='#'>Buscar</a></li>
                <li><a href='#'>Mostrar</a></li>
                <li><a href='#'>Dar de Alta</a></li>
                <li><a href='#'>Modificar</a></li>
                <li><a href='#'>Dar de baja</a></li>
            </ul>
        </li>
        <li><a href="#">CURSOS</a>
            <ul>
                <li><a href='#'>Buscar</a></li>
                <li><a href='#'>Mostrar</a></li>
                <li><a href='#'>Dar de Alta</a></li>
                <li><a href='#'>Modificar</a></li>
                <li><a href='#'>Dar de baja</a></li>
            </ul>
        </li>
        <li><a href="#" onclick="cargarPagina('ControlIniciarSesion','contenido')">Cerrar Sesion</a></li>
   	  </ul>
  </div>
        <!-- <frameset> 
       <div id="menu2" style="margin-top: 100px;"> 
           <br><br><br><br>
           <ul>Inform&aacute;tica</ul>
        <ul>Administracion</ul>
        <ul>Desarrollo de Software Empresarial</ul>
        <ul>Inteligencia Artificial</ul>
        <ul>Lenguajes de Programaci&oacute;n</ul>
        <ul>Administraci&oacute;n de proyectos</ul>

    </div> 
        
    </frameset> -->
</div>
