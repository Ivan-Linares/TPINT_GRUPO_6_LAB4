<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="dominio.Usuario"%>
<%@ page import="dominio.Medico"%>
<%@ page import="dominio.Turnos"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<style>
<jsp:include page="css/StyleSheet.css"></jsp:include>
</style>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script src="js/code.jquery.com_jquery-3.7.0.min.js"></script>	
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
			Medico medico = null;
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
				else{
					
			if(session.getAttribute("medicoUsuario") != null) medico =(Medico)session.getAttribute("medicoUsuario");
			String dniMedico = "";
			dniMedico = medico.getDni();
			int idMedico = 0;
			idMedico = medico.getIdMedico();
			String url = "serverletsMedicos?method=post&dniMedico="+dniMedico+"&idMedico="+idMedico+ "&btn-ver-medico=";
			%>	
									<li>
					<a href="servletsTurnos?method=get">				
						<span class="material-symbols-outlined">calendar_month</span>	
						Turnos
					</a>
				</li>
								<li>
					<form method="post" action="serverletsMedicos">	
								<input type="hidden" name="dniMedico" value="<%=dniMedico%>">
								<input type="hidden" name="idMedico" value="<%=idMedico%>">
											<button class="btn-a" name="btn-ver-medico">				
						<span class="material-symbols-outlined">account_circle</span>	
						Mi Perfil
					</button>
					</form>
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
		Turnos turno = (Turnos)request.getAttribute("turno"); 	
		%>
	
	<div class="container fd-column m-auto" style="width:100%;
    margin: 0px 100px; margin-bottom:60px" visible="<% if(turno == null) {%> false <%}%>">
	<div class="title-section d-flex jc-sb" >
			<h3 class="user-info-container">Datos Turno</h3>
		 <a href="servletsTurnos?method=get" name="btn-volver" class="btn bg-blue">Volver al Listado</a>	
	</div>
	
	    <br>
	    <% String mensajeModifico = "";
	    if(request.getAttribute("mensaje") != null) mensajeModifico = (String) request.getAttribute("mensaje");
 		 if (mensajeModifico != "") { %>
   		<h3 style="font-weight: bold; color: green; margin: 20px 0 20px 0;">
      		<%= mensajeModifico %>
  		</h3>
   		<%} %>
	    
	<div>		
	
	<form method="post" action="servletsTurnos" class="position-relative">
	<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
	<div class="d-flex row">	
			<div class="d-flex fd-column">
				<%if(request.getAttribute("editar-turno") != null){%>
				<input type="hidden" required="true" name="idTurno" class="campo" value="<%= turno.getIdTurno() %>">
				<%}
				else{%>
					<label>ID Turno</label>
					<p class="campo" name="medico"><%= turno.getIdTurno() %></p>	
					<%}%>
						
				<span id="medicoError" class="error"></span>
			</div>
	</div>
		<div class="d-flex row">
		
			<div class="d-flex fd-column">
				<label>Medico</label>
				<%if(request.getAttribute("editar-turno") != null){%>
				<input disabled="disabled" type="text" required="true" name="medico" class="campo" value="<%= turno.getMedico().getNombre() %> <%=turno.getMedico().getApellido()%>">
				<%}
				else{%>
					<p class="campo" name="medico"><%= turno.getMedico().getNombre() %> <%=turno.getMedico().getApellido() %></p>	
					<%}%>
						
				<span id="medicoError" class="error"></span>
			</div>
					
			<div class="d-flex fd-column">
				<label>Especialidad</label>
				<%if(request.getAttribute("editar-turno") != null){%>
				<input disabled="disabled" type="text" required="true" name="especialidad" class="campo" value="<%= turno.getEspecialidad().getDescripcion() %> ">
				<%}
				else{%>
					<p class="campo" name="especialidad"><%= turno.getEspecialidad().getDescripcion() %></p>	
					<%}%>
						
				<span id="horaTurnoError" class="error"></span>
			</div>
			<div class="d-flex fd-column">
				<label>Datos Paciente</label>
				<input type="hidden" required="true" name="dni" class="campo" value="<%=turno.getPaciente().getDni() %>">
				<p class="campo" name="dni"><%=turno.getPaciente().getDni()%> - <%=turno.getPaciente().getNombre() %> <%=turno.getPaciente().getApellido() %></p>					
			</div>
		</div>
		<div class="d-flex row">
			
			<div class="d-flex fd-column w-50">
				<label>Fecha</label>
				<%if(request.getAttribute("editar-turno") != null){%><input  type="Date" required="true" name="fecha" class="campo" value="<%= turno.getFecha() %>"><%}
				else{%><p class="campo" name="fecha"><%= turno.getFecha() %></p><%}%>
				<span id="fechaError" class="error"></span>
			</div>
					
			<div class="d-flex fd-column w-50">
				<label>Hora</label>
				<%if(request.getAttribute("editar-turno") != null){%><input  type="number" required="true" name="hora" class="campo" value="<%= turno.getHora() %>"><%}
				else{%><p class="campo" name="hora"><%= turno.getHora() %></p><%}%>
				<span id="horaError" class="error"></span>
			</div>
		</div>
		
				<div class="d-flex row">

			<div class="d-flex fd-column w-100">
				<label>Observacion</label>
				<%if(request.getAttribute("editar-turno") != null){%><input  type="text" name="observaciones" class="campo" value="<%= turno.getObservacion() %>">
				<%}
				else{%><p class="campo" name="observaciones"><%=turno.getObservacion()%></p><%
				}%>
				<span id="observacionError" class="error"></span>
			</div>
					
		</div>
	</div>
			
		<%if(request.getAttribute("editar-turno") != null){
		%>
		<button type="submit" name="btn-guardar-turno" class="btn bg-green position-absolute" style="right:0;">Guardar Cambios</button>
		<%} %>
			
	</form>
	</div>
	</div>
</div>
<script src="js/validaciones.js"></script>	
</body>
</html>