<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="dominio.Paciente"%>
<%@ page import="dominio.Especialidad"%>
<%@ page import="dominio.Medico"%>
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
					 	<a href="serverletsMedicos?method=get" class="active">
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
						<div>	
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
								<div class="d-flex fd-column">
									<button type="submit" name="btn-buscar-medicos" class="btn bg-blue">Buscar Medicos</button>
								</div>
								<input type="hidden" class="campo" >
							<%
								ArrayList<Medico> listaMedicos = new ArrayList<Medico>();
								if(request.getAttribute("listaMed") != null){
									listaMedicos = (ArrayList<Medico>)request.getAttribute("listaMed");
									%>
									<div class="d-flex fd-column">
										<label>Medico</label>
										<select name="medico" class="select">	
									<%
								}
										
								ListIterator <Medico> it2 = listaMedicos.listIterator();
								while(it2.hasNext()){
									Medico med = it2.next();
								%>
										<option value="<%=med.getIdMedico() %>"><%=med.getNombre() %> <%=med.getApellido() %></option>
								<%}%>
										</select>
									</div>
						</div>		
							
							<%
							if(request.getAttribute("listaMed") != null){
								String dniPaciente = (String)request.getAttribute("dni");
								String idEsp = (String)request.getAttribute("espSelect");
								String descripcion = (String)request.getAttribute("descripcionEsp");
							%>
							<div class="d-flex row">
							<a>ESPECIALIDAD ELEGIDA: <%=descripcion %></a>
							</div>
							<input type="hidden" class="campo" name="espSelect" value="<%=idEsp %>">
							<input type="hidden" class="campo" name="descripcionEsp" value="<%=descripcion %>">
							
							<div class="d-flex row">
								<div class="d-flex fd-column">
									<label>DNI</label>
									<input type="number" class="campo" name="dniPaciente" value="<%=dniPaciente %>">
								</div>
							</div>
							
							<div class="d-flex row">
								<div class="d-flex fd-column">
									<label>Fecha</label>
									<input type="date" class="campo" name="fecha">
								</div>				
								
								<div class="d-flex fd-column">
									<label>Hora</label>
									<input type="number" class="campo" name="hora">
								</div>
								
								<div class="d-flex fd-column">
									<label>Observaciones</label>
									<input type="text" class="campo" disabled="disabled" name="observaciones">
								</div>				
							</div>
							<button type="submit" name="btn-agregar-turno" class="btn bg-green">Agregar Turno</button>
							<% }%>						
						</form>
						
					</div>
			</div>
		</div>
	</div>
	</body>
</html>