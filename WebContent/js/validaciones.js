$(document).ready(function() {
		  $('input[name="dni"]').blur(validarDNI);
		  $('input[name="fechaNacimiento"]').blur(validarFechaNacimiento);
		  $('input[name="nombre"]').blur(validarNombre);
		  $('input[name="apellido"]').blur(validarApellido);
		  $('input[name="correo"]').blur(validarCorreo);
		  $('input[name="password"]').blur(validarPassword);
		  $('input[name="telefono"]').blur(validarTelefono);
		  $('select[name="sexoSelect"]').blur(validarSelect);
		  $('select[name="nacionalidadSelect"]').blur(validarSelect);
		  $('select[name="paisSelect"]').blur(validarSelect);
		  $('select[name="coberturaSelect"]').blur(validarSelect);
		  $('select[name="especialidadSelect"]').blur(validarSelect);
		    
		  let esValido = true;
		  
		  function validarDNI() {
			  var dniInput = $(this);
			  var dniError = $('#dniError');

			  var dni = dniInput.val().trim();

			  if (dni.length !== 8 || isNaN(dni)) {
			    dniError.text('El DNI debe tener exactamente 8 números');
			    esValido = false;
			  } else {
			    dniError.text('');
			  }
			}

		  function validarFechaNacimiento() {
		    var fechaNacimiento = $(this);
		    var fechaNacimientoError = $('#fechaNacimientoError');

		    if (Date.parse(fechaNacimiento.val())) {
		      var fechaActual = new Date();
		      var fechaIngresada = new Date(fechaNacimiento.val());

		      if (fechaIngresada <= fechaActual && fechaIngresada.getFullYear() >= 1900) {
		        fechaNacimientoError.text('');
		      } else {
		        fechaNacimientoError.text('Ingrese una fecha de nacimiento valida');
		        esValido = false;
		      }
		    } else {
		      fechaNacimientoError.text('Ingrese una fecha de nacimiento valida');
		      esValido = false;
		    }
		  }

		  function validarNombre() {
		    var nombre = $(this);
		    var nombreError = $('#nombreError');

		    if (nombre.val().trim().length >= 2 && /^[a-zA-Z]+$/.test(nombre.val())) {
		      nombreError.text('');
		    } else {
		      nombreError.text('Ingrese un nombre valido (minimo 2 letras, sin caracteres especiales, sin numeros)');
		      esValido = false;
		    }
		  }

		  function validarApellido() {
		    var apellido = $(this);
		    var apellidoError = $('#apellidoError');

		    if (apellido.val().trim().length >= 2 && /^[a-zA-Z]+$/.test(apellido.val())) {
		      apellidoError.text('');
		    } else {
		      apellidoError.text('Ingrese un apellido valido (minimo 2 letras, sin caracteres especiales, sin numeros)');
		      esValido = false;
		    }
		  }

		  function validarCorreo() {
		    var email = $(this);
		    var emailError = $('#mailError');
		    var regexCorreo = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

		    if (regexCorreo.test(email.val())) {
		      emailError.text('');
		    } else {
		      emailError.text('Ingrese una direccion de correo electonico valida');
		      esValido = false;
		    }
		  }
		  
		  function validarPassword() {
			    var password = $(this);
			    var passwordError = $('#passwordError');
			    var passwordVerificacion = $('input[name="passwordVerificacion"]');
			    passwordError.text('');
			    if(password.val().length < 8){
			    	passwordError.text('La contraseña necesita mínimo 8 carácteres.');
			    	esValido = false;
			    }
			    if(password.val() != passwordVerificacion.val()){			    	
			    	passwordError.text('Las contraseñas ingresadas son diferentes.');
			    	esValido = false;
			    }
			  }
		  

		  
		  function validarPasswords() {			 
			    var password =  $('input[name="password"]');
			    var passwordError = $('#passwordError');
			    var passwordVerificacion = $('input[name="passwordVerificacion"]');
			    passwordError.text('');
			    if(password.val().length < 8){
			    	passwordError.text('La contraseña necesita mínimo 8 carácteres.');
			    	return false;
			    }
			    if(password.val() != passwordVerificacion.val()){
			    	passwordError.text('Las contraseñas ingresadas son diferentes.');
			    	return false;
			    }
			    
			    return true;
			  }
		  
		  function validarSelect(){
			  var select = $(this);
			  var selectError = $('#'+select.attr("name")+'Error');

			  if(select.val() < 0){
				  selectError.text('Seleccione una opción valida');
				  esValido = false;
			  }
			  else selectError.text('');
		  }
		  
		  
		  function validarTelefono() {
		    var telefono = $(this);
		    var telefonoError = $('#telefonoError');
		    var regexTelefono = /^\d+$/;

		    if (telefono.val().trim().length >= 7 && regexTelefono.test(telefono.val())) {
		      telefonoError.text('');
		    } else {
		      telefonoError.text('Ingrese un numero de telefono valido (minimo 7 dígitos, solo números)');
		      esValido = false;
		    }
		  }
		  
		  if(!esValido){
			event.preventDefault();
		  }
		  
		  var form = $("#formulario");
		  form.on("submit", function() { 
			  var formInvalid = false;
			  if($('select[name="sexoSelect"]').val() < 0) {
				  var selectError = $('#sexoSelectError');
				  selectError.text('Seleccione una opción valida');
				  formInvalid = true;
			  }		
			  if($('select[name="nacionalidadSelect"]').val() < 0) {
				  var selectError = $('#nacionalidadSelectError');
				  selectError.text('Seleccione una opción valida');
				  formInvalid = true;
			  }
			  
			  if($('select[name="paisSelect"]').val() < 0) {
				  var selectError = $('#paisSelectError');
				  selectError.text('Seleccione una opción valida');
				  formInvalid = true;
			  }
			  if($('select[name="coberturaSelect"]').val() < 0) {
				  var selectError = $('#coberturaSelectError');
				  selectError.text('Seleccione una opción valida');
				  formInvalid = true;			  
			  }
			  if($('select[name="especialidadSelect"]').val() < 0) {
				  var selectError = $('#especialidadSelectError');
				  selectError.text('Seleccione una opción valida');
				  formInvalid = true;			  
			  }
			  
			  if(formInvalid || !validarPasswords())event.preventDefault();
		  });
		  
		  function cambiarPassword() {

			  var passwordOriginal =  $('input[name="passwordOriginal"]');
			  var passwordAnterior =  $('input[name="passwordAnterior"]');
			  var passwordNueva =  $('input[name="passwordNueva"]');
			  var passwordNuevaValidacion =  $('input[name="passwordNuevaValidacion"]');
			  var passwordAnteriorError = $('#passwordAnteriorError');
			  var passwordNuevaError = $('#passwordNuevaError');
			  
			  passwordAnteriorError.text('');
			  passwordNuevaError.text('');
			  
			    if(passwordOriginal.val() != passwordAnterior.val()){
			    	passwordAnteriorError.text('La contraseña ingresada no coincide con la contraseña original.');
			    	return false;
			    }
			    
			    if(passwordNueva.val().length < 8){
			    	passwordNuevaError.text('La contraseña necesita mínimo 8 carácteres.');
			    	return false;
			    }
			    
			    if(passwordNueva.val() != passwordNuevaValidacion.val()){
			    	passwordNuevaError.text('Las contraseñas nuevas ingresadas no coinciden.');
			    	return false;
			    }
			    return true;
			  }
		  
		  
		  var formUsuario = $("#formularioUsuario");
		  formUsuario.on("submit", function() { 			
			  console.log("ntro");
			  if(!cambiarPassword())event.preventDefault();
		  });


		});

function modificarUsuario(){
	var labelPasswordOriginal =  $('label[name="lblPassword"]');
	  var passwordOriginal =  $('input[name="passwordOriginal"]');
	  var divModificarUsuario = $('div[name="div-modificar-usuario"]');
	  var btnModificarUsuario =  $('button[name="btn-modificar-usuario"]');
	  var passwordAnterior =  $('input[name="passwordAnterior"]');
	  var passwordNueva =  $('input[name="passwordNueva"]');
	  var btnGuardarUsuario =  $('button[name="btn-guardar-usuario-medico"]');
	  
	  labelPasswordOriginal.css('display', 'none');
	  passwordOriginal.css('display', 'none');
	  btnModificarUsuario.css('display', 'none');
	  
	  divModificarUsuario.css('display', 'block');
	  btnGuardarUsuario.css('display', 'block');
	  
	  event.preventDefault();
	}

