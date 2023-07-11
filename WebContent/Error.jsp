<%@ page import="dominio.Usuario"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<style>
<jsp:include page="css/StyleSheet.css"></jsp:include>
</style>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<title>Error</title>
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
				<li>
				
				<a href="serverletsPacientes?method=get">
				<span class="material-symbols-outlined">
					groups
				</span>
				Pacientes</a>
				</li>
				
				<li> 
				
				 <a href="Medicos.jsp">
				 	<span class="material-symbols-outlined">
						clinical_notes
					</span>	
					Médicos</a>
				 </li>
				
				<li>

					<a href="Turnos.jsp">				<span class="material-symbols-outlined">
					calendar_month
				</span>	Turnos</a>
				</li>
			</ul>
		</div>
		
		<div class="user-container">
			
			
			<%if(session.getAttribute("usuario") != null){
				Usuario user = (Usuario)session.getAttribute("usuario");
				%>	
				<strong><%= user.getCorreo() %></strong>
			<%} %>
			<a href="serverletsLogin?method=get" class="btn bg-green">Cerrar Sesión</a>
		</div>
	</div>
	<div>
    <div style="width:100%; margin: 40px 100px;">
    <form action="serverletsError" method="post">
		<h1>Error</h1>
	    <p style="color: red; font-size: 24px;"><strong>${mensaje}</strong></p>
	    <% String mensajeError = (String) request.getAttribute("mensajeError");
	    boolean errorDNI = false;
		   if (mensajeError != null) { 
			%>
			<h3 style="font-weight: bold; color: red; margin: 20px 0 20px 0;">
		      <%= mensajeError%>
		   </h3>
			<%
		   		if(request.getAttribute("errorDNI")!=null){
		   			errorDNI = (boolean) request.getAttribute("errorDNI"); 
		   		}
		   		if(errorDNI){
		   %>
		   <button type="submit" name="btn-error-DNI" class="btn bg-blue">Alta Paciente</button>
		   <%} %>
		   <button type="submit" name="btn-volver-alta-turnos" class="btn bg-blue">Volver a Turnos</button>
		   </form>
		<%
		}%>		    
    </div>
</div>
</div>
</body>
</html>