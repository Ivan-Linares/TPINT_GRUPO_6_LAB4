<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="dominio.Usuario"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insertar Horario</title>
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
					<a href="Turnos.jsp">				
						<span class="material-symbols-outlined">calendar_month</span>	
						Turnos
					</a>
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


<div class="container fd-column m-auto w-100" style="padding: 0px 40px">	
		<div class="title-section d-flex jc-sb">
		<h1>Nuevo Horario</h1>

	<% 
	String idMedico = ""; 
	String dniMedico = ""; 
	if(request.getAttribute("idMedico") != null){
			 idMedico = request.getAttribute("idMedico").toString();
	}		 
			 if(request.getAttribute("dniMedico") != null){			 
				 dniMedico = request.getAttribute("dniMedico").toString();
			 %>
			 <form action="serverletsMedicos" method="post">
			<input type="hidden" name="dniMedico" value="<%=dniMedico %>">  
				<input type="hidden" name="idMedico" value="<%=idMedico %>"> 
		<button class="btn bg-blue w-100" type="submit" name="btn-editar-medico"> Volver Atrás</button>
		</form>
			 <% }%>
		
	</div>
	
		<form method="post" action="serverletsHorariosMedico">
		
		<%if(idMedico != ""){
			%>
			 <input type="hidden" name="idMedico" value="<%=idMedico %>">
		<%} %>
		<%if(dniMedico != ""){
			%>
			 <input type="hidden" name="dniMedico" value="<%=dniMedico %>">
		<%} %>
			<div class="d-flex fd-column style-form" style="padding: 50px 0px;">
			
				<div class="d-flex row">
					<div class="d-flex fd-column">
						<label>Día</label>
						<select name="dia" class="select">
									<option value="Lunes">Lunes</option>
									<option value="Martes">Martes</option>		
									<option value="Miércoles">Miércoles</option>				
									<option value="Jueves">Jueves</option>		
									<option value="Viernes">Viernes</option>	
									<option value="Sábados">Sábado</option>		
									<option value="Domingo">Domingo</option>				
								</select>
					</div>	
					<div class="d-flex fd-column">
						<label>Hora Entrada</label>
						<input type="time"  id="horaDesde"  required="true" name="horaEntrada" class="campo">
					</div>	
					<div class="d-flex fd-column">
						<label>Hora Salida</label>
						<input type="time"  id="horaHasta" required="true" name="horaSalida" class="campo">
					</div>	
				</div>
			</div>
			
			<button type="submit" name="btn-agregar-horario-trabajo" class="btn bg-green">Agregar Horario</button>
		</form>
		
	<%if (request.getAttribute("estadoNuevoHorario") != null) {
	String mensaje = request.getAttribute("estadoNuevoHorario").toString();%>
	
	<span> <%= mensaje %></span>
	<%}%>
	</div>
	
	</div>
</body>
</html>