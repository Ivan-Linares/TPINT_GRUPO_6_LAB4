<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="dominio.Paciente"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Paciente</title>
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
		
		<%
		
		Paciente paciente = (Paciente)request.getAttribute("paciente"); 
		
		
		%>
		<div class="container fd-column m-auto" style="width:100%;
	    margin: 0px 100px;" visible="<% if(paciente == null) {%> false <%}%>">		
			<div>		
				<form method="post" action="serverletsPacientes">
					<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
					
						<div class="d-flex row">
							<div class="d-flex fd-column">
								<label>DNI</label>
								<input type="number" required="true" name="dni" class="campo" value="<%= paciente.getDni()%>">
							</div>
					
							<div class="d-flex fd-column">
								<label>Fecha de Nacimiento</label>
								<input type="Date" required="true" name="fechaNacimiento" class="campo" value="<%= paciente.getFechaNacimiento().toString()%>">
							</div>
							
							<div class="d-flex fd-column">
								<label>Sexo</label>
								<select name="sexoSelect" class="select">
									<option value="F" <%if(paciente.getSexo().contains("F")){%> selected="true"<%}%>>Femenino</option>
									<option value="M" <%if(paciente.getSexo().contains("M")){%> selected="true"<%}%>>Masculino</option>
									<option value="O" <%if(paciente.getSexo().contains("O")){%> selected="true"<%}%>>Otro</option>
								</select>
							</div>						
						</div>
						
						<div class="d-flex row">
							<div class="d-flex fd-column">
								<label>Nombre</label>
								<input type="text" required="true" name="nombre" class="campo" value="<%= paciente.getNombre()%>">
							</div>
					
							<div class="d-flex fd-column">
								<label>Apellido</label>
								<input type="text" required="true" name="apellido" class="campo" value="<%= paciente.getApellido()%>">
							</div>
							
							<div class="d-flex fd-column">
							Terminar de chequear como setear por defecto el item de la bd
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
								<input type="mail" required="true" name="correo" class="campo" value="<%= paciente.getCorreo()%>">
							</div>
					
							<div class="d-flex fd-column w-50">
								<label>Telefono</label>
								<input type="number" required="true" name="telefono" class="campo" value="<%= paciente.getTelefono().getTelefono()%>">
							</div>							
						</div>
					
						<div class="d-flex row">			
					</div>
					
					<div class="d-flex row">					
						<div class="d-flex fd-column">
							<label>Dirección</label>
							<input type="Text" required="true"  name="direccion" class="campo" value="<%= paciente.getDomicilio().getDireccion()%>">
						</div>
						
						<div class="d-flex fd-column">
							<label>Localidad</label>
							<input type="Text" required="true" name="localidad"  class="campo" value="<%= paciente.getDomicilio().getLocalidad()%>">
						</div>
						
						<div class="d-flex fd-column">
							<label>Provincia</label>
							<input type="Text" required="true" name="provincia"  class="campo" value="<%= paciente.getDomicilio().getProvincia()%>">
						</div>				
					</div>	
					
					<div class="d-flex row">
					
												<div class="d-flex fd-column">
								<label>Pais</label>
								<select name="paisSelect" class="select">
									<option value="1">Argentina</option>
									<option value="2">Bolivia</option>
									<option value="3">Peru</option>
								</select>
							</div>
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
					
					<%if(request.getAttribute("editarPaciente") != null){
					%>
					<button type="submit" name="btn-agregar-paciente" class="btn bg-green">Editar Paciente</button>
					<%}else{ %>
						<a href="serverletsPacientes?method=get" name="btn-volver" class="btn bg-green">Volver al Listado</a>
					<%} %>
				</form>
			</div>
		</div>
	</div>
	</body>
</html>