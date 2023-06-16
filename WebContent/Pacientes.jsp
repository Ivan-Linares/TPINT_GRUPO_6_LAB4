<%@ page import="java.util.ArrayList"%>
<%@ page import="dominio.Paciente"%>
<%@ page import="dominio.Usuario"%>
<%@ page import="java.util.*" %>
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
<title>Pacientes</title>
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
				
				<a href="serverletsPacientes?method=get" class="active">
				<span class="material-symbols-outlined">
					groups
				</span>
				Pacientes</a>
				</li>
				
				<li> 
				
				 <a href="Medicos.jsp">
				 	<span class="material-symbols-outlined">
						clinical_notes
					</span>	
					Médicos</a>
				 </li>
				
				
				<li>

					<a href="Turnos.jsp">				<span class="material-symbols-outlined">
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
		<h1>Pacientes</h1>		
		
<form method="get" action="serverletsPacientes"  class="filtro">
		<h3>Filtrar por:</h3>
		<select name="filtroPacientes" id="filtroPacientes">
			<option>DNI</option>
			<option>Nombre</option>
			<option>Apellido</option>
			<option>Correo electrónico</option>
			<option>Teléfono</option>			
		</select>
		
		<input type="text">
		 <input type="submit" name="btnBuscarPacientes" class="btn bg-blue" value="Buscar"/> 
		 
		 <a href="InsertarPaciente.jsp" class="btn bg-green">Agregar Paciente</a>
		 </form>
		
		
	</div>
	
	<%
	ArrayList<Paciente> listaPacientes = new ArrayList<Paciente>();

	if(request.getAttribute("listaPacientes") != null){
		Object obj = request.getAttribute("listaPacientes");
		if(obj instanceof ArrayList<?>){
			listaPacientes = (ArrayList<Paciente>) obj;
		}
		else{
			listaPacientes = null;
		}		
	}
%>

	<div>
		<table class="content-table header-table-blue"> 
			<thead> 
				<tr>
					<th>DNI</th>
					<th>Nombre</th>
					<th>Apellido</th> 
					<th>Sexo</th> 
					<th>Nacionalidad</th> 
					<th>Fecha Nacimiento</th> 					
					<th>Correo electrónico</th>
					<th>Activo</th>
					<th>Acciones</th>
				</tr>
			</thead> 
			<tbody>
			<% if (listaPacientes != null) { %>
				<%for(Paciente paciente : listaPacientes){%>
					<tr> 
					<% String nombreClase ="bg-green" ; 
					String textButtonActivo ="Activo";
					if(!(paciente.isActivo())){
						 nombreClase = "bg-red"; 
						 textButtonActivo ="Inactivo";
					}
					%>
						<form action="serverletsPacientes" method="post" class="<%=nombreClase%>">

							<td><strong><%=paciente.getDni() %></strong> <input type="hidden" name="dniPaciente" value="<%=paciente.getDni() %>"> </td> 
							<td><%= paciente.getNombre() %></td> 
							<td><%= paciente.getApellido()%></td>
							<td><%= paciente.getSexo()%></td> 
							<td><%= paciente.getNacionalidad() %></td> 
							<td><%= paciente.getFechaNacimiento() %></td> 
							
							<td><%=paciente.getCorreo() %></td>	
							<td><button class="btn w-100 <%= nombreClase%>">
							 <%= textButtonActivo%>
							</button></td> 			
							<td class="d-flex">
								<button type="submit" name="btn-ver-paciente" class="btn bg-blue">Ver</button>
								<button type="submit" name="btn-editar-paciente" class="btn bg-green">Editar</button>
								<button type="submit" name="btn-eliminar-paciente" class="btn bg-red" onclick="return confirm('Esta seguro que desea eliminar al Paciente ?');">Eliminar</button>
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