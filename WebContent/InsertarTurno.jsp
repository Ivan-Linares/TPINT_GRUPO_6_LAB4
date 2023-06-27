<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="dominio.Paciente"%>
<%@ page import="dominio.Especialidad"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="java.util.ListIterator"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Agregar Turno</title>
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
		
		<div class="container fd-column m-auto" style="width:100%;
	    margin: 0px 100px;">		
			<div>		
					<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
					
					<form action="servletsTurnos" method="post">
						<div class="d-flex fd-column">
							<label>DNI</label>
							<input type="number" class="campo" name="dniPaciente">
							<input type="submit" name="btn-buscar-dni" class="btn bg-green" value="Buscar">
						</div>
					</form>
					
					<% 
					boolean encontro = false; 
					Paciente paciente = (Paciente)request.getAttribute("paciente");
					String dni = (String)request.getAttribute("dni");
					
					if(request.getAttribute("encontroDNI") != null){
						if((boolean)request.getAttribute("encontroDNI") == false){
							%>
							<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
								<label>DNI <%=dni %> NO EXISTENTE</label>
								<button type="submit" name="btn-agregar-paciente" class="btn bg-green">Agregar Paciente</button>
							</div>
							<%
						}else{
							%>
							<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
								<div class="d-flex row">
									<label>DNI</label>
									<label>Nombre</label>
									<label>Apellido</label>
									<label>Fecha Nacimiento</label>
								</div>
								<div class="d-flex row">
									<input type="number" disabled="disabled" class="campo" name="dniPaciente" value=<%=paciente.getDni() %>>
									<input type="text" disabled="disabled" class="campo" name="nombrePaciente" value=<%=paciente.getNombre() %>>
									<input type="text" disabled="disabled" class="campo" name="apellidoPaciente" value=<%=paciente.getApellido() %>>
									<input type="text" disabled="disabled" class="campo" name="fechaNacPaciente" value=<%=paciente.getFechaNacimiento() %>>
								</div>
							</div>
							<%
						}
						
					}
					%>
						<div class="d-flex row">	
							<form action="servletsTurnos" method="post">
								<div class="d-flex fd-column">
									<label>Especialidades</label>
									<select name="especialidadSelect" class="select">
										<option value="-1">Selecciona una especialidad</option>
										<%
										ArrayList<Especialidad> listaEspecialidades = new ArrayList<Especialidad>();
										if(request.getAttribute("listaEspecialidades") != null){
											listaEspecialidades = (ArrayList<Especialidad>)request.getAttribute("listaEspecialidades");
										}
										
										ListIterator <Especialidad> it = listaEspecialidades.listIterator();
										while(it.hasNext()){
											Especialidad esp = it.next();
										%>
										<option value="<%=esp.getIdEspecialidad() %>"><%=esp.getDescripcion() %></option>
										<%
										}%>
									</select>
								</div>	
							</form>
							
							<div class="d-flex fd-column">
								<label>Medico</label>
								<select name="medico" class="select">
									<option>Mario Gutierrez</option>
									<option>Carla Montenegro</option>		
									<option>Mariana Cantero</option>							
								</select>
							</div>					
						</div>					
						
						<div class="d-flex row">
							<div class="d-flex fd-column">
								<label>Fecha</label>
								<input type="date" class="campo" >
							</div>				
							
							<div class="d-flex fd-column">
								<label>Hora</label>
								<input type="number" class="campo" >
							</div>
							
							<div class="d-flex fd-column">
								<label>Observaciones</label>
								<input type="text" class="campo" >
							</div>				
						</div>
					
					</div>		
			</div>
		</div>
	</div>
	</body>
</html>