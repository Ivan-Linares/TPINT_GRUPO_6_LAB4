<%@ page import="java.util.ArrayList"%>
<%@ page import="dominio.Usuario"%>
<%@ page import="dominio.Turnos"%>
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
<title>Turnos</title>
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
				
				 <a href="serverletsMedicos?method=get">
				 	<span class="material-symbols-outlined">
						clinical_notes
					</span>	
					Médicos</a>
				 </li>
				
				
				<li>

					<a href="servletsTurnos?method=get" class="active">				<span class="material-symbols-outlined">
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


<div class="container fd-column m-auto">
	<div class="title-section d-flex jc-sb">
			<h1>Turnos</h1>
			<form action="servletsTurnos" method="get">
				<div class="filtro">
				<h3>Filtrar por:</h3>
				<select name="filtroTurnos" id="filtroTurnos">
					<option>Especialidad</option>
					<option>Medico</option>
					<option>Paciente</option>				
				</select>			
				<input type="text">
				<input type="submit" name="btnBuscar" class="btn bg-blue" value="Buscar"/> 
				<button type="submit" name="btn-nuevo-turno" class="btn bg-green">Agregar Turno</button>	
				</div>
			</form>
		</div>
		
	<%
	ArrayList<Turnos> listaTurnos = new ArrayList<Turnos>();
	
	if(request.getAttribute("listaTurnos") != null){
		Object obj = request.getAttribute("listaTurnos");
		if(obj instanceof ArrayList<?>){
			listaTurnos = (ArrayList<Turnos>) obj;
		}
		else{
			listaTurnos = null;
		}		
	}
	%>
	<% String mensaje = (String) request.getAttribute("mensajeExito");
   if (mensaje != null) { %>
   <h3 style="font-weight: bold; color: green; margin: 20px 0 20px 0;">
      <%= mensaje %>
   </h3>
<% } %>

<% String mensajeError = (String) request.getAttribute("mensajeError");
   String dniError = (String) request.getAttribute("dniError");
   if (mensajeError != null) { %>
   <h3 style="font-weight: bold; color: red; margin: 20px 0 20px 0;">
      <%= mensajeError%>
   </h3>
<% } %>
	
	<div>
		<table class="content-table header-table-blue"> 
			<thead> 
				<tr> 
					<th>ID Turno</th>
					<th>Fecha</th>
					<th>Hora</th> 
					<th>Medico</th> 
					<th>Especialidad</th> 
					<th>Paciente</th> 
					<th>Estado</th> 
					<th>Observacion</th> 
					<th>Acciones</th>
				</tr>
			</thead> 
			<tbody>
			<%if(listaTurnos != null) {%>
				<%for(Turnos turno : listaTurnos) {%>
					<tr> 
					<% String nombreClase ="bg-green" ; 
					String textButtonActivo ="Activo";
					if(!(turno.isActivo())){
						 nombreClase = "bg-red"; 
						 textButtonActivo ="Inactivo";
					}  
					%>
					<form action="servletsTurnos" method="post" class="<%=nombreClase%>">
						<td><strong><%=turno.getIdTurno() %></strong> <input type="hidden" name="idTurno" value="<%=turno.getIdTurno() %>"></td> 
						<td><%=turno.getFecha() %></td> 
						<td><%=turno.getHora() %>hs</td>
						<td><%=turno.getMedico().getNombre() %> <%=turno.getMedico().getApellido() %></td>
						<td><%=turno.getEspecialidad().getDescripcion() %></td> 
						<td><%=turno.getPaciente().getNombre() %> <%=turno.getPaciente().getApellido() %> </td> 
						<td><%if(turno.getEstado().getDescripcion() == null){ %>
							Sin Estado
						<%}else{ %>
							<%=turno.getEstado().getDescripcion() %>
						<%} %>
						</td> 
						<td><%if(turno.getObservacion() == null){ %>
							Sin Observaciones
						<%}else{ %>
							<%=turno.getObservacion() %>
						<%} %>
						</td>
						<td><button class="btn w-100 <%= nombreClase%>">
							 <%= textButtonActivo%>
							</button></td> 			
							<td class="d-flex">
								<button type="submit" name="btn-ver-turno" class="btn bg-blue">Ver</button>
								<button type="submit" name="btn-editar-turno" class="btn bg-green">Editar</button>
								<button type="submit" name="btn-eliminar-turno" class="btn bg-red" onclick="return confirm('Esta seguro que desea eliminar al turno ?');">Eliminar</button>
						 	</td>
					</form>
					</tr>
				<%} %>
			<%} %>
			</tbody> 
		</table>
	</div>
</div>
</div>

</body>
</html>