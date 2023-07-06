<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="dominio.Usuario"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insertar Telefono</title>
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
		<h1>Nuevo Telefono</h1>

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
		<button class="btn bg-blue w-100" type="submit" name="btn-ver-medico"> Volver Atrás</button>
		</form>
			 <% }%>
		
	</div>
	

		<form action="serverletsTelefono" method="post">
				<%if(idMedico != ""){
			%>
			 <input type="hidden" name="idMedico" value="<%=idMedico %>">
		<%} %>
		<%if(dniMedico != ""){
			%>
			 <input type="hidden" name="dniMedico" value="<%=dniMedico %>">
		<%} %>
			<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
			
				<div class="d-flex row">
					<div class="d-flex fd-column">
						<label>Telefono</label>
						<input type="number" required="true" name="telefono" class="campo">
					</div>	
				</div>
			</div>
			
			<button type="submit" name="btn-agregar-telefono" class="btn bg-green">Agregar Telefono</button>
		</form>
		
		<%if (request.getAttribute("estadoNuevoTelefono") != null) {
	String mensaje = request.getAttribute("estadoNuevoTelefono").toString();%>
	<br/>
			<h3 style="font-weight: bold; color: green; margin: 20px 0 20px 0;">
      <%= mensaje %>
   </h3>
	<%}%>
	</div>
	</div>
	
</body>
</html>