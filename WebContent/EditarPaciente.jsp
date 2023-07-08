<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="dominio.Paciente"%>
<%@ page import="dominio.Pais"%>
<%@ page import="dominio.Cobertura"%>
<%@page import="java.util.ListIterator"%>
<%@ page import="java.util.ArrayList"%>
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
	    margin: 40px 100px;" visible="<% if(paciente == null) {%> false <%}%>">	
	    <a href="serverletsPacientes?method=get" name="btn-volver" class="btn bg-blue">Volver al Listado</a>	
	    <br>
	    <% String mensaje = "";
	    if(request.getAttribute("mensaje") != null) mensaje = (String) request.getAttribute("mensaje");
   if (mensaje != "") { %>
   <h3 style="font-weight: bold; color: green; margin: 20px 0 20px 0;">
      <%= mensaje %>
   </h3>
   <%} %>
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
								<label>Nacionalidad</label>
								<select name="nacionalidadSelect" class="select">
								
								<% 
								ArrayList<Pais> listaPaises = new ArrayList<Pais>();
								if(request.getAttribute("listaPaises") != null){
									listaPaises = (ArrayList<Pais>)request.getAttribute("listaPaises");
								}
								
								ListIterator <Pais> it = listaPaises.listIterator();
												while(it.hasNext())
				{
					Pais pais = it.next();
					String paisPaciente= paciente.getDomicilio().getPais().getDescripcion().toString();
					String paisSelect = pais.getDescripcion().toString();
				%>
				<option value="<%= pais.getIdPais()%>"  <%if(paisPaciente.equals(paisSelect)){%> selected="true"<%}%> ><%= pais.getDescripcion() %></option>
				<%
				}%>				
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
								<% 
								
								ListIterator <Pais> it2 = listaPaises.listIterator();
												while(it2.hasNext())
				{
					Pais pais = it2.next();
					String paisPaciente= paciente.getDomicilio().getPais().getDescripcion().toString();
					String paisSelect = pais.getDescripcion().toString();
				%>
				<option value="<%= pais.getIdPais()%>" <%if(paisPaciente.equals(paisSelect)){ %> selected="true" <%}%>><%= pais.getDescripcion() %></option>
				<%
				}%>							
								</select>
							</div>
					<div class="d-flex fd-column">
								<label>Cobertura</label>
								<select name="coberturaSelect" class="select">
									<option value="-1">Escoge una Cobertura: </option>
								<% 
								ArrayList<Cobertura> listaCoberturas = new ArrayList<Cobertura>();
								if(request.getAttribute("listaCoberturas") != null){
									listaCoberturas = (ArrayList<Cobertura>)request.getAttribute("listaCoberturas");
								}
								
								ListIterator <Cobertura> it3 = listaCoberturas.listIterator();
												while(it3.hasNext())
				{
					Cobertura cobertura = it3.next();
					String coberturaPaciente = paciente.getCobertura().getDescripcion();
					String coberturaSelect = cobertura.getDescripcion();
				%>
				<option value="<%= cobertura.getId()%>"  <%if(coberturaPaciente.equals(coberturaSelect)){%> selected="true"<%}%>><%= cobertura.getDescripcion() %></option>
				<%
				}%>
								
								
								</select>
							</div>
							</div>	
					
					</div>					
					
					<%if(request.getAttribute("editar-paciente") != null){
					%>
					<button type="submit" name="btn-guardar-paciente" class="btn bg-green">Guardar Cambios</button>
					<%} %>
				</form>
			</div>
		</div>
	</div>
	</body>
</html>