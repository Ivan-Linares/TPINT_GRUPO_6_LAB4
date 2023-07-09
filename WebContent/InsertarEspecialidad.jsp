<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="dominio.Usuario"%>
<%@page import="dominio.Especialidad"%>
<%@page import="java.util.ListIterator"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insertar Especialidad</title>
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

<div class="container fd-column m-auto w-100"  style="padding: 0px 40px">	
		<div class="title-section d-flex jc-sb">
		<h1>Nueva Especialidad</h1>

		<% 
	String idMedico = ""; 
	String dniMedico = ""; 
	if(request.getAttribute("idMedico") != null){
		idMedico = request.getAttribute("idMedico").toString();
	}		 
	if(request.getAttribute("dniMedico") != null){			 
		dniMedico = request.getAttribute("dniMedico").toString();%>
		<form action="serverletsMedicos" method="post">
		<input type="hidden" name="dniMedico" value="<%=dniMedico %>">  
		<button class="btn bg-blue w-100" type="submit" name="btn-ver-medico"> Volver Atrás</button>
		</form>
	<%}%>
	</div>
	

		<form action="serverletsEspecialidades" method="post">
		<%if(idMedico != ""){%>
			 <input type="hidden" name="idMedico" value="<%=idMedico %>">
		<%} 
		  if(dniMedico != ""){%>
			 <input type="hidden" name="dniMedico" value="<%=dniMedico %>">
		<%} %>
			<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
			
				<div class="d-flex row">
					<div class="d-flex fd-column">
					<label>Especialidades</label>
					<select name="especialidadSelect" class="select">
						<option value="-1">Selecciona una especialidad</option>
						<%
						ArrayList<Especialidad> listaEspecialidades = new ArrayList<Especialidad>();
						if(request.getAttribute("listaEspecialidades") != null){
							listaEspecialidades = (ArrayList<Especialidad>)request.getAttribute("listaEspecialidades");
						}
						
						ListIterator <Especialidad> it3 = listaEspecialidades.listIterator();
						while(it3.hasNext()){
							Especialidad esp = it3.next();
						%>
						<option value="<%=esp.getIdEspecialidad() %>"><%=esp.getDescripcion() %></option>
						<%
						}%>
					</select>
				</div>
				</div>
			</div>
			
			<button type="submit" name="btn-agregar-especialidad" class="btn bg-green">Asignar Especialidad</button>
		</form>
		
		<%if (request.getAttribute("estado") != null) {
		String mensaje = request.getAttribute("estado").toString();%>
			<br/>
			<h3 style="font-weight: bold; color: green; margin: 20px 0 20px 0;"><%= mensaje %></h3>
		<%}%>
	</div>

</div>
</body>
</html>