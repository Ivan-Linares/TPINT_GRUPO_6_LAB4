<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="dominio.Usuario"%>
<%@ page import="dominio.Medico"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Médicos</title>

<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
	
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#tablaMedicos').DataTable();
	});
</script>

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
	
	
	<div class="container fd-column m-auto">
	<div class="title-section d-flex jc-sb">
		<h1>Médicos</h1>
		
		<div class="filtro">
		<h3>Filtrar por:</h3>
		<select name="filtroMedicos" id="filtroMedicos">
			<option>Nombre</option>
			<option>Apellido</option>
			<option>Día de Atención</option>
			<option>Horario de Atención</option>
			<option>Especialidad</option>
		</select>
		
		<input type="text">
		
		<a href="InsertarMedico.jsp" class="btn bg-green">Agregar Médico</a>
		</div>
	</div>
	
	<%
		ArrayList<Medico> listaMedicos = new ArrayList<Medico>();

		if(request.getAttribute("listaMedicos") != null){
			Object obj = request.getAttribute("listaMedicos");
			if(obj instanceof ArrayList<?>){
				listaMedicos = (ArrayList<Medico>) obj;
			}
			else{
				listaMedicos = null;
			}		
		}
	%>
	
	<div>
		<table class="content-table header-table-blue" id="tablaMedicos"> 
			<thead> 
				<tr> 
					<th>DNI</th>
					<th>Nombre</th>
					<th>Apellido</th> 
					<th>Día de Atención</th> 
					<th>Horario de Atención</th> 
					<th>Especialidad</th> 
					<th>Acciones</th> 
				</tr>
			</thead> 
			<tbody>
			<% 
				if (listaMedicos != null) { 
					for(Medico medico : listaMedicos){%>				
					<tr> 
				<% 
					String nombreClase ="bg-green" ; 
					String textButtonActivo ="Activo";
					if(!(medico.isActivo())){
						 nombreClase = "bg-red"; 
						 textButtonActivo ="Inactivo";
					}
				%>
						<form action="serverletsMedicos" method="post" class="<%=nombreClase%>">
							<td><strong><%=medico.getDni() %></strong></td> 
							<td><%=medico.getNombre() %></td> 
							<td><%=medico.getApellido() %></td>
							<td>Lunes</td> 
							<td>18 a 20 hrs</td> 
							<td>Neurología, Psicología</td> 
							<td class="d-flex">
								<a href="#" class="btn bg-blue">Ver</a>
								<a href="#" class="btn bg-green">Editar</a>
								<a href="#" class="btn bg-red">Eliminar</a>
							 </td>
						</form>
					</tr> 
					<% 
					}
				}%>				
			</tbody> 
		</table>
	</div>
	</div>
</div>

</body>
</html>