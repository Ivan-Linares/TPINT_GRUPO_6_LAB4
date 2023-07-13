<%@page import="dominio.Especialidad"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="dominio.Pais"%>
<%@ page import="dominio.Cobertura"%>
<%@page import="java.util.ListIterator"%>
<%@ page import="java.util.ArrayList"%>
    <%@ page import="dominio.Usuario"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Agregar Médico</title>
<style>
<jsp:include page="css/StyleSheet.css"></jsp:include>
</style>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script src="js/code.jquery.com_jquery-3.7.0.min.js"></script>	
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
					<a href="serverletsPacientes?method=get">
						<span class="material-symbols-outlined">groups</span>
						Pacientes
					</a>
				</li>
				
				<li> 
				
				 	<a href="serverletsMedicos?method=get" class="active">
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
					<a href="servletsTurnos?method=get">				
						<span class="material-symbols-outlined">calendar_month</span>	
						Turnos
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
	
	
	<div class="container fd-column m-auto" style="width:100%;
    margin: 0px 100px;">
	<div class="title-section d-flex jc-sb">
		<h3 class="user-info-container">Datos Médico</h3>
		 <a href="serverletsMedicos?method=get" name="btn-volver" class="btn bg-blue">Volver al Listado</a>	
	</div>
	
	<div>		
		<form method="post" action="serverletsMedicos" id="formulario">
		
		<%
		Integer idMedico = 0;
		if(request.getAttribute("idMedico")!= null) Integer.parseInt(request.getAttribute("idMedico").toString());
		%>
			<input type="hidden" name="IdMedico" value="<%=idMedico %>"> 
			
			<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
			
				<div class="d-flex row">
					<div class="d-flex fd-column">
						<label>DNI</label>
						<input type="number" required="true" name="dni" class="campo">
						<span id="dniError" class="error"></span>
					</div>
			
					<div class="d-flex fd-column">
						<label>Fecha de Nacimiento</label>
						<input type="Date" required="true" name="fechaNacimiento" class="campo">
						<span id="fechaNacimientoError" class="error"></span>
					</div>
					
					<div class="d-flex fd-column">
						<label>Sexo</label>
						<select name="sexoSelect" class="select">
							<option value="F">Femenino</option>
							<option  value="M">Masculino</option>
							<option  value="O">Otro</option>
						</select>
					</div>
					
				</div>
				
				<div class="d-flex row">
					<div class="d-flex fd-column">
						<label>Nombre</label>
						<input  type="text" required="true" name="nombre" class="campo">
						<span id="nombreError" class="error"></span>
					</div>
			
					<div class="d-flex fd-column">
						<label>Apellido</label>
						<input type="text" required="true" name="apellido" class="campo">
						<span id="apellidoError" class="error"></span>
					</div>
					
					<div class="d-flex fd-column">
						<span id="nacionalidadSelectError" class="error"></span>
						<label>Nacionalidad</label>
						<select name="nacionalidadSelect" class="select">
							<option value="-1">Escoge un Pais: </option>
								<% 
								ArrayList<Pais> listaPaises = new ArrayList<Pais>();
								if(request.getAttribute("listaPaises") != null){
									listaPaises = (ArrayList<Pais>)request.getAttribute("listaPaises");
								}
								
								ListIterator <Pais> it = listaPaises.listIterator();
												while(it.hasNext())
				{
					Pais pais = it.next();
				%>
				<option value="<%= pais.getIdPais()%>"><%= pais.getDescripcion() %></option>
				<%
				}%>
						</select>
					</div>
				</div>
				
			<div class="d-flex row">
				
					<div class="d-flex fd-column w-50"> 
					
						<label>Dirección</label>
								<input type="Text" required="true"  name="direccion" class="campo">
					</div>
				
								<div class="d-flex fd-column w-50">
								<label>Localidad</label>
<input type="Text" required="true" name="localidad"  class="campo">
				</div>
				

											
			</div>
			
							<div class="d-flex row">
											<div class="d-flex fd-column w-50">
								<label>Provincia</label>
<input type="Text" required="true" name="provincia"  class="campo">
				</div>
				<div class="d-flex fd-column w-50">
				<span id="paisSelectError" class="error"></span>
								<label>Pais</label>
								<select name="paisSelect" class="select">
								<option value="-1">Escoge un Pais: </option>
								<% 
								
								ListIterator <Pais> it2 = listaPaises.listIterator();
												while(it2.hasNext())
				{
					Pais pais = it2.next();
				%>
				<option value="<%= pais.getIdPais()%>"><%= pais.getDescripcion() %></option>
				<%
				}%>
								
								</select>
				</div>
			</div>
			<h3 class="user-info-container">Datos de Usuario</h3>
				<div class="d-flex row">
					<div class="d-flex fd-column w-50">
						<label>Correo Electrónico</label>
						<input type="mail" required="true" name="correo" class="campo">
						<span id="mailError" class="error"></span>
					</div>
				</div>
			
				<div class="d-flex row">
					<div class="d-flex fd-column w-50">
						<label>Contraseña</label>
						<input  type="password" required="true" name="password" class="campo">
						<span id="passwordError" class="error"></span>
					</div>
				</div>
				
				<div class="d-flex row">
					<div class="d-flex fd-column w-50">
						<label>Verificar Contraseña</label>
						<input  type="password" required="true" name="passwordVerificacion" class="campo">
						<span id="contraseñaError" class="error"></span>
					</div>
				</div>
			<button type="submit" name="btn-agregar-medico" class="btn bg-green">Agregar Médico</button>
		</form>
	</div>
	
	

	</div>
	

	</div>
	<script src="js/validaciones.js"></script>	
</body>
</html>