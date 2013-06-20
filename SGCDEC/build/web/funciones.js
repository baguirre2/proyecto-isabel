function inscrbirAlumno(idCurso, idInscr, noFolio) {

        var obj = false;

        if (window.XMLHttpRequest) {
            //Cuidado aqui, el objeto XMLHttpRequest no esta disponible en versiones previas a IE7
            obj = new XMLHttpRequest();
        } else {
            return false;
        }
        obj.onreadystatechange = function () {
            if ( obj.readyState == 4 && (obj.status == 200 || window.location.href.indexOf ("http")==- 1)) {
                document.getElementById('contenido').innerHTML = obj.responseText;
            } else {
                document.getElementById('contenido').innerHTML = "<img src='cargando.gif'/></img>";
            }    
        };
        
        folio = document.getElementById(noFolio).value;
        
        obj.open("GET", "CtlConsulGrup?idCurso=" + idCurso + "&idInscr=" + idInscr + "&folio=" + folio, true);

        obj.send(null);
        return (true);
}

function obtenerAlumnos(idCurso) {

        var obj = false;

        if (window.XMLHttpRequest) {
            //Cuidado aqui, el objeto XMLHttpRequest no esta disponible en versiones previas a IE7
            obj = new XMLHttpRequest();
        } else {
            return false;
        }
        obj.onreadystatechange = function () {
            if ( obj.readyState == 4 && (obj.status == 200 || window.location.href.indexOf ("http")==- 1)) {
                document.getElementById('contenido').innerHTML = obj.responseText;
            } else {
                document.getElementById('contenido').innerHTML = "<img src='cargando.gif'/></img>";
            }    
        };
        
        obj.open("GET", "CtlConsulGrup?idCurso=" + idCurso, true);

        obj.send(null);
        return (true);
}

function Enviar(accion) {

        var obj = false;

        if (window.XMLHttpRequest) {
            //Cuidado aqui, el objeto XMLHttpRequest no esta disponible en versiones previas a IE7
            obj = new XMLHttpRequest();
        } else {
            return false;
        }
        obj.onreadystatechange = function () {
            if ( obj.readyState == 4 && (obj.status == 200 || window.location.href.indexOf ("http")==- 1)) {
                document.getElementById("contenido").innerHTML = obj.responseText;
            } else {
                document.getElementById("contenido").innerHTML = "<img src='cargando.gif'/></img>";
            }    
        };
        
        switch (accion) {
            case 1:obj.open("GET", "CtlConsulGrup?nombre=" + document.getElementById("nombre").value + "&precio=" + document.getElementById("precio").value + "&duracion=" + document.getElementById("duracion").value, true);
                break;
            case 2:obj.open("GET", "CtlInscrip?idCurso=" + getRadioButtonSelectedValue(idPrograma), true);
                break;
            case 3:obj.open("GET", "Prueba?nombre1=" + document.getElementById("nombre1").value + "&apPat=" + document.getElementById("apPat").value + "&apMat=" + document.getElementById("apMat").value, true);
                break;
            case 4:obj.open("GET", "CtlConsulGrup?nombre=&precio=0&duracion=0", true);
                break;
        }

        obj.send(null);
        return (true);
}
   
    function getRadioButtonSelectedValue (ctrl) {
        for(i=0;i<ctrl.length;i++)
            if(ctrl[i].checked) return ctrl[i].value;
    }
    
function Enviar2(URL, div, frm) {

        var obj = false;

        if (window.XMLHttpRequest) {
            //Cuidado aqui, el objeto XMLHttpRequest no esta disponible en versiones previas a IE7
            obj = new XMLHttpRequest();
        } else {
            return false;
        }
        obj.onreadystatechange = function () {
            if ( obj.readyState == 4 && (obj.status == 200 || window.location.href.indexOf ("http")==- 1)) {
                document.getElementById(div).innerHTML = obj.responseText;
            } else {
                document.getElementById(div).innerHTML = "<img src='cargando.gif'/></img>";
            }    
        };
        
        obj.open("GET", URL + "?" + getFormData(frm, 'silent', true), true);

        obj.send(null);
        return (true);
}    

function prueba () {
    document.getElementById("DatosAlumno").innerHTML = '<h1>' + getFormData('frmIniSesion', 'silent', true) + '</h1>';
}

function getFormData(objf, info, rval) {
    // La función getFormData recorre todos los elementos de un formulario
    // y va formando una cadena de formato "objeto=valor&objeto=valor&...".
    // Los campos del formulario para los que se haya especificado el
    // atributo TITLE, serán considerados campos obligatorios.
    //
    // formato: getFormData(objetoFormulario, tipoAvisoError, returnValue);
    // objetoFormulario: tiene que ser el OBJETO, NO el nombre del formulario
    // tipoAvisoError: silent: no muestra errores, si no se obtuvieron datos del formulario
    // alert: muestra un mensaje de alerta y detiene la ejecución, si no se obtuvieron los datos
    // returnValue: si debe devolver o no el resultado, true o false
    // los campos con el title vació no son alertados
    //
    // ejemplo: var queryStrign = getFormData('formularioId', 'silent', true);
    var formComplete = true;
    var alertMsg = "Debe completar los siguientes campos:\r";
    var getstr = "";
    var formObj =document.getElementById(objf);
    for (var i = 0; i < formObj.elements.length; i++) {
        
        if (formObj.elements[i].type != undefined && formObj.elements[i].name != undefined){
            var elemValLength = formObj.elements[i].value;
            // si algún campo para el envío de archivos cambia el enctype del form.
            if (formObj.elements[i].type == "file"){
                formObj.enctype = "multipart/form-data";
            }
            // chequea que todos los campos con atributo TITLE estén completos.
            if (formObj.elements[i].title != "" && elemValLength.length < 1) {
                alertMsg += " - " + formObj.elements[i].title + "\r";
                formComplete = false;
                continue;
            }
            // si es un checkbox, verifica que esté chequeado
            if (formObj.elements[i].type == "checkbox"){
                if (formObj.elements[i].checked == true){
                    getstr += formObj.elements[i].name + "=" + formObj.elements[i].value + "&";
                }
                continue;
            }
            // si es un radio, verifica que esté chequeado
            if (formObj.elements[i].type == "radio"){
                if (formObj.elements[i].checked == true){
                    getstr += formObj.elements[i].name + "=" + formObj.elements[i].value + "&";
                }
                continue;
            }
            if (elemValLength.length > 0) {
                getstr += formObj.elements[i].name + '=' + formObj.elements[i].value + '&';
            }
        }
    }
    if (!formComplete){
        if (info == 'alert'){
            alert(alertMsg);
        }
        return false;
    } else {
        if (rval){
            return getstr;
        } else {
            return true;
        }
    }
}

function cargarPagina(URL, div) {

        var obj = false;

        if (window.XMLHttpRequest) {
            //Cuidado aqui, el objeto XMLHttpRequest no esta disponible en versiones previas a IE7
            obj = new XMLHttpRequest();
        } else {
            return false;
        }
        obj.onreadystatechange = function () {
            if ( obj.readyState == 4 && (obj.status == 200 || window.location.href.indexOf ("http")==- 1)) {
                document.getElementById(div).innerHTML = obj.responseText;
            } else {
                document.getElementById(div).innerHTML = "<img src='cargando.gif'/></img>";
            }    
        };
        
        obj.open("GET", URL , true);
        obj.send(null);
}
   