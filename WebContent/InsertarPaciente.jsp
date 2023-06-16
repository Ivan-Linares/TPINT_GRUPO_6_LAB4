<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Agregar Paciente</title>
	<style>
	<jsp:include page="css/StyleSheet.css"></jsp:include>
	</style>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<body>
	<div class="container">
		<div class="navbar">
			<div class="nav-title">
				<span class="material-symbols-outlined">ecg_heart</span>
				<h3>Sistema Clínica</h3>
			</div>
	
			<div class="items">
				<ul>
					<li>					
						<a href="Pacientes.jsp">
							<span class="material-symbols-outlined">groups</span>Pacientes
						</a>
					</li>
					
					<li>					
					 	<a href="Medicos.jsp" class="active">
					 		<span class="material-symbols-outlined">clinical_notes</span>Médicos
					 	</a>
					 </li>
					
					
					<li>
						<a href="Turnos.jsp">
						<span class="material-symbols-outlined">calendar_month</span>Turnos</a>
					</li>
				</ul>
			</div>
		</div>		
		
		<div class="container fd-column m-auto" style="width:100%;
	    margin: 0px 100px;">		
			<div>		
				<form method="post" action="serverletsPacientes">
					<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
					
						<div class="d-flex row">
							<div class="d-flex fd-column">
								<label>DNI</label>
								<input type="number" required="true" name="dni" class="campo">
							</div>
					
							<div class="d-flex fd-column">
								<label>Fecha de Nacimiento</label>
								<input type="Date" required="true" name="fechaNacimiento" class="campo">
							</div>
							
							<div class="d-flex fd-column">
								<label>Sexo</label>
								<select name="sexoSelect" class="select">
									<option value="F">Femenino</option>
									<option value="M">Masculino</option>
									<option value="O">Otro</option>
								</select>
							</div>						
						</div>
						
						<div class="d-flex row">
							<div class="d-flex fd-column">
								<label>Nombre</label>
								<input type="text" required="true" name="nombre" class="campo">
							</div>
					
							<div class="d-flex fd-column">
								<label>Apellido</label>
								<input type="text" required="true" name="apellido" class="campo">
							</div>
							
							<div class="d-flex fd-column">
								<label>Nacionalidad</label>
								<select name="nacionalidadSelect" class="select">
									<option value="1">Argentina</option>
									<option value="2">Bolivia</option>
									<option value="3">Peru</option>
								</select>
							</div>
						</div>
						
						<div class="d-flex row">
							<div class="d-flex fd-column w-50">
								<label>Correo Electrónico</label>
								<input type="mail" required="true" name="correo" class="campo">
							</div>
					
							<div class="d-flex fd-column w-50">
								<label>Telefono</label>
								<input type="number" required="true" name="telefono" class="campo">
							</div>							
						</div>
					
						<div class="d-flex row">			
					</div>
					
					<div class="d-flex row">					
						<div class="d-flex fd-column">
							<label>Dirección</label>
							<input type="Text" required="true"  name="direccion" class="campo">
						</div>
						
						<div class="d-flex fd-column">
							<label>Localidad</label>
							<input type="Text" required="true" name="localidad"  class="campo">
						</div>
						
						<div class="d-flex fd-column">
							<label>Provincia</label>
							<input type="Text" required="true" name="provincia"  class="campo">
						</div>				
					</div>	
					
					<div class="d-flex row">
					<div class="d-flex fd-column">
								<label>Cobertura</label>
								<select name="coberturaSelect" class="select">
									<option value="1">Ejemplo 1</option>
									<option value="2">Ejemplo 2</option>
									<option value="3">Etc</option>
								</select>
							</div>
							</div>	
					
					</div>					
					
					<button type="submit" name="btn-agregar-paciente" class="btn bg-green">Agregar Paciente</button>
				</form>
			</div>
		</div>
	</div>
	</body>
</html>