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
<title>Reportes</title>
</head>
<body>

<div class="container">
	<div class="navbar">
		<div class="nav-title">
		<span class="material-symbols-outlined">
			ecg_heart
		</span>
			<h3>Sistema Cl�nica</h3>
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
				
				 	<a href="serverletsMedicos?method=get" >
				 		<span class="material-symbols-outlined">clinical_notes</span>	
						M�dicos
					</a>
				 </li>
								
				<li>
					<a href="servletsTurnos?method=get">				
						<span class="material-symbols-outlined">calendar_month</span>	
						Turnos
					</a>
				</li>
				
				<li>
					<a href="Reportes.jsp"  class="active">				
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
				
																	<li>
					<a href="servletsTurnos?method=get">				
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
			<a href="serverletsLogin?method=get&btn-cerrar-sesion" class="btn bg-green">Cerrar Sesi�n</a>
		</div>
	</div>
	
	<div class="container fd-column m-auto" style="width:100%; padding:20px">
	<div class="title-section d-flex jc-sb" style="flex-direction:column;gap:1rem;">
	<h1>Reportes</h1>
	<p>Seleccione el Reporte a generar</p>
	</div>
	
		<div style="margin-top:50px; display:flex; flex-direction:column; gap: 1rem; ">
		<a href="TurnosPorEspecialidad.jsp" class="btn bg-green" style="align-self:start;">Cantidad de turnos por especialidad</a>
		<a href="TurnosPorMedico.jsp" class="btn bg-blue" style="align-self:start;">Cantidad de turnos por m�dico</a>

	</div>
</div>
</div>



</body>
</html>