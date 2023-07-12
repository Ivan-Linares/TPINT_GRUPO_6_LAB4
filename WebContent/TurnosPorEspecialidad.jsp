<%@ page import="dominio.Usuario"%>
<%@ page import="dtos.TurnosPorEspecialidadDTO"%>
<%@ page import="java.util.ArrayList"%>

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
<title>Turnos por especialidad</title>
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
			<a href="serverletsLogin?method=get&btn-cerrar-sesion" class="btn bg-green">Cerrar Sesión</a>
		</div>
	</div>
	<div class="container fd-column m-auto">
		<div class="title-section d-flex jc-sb">
			<a href="Reportes.jsp" class="btn bg-green">Volver</a>
			
			
			<form method="get" action="serverletsTurnosPorEspecialidad">
				<div class="d-flex fd-column">
					<label>Fecha inicio</label>
					<input type="Date" required="true" name="fechaInicio" class="campo">
					<span id="fechaNacimientoError" class="error"></span>
				</div>
				<div class="d-flex fd-column">
					<label>Fecha fin</label>
					<input type="Date" required="true" name="fechaFin" class="campo">
					<span id="fechaNacimientoError" class="error"></span>
				</div>
				<button type="submit" name="btn-buscar" class="btn bg-green">Confirmar fechas</button>
			</form>
		</div>
		
		<table class="content-table header-table-blue"  id="tablaPacientes" style="margin: 2rem 0;"> 
			<thead> 
				<tr>
					<th>Especialidad</th>
					<th>Cantidad de Turnos</th>					
				</tr>
			</thead> 
			<tbody>
    <% 
    ArrayList<TurnosPorEspecialidadDTO> listaTurnos = (ArrayList<TurnosPorEspecialidadDTO>)request.getAttribute("listaTurnos");
    if (listaTurnos != null) {
        for (TurnosPorEspecialidadDTO turno : listaTurnos) {
    %>
    <tr>
        <td><%= turno.getNombreEspecialidad() %></td>
        <td><%= turno.getCantidadTurnos() %></td>
    </tr>
    <% 
        }
    }
    %>
</tbody>

		</table>
		
	</div>

</div>
<script src="js/validaciones.js"></script>	
</body>
</html>