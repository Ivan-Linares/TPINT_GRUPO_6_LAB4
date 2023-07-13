<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="dominio.Paciente"%>
<%@ page import="dominio.Pais"%>
<%@ page import="dominio.Cobertura"%>
<%@page import="java.util.ListIterator"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="dominio.Usuario"%>
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
		<span class="material-symbols-outlined">
			ecg_heart
		</span>
			<h3>Sistema Clínica</h3>
		</div>

		<div class="items">
			<ul>
			<%
			Usuario user = null;
			if(session.getAttribute("usuario") != null){
				 user = (Usuario)session.getAttribute("usuario");
				if(user.isEsAdministrador()){%>	
					<li>
					<a href="serverletsPacientes?method=get"  class="active">
						<span class="material-symbols-outlined">groups</span>
						Pacientes
					</a>
				</li>
				
				<li> 
				
				 	<a href="serverletsMedicos?method=get">
				 		<span class="material-symbols-outlined">clinical_notes</span>	
						Médicos
					</a>
				 </li>
								
				<li>
					<a href="servletsTurnos?method=get">				
						<span class="material-symbols-outlined">calendar_month</span>	
						Turnos
					</a>
				</li>
								<li>
					<a href="Reportes.jsp">				
						<span class="material-symbols-outlined">density_small</span>
						Reportes
					</a>
				</li>
				<%}
				else{%>	
									<li>
					<a href="servletsTurnos?method=get" class="active">				
						<span class="material-symbols-outlined">calendar_month</span>	
						Turnos
					</a>
				</li>
													<li>
					<a href="servletsTurnos?method=get" class="active">				
						<span class="material-symbols-outlined">calendar_month</span>	
						Mi Perfil
					</a>
				</li>
					<%}
			} %>
				
			</ul>
		</div>
		
		<div class="user-container">
			
			
			<%if(user != null){
				%>	
				<strong><%= user.getCorreo() %></strong>
			<%} %>
			<a href="serverletsLogin?method=get&btn-cerrar-sesion" class="btn bg-green">Cerrar Sesión</a>
		</div>
	</div>
		
		<%
		
		Paciente paciente = (Paciente)request.getAttribute("paciente"); 
		
		
		%>
		<div class="container fd-column m-auto" style="width:100%;
	    margin: 0 100px;" visible="<% if(paciente == null) {%> false <%}%>">	
	    	<div class="title-section d-flex jc-sb" >
			<h3 class="user-info-container">Datos Paciente</h3>
			<%if(request.getAttribute("idTurno") != null){ %>
			 <form action="servletsTurnos" method="post">
						<input type="hidden" name="idTurno" value="<%=Integer.parseInt(request.getAttribute("idTurno").toString())%>">								
								<button type="submit" name="btn-editar-turno" style="width:100%" class="btn bg-blue">Volver al Turno</button>
					</form>
			 <%}else{%>
			 <a href="serverletsPacientes?method=get&btn-cerrar-sesion" name="btn-volver" class="btn bg-blue">Volver al Listado</a>	
			 <%} %>
	</div>
	   
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
								<input type="hidden" required="true" name="dni" class="campo" value="<%= paciente.getDni()%>">
								<h3 class="campo"><%= paciente.getDni()%></h3>
							</div>
					
							<div class="d-flex fd-column">
								<label>Fecha de Nacimiento</label>
								<%if(request.getAttribute("editar-paciente") != null){%><input type="Date" required="true" name="fechaNacimiento" class="campo" value="<%= paciente.getFechaNacimiento().toString()%>"><%}
								else{%><p class="campo"><%= paciente.getFechaNacimiento().toString()%></p><%}%>
							</div>
							
							<div class="d-flex fd-column">
								<label>Sexo</label>
								<%if(request.getAttribute("editar-paciente") != null){%>
																<select name="sexoSelect" class="select">
									<option value="F" <%if(paciente.getSexo().contains("F")){%> selected="true"<%}%>>Femenino</option>
									<option value="M" <%if(paciente.getSexo().contains("M")){%> selected="true"<%}%>>Masculino</option>
									<option value="O" <%if(paciente.getSexo().contains("O")){%> selected="true"<%}%>>Otro</option>
								</select><%}
								else{
									String sexo = paciente.getSexo().toString();
									if(sexo.contains("F"))sexo = "Femenino";
									else if(sexo.contains("M"))sexo = "Masculino";
									else if(sexo.contains("O"))sexo = "Otro";
									else sexo = "";						
									%>
										<p class="campo"><%=sexo %></p>	
									<%}%>

							</div>						
						</div>
						
						<div class="d-flex row">
							<div class="d-flex fd-column">
								<label>Nombre</label>
								<%if(request.getAttribute("editar-paciente") != null){%><input type="text" required="true" name="nombre" class="campo" value="<%= paciente.getNombre()%>"><%}
								else{%><p class="campo"><%= paciente.getNombre()%></p><%}%>
								
							</div>
					
							<div class="d-flex fd-column">
								<label>Apellido</label>
								<%if(request.getAttribute("editar-paciente") != null){%><input type="text" required="true" name="apellido" class="campo" value="<%= paciente.getApellido()%>"><%}
								else{%><p class="campo"><%= paciente.getApellido()%></p><%}%>
								
							</div>
							
							<div class="d-flex fd-column">
								<label>Nacionalidad</label>
								<%
								ArrayList<Pais> listaPaises = new ArrayList<Pais>();
								if(request.getAttribute("listaPaises") != null){
									listaPaises = (ArrayList<Pais>)request.getAttribute("listaPaises");
								}
								if(request.getAttribute("editar-paciente") != null){%>
								<select name="nacionalidadSelect" class="select">
								
								<% 						
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
								<%}
								else{%><p class="campo"><%= paciente.getNacionalidad().getDescripcion()%></p><%}%>
								
							</div>
						</div>
						
						<div class="d-flex row">
							<div class="d-flex fd-column w-50">
								<label>Correo Electrónico</label>
								<%if(request.getAttribute("editar-paciente") != null){%><input type="mail" required="true" name="correo" class="campo" value="<%= paciente.getCorreo()%>"><%}
								else{%><p class="campo"><%= paciente.getCorreo()%></p><%}%>
								
							</div>
					
							<div class="d-flex fd-column w-50">
								<label>Telefono</label>
								<%if(request.getAttribute("editar-paciente") != null){%><input type="number" required="true" name="telefono" class="campo" value="<%= paciente.getTelefono().getTelefono()%>"><%}
								else{%><p class="campo"><%=paciente.getTelefono().getTelefono()%></p><%}%>
								
							</div>							
						</div>
					
						<div class="d-flex row">			
					</div>
					
					<div class="d-flex row">					
						<div class="d-flex fd-column">
							<label>Dirección</label>
							<%if(request.getAttribute("editar-paciente") != null){%><input type="Text" required="true"  name="direccion" class="campo" value="<%= paciente.getDomicilio().getDireccion()%>"><%}
								else{%><p class="campo"><%=paciente.getDomicilio().getDireccion()%></p><%}%>
							
						</div>
						
						<div class="d-flex fd-column">
							<label>Localidad</label>
							<%if(request.getAttribute("editar-paciente") != null){%><input type="Text" required="true" name="localidad"  class="campo" value="<%= paciente.getDomicilio().getLocalidad()%>"><%}
								else{%><p class="campo"><%=paciente.getDomicilio().getLocalidad()%></p><%}%>
						</div>
						
						<div class="d-flex fd-column">
							<label>Provincia</label>
							<%if(request.getAttribute("editar-paciente") != null){%><input type="Text" required="true" name="provincia"  class="campo" value="<%= paciente.getDomicilio().getProvincia()%>"><%}
								else{%><p class="campo"><%= paciente.getDomicilio().getProvincia()%></p><%}%>
						</div>				
					</div>	
					
					<div class="d-flex row">
					
												<div class="d-flex fd-column">
								<label>Pais</label>
									<%if(request.getAttribute("editar-paciente") != null){%>
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
									<%}
								else{%><p class="campo"><%=paciente.getDomicilio().getPais().getDescripcion().toString()%></p><%}%>
								
							</div>
					<div class="d-flex fd-column">
								<label>Cobertura</label>
								<%if(request.getAttribute("editar-paciente") != null){%>
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
				<%}%>
							</select>
								<%}
								else{%><p class="campo"><%=paciente.getCobertura().getDescripcion()%></p><%}%>
								
								
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