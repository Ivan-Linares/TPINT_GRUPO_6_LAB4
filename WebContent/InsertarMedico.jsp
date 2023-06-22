<%@page import="dominio.Especialidad"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="dominio.Pais"%>
<%@ page import="dominio.Cobertura"%>
<%@page import="java.util.ListIterator"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Agregar Médico</title>
<style>
<jsp:include page="css/StyleSheet.css"></jsp:include>
</style>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script src="js/code.jquery.com_jquery-3.7.0.min.js"></script>	
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
				
				 <a href="serverletsMedicos?method=get" class="active">
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
	</div>
	
	
	<div class="container fd-column m-auto" style="width:100%;
    margin: 0px 100px;">
	<div class="title-section d-flex jc-sb">
		<h1>Nuevo Médico</h1>

		<a href="#" class="btn bg-green">Agregar Médico</a>
		
	</div>
	
	<div>		
		<form>
			<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
			
				<div class="d-flex row">
					<div class="d-flex fd-column">
						<label>DNI</label>
						<input type="number" required="true" name="dni" class="campo">
						<span id="dniError" class="error"></span>
					</div>
			
					<div class="d-flex fd-column">
						<label>Fecha de Nacimiento</label>
						<input type="Date" required="true" name="fechaNacimiento" class="campo">
						<span id="fechaNacimientoError" class="error"></span>
					</div>
					
					<div class="d-flex fd-column">
						<label>Sexo</label>
						<select name="sexoSelect" class="select">
							<option>Femenino</option>
							<option>Masculino</option>
							<option>Otro</option>
						</select>
					</div>
					
				</div>
				
				<div class="d-flex row">
					<div class="d-flex fd-column">
						<label>Nombre</label>
						<input  type="text" required="true" name="nombre" class="campo">
						<span id="nombreError" class="error"></span>
					</div>
			
					<div class="d-flex fd-column">
						<label>Apellido</label>
						<input type="text" required="true" name="apellido" class="campo">
						<span id="apellidoError" class="error"></span>
					</div>
					
					<div class="d-flex fd-column">
						<label>Nacionalidad</label>
						<select name="nacionalidadSelect" class="select">
							<option value="-1">Escoge un Pais: </option>
								<% 
								ArrayList<Pais> listaPaises = new ArrayList<Pais>();
								if(request.getAttribute("listaPaises") != null){
									listaPaises = (ArrayList<Pais>)request.getAttribute("listaPaises");
								}
								
								ListIterator <Pais> it = listaPaises.listIterator();
												while(it.hasNext())
				{
					Pais pais = it.next();
				%>
				<option value="<%= pais.getIdPais()%>"><%= pais.getDescripcion() %></option>
				<%
				}%>
						</select>
					</div>
				</div>
				
				<div class="d-flex row">
					<div class="d-flex fd-column w-50">
						<label>Correo Electrónico</label>
						<input type="mail" required="true" name="correo" class="campo">
						<span id="mailError" class="error"></span>
					</div>
			
					<div class="d-flex fd-column w-50">
						<label>Telefono</label>
						<input type="number" required="true" name="telefono" class="campo">
						<span id="telefonoError" class="error"></span>
					</div>
						
				</div>
			
				<div class="d-flex row">
				
			</div>
			
			<div class="d-flex row">
				
								<div class="d-flex fd-column">
								<label>Dirección</label>
								<input type="Text" required="true"  name="direccion" class="campo">
				</div>
				
								<div class="d-flex fd-column">
								<label>Localidad</label>
<input type="Text" required="true" name="localidad"  class="campo">
				</div>
				
				<div class="d-flex fd-column">
								<label>Provincia</label>
<input type="Text" required="true" name="provincia"  class="campo">
				</div>
			
			</div>
			
			<div class="d-flex row">
			
			<div class="d-flex fd-column">
								<label>Pais</label>
								<select name="paisSelect" class="select">
								<option value="-1">Escoge un Pais: </option>
								<% 
								
								ListIterator <Pais> it2 = listaPaises.listIterator();
												while(it2.hasNext())
				{
					Pais pais = it2.next();
				%>
				<option value="<%= pais.getIdPais()%>"><%= pais.getDescripcion() %></option>
				<%
				}%>
								
								</select>
							</div>	
			</div>
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
		</form>
	</div>
	
	
	<div >	
		<div  class="filtro">
			<h3>Telefonos Médico</h3> 
			<!--  <button type="submit" name="btn-agregar-telefono" class="btn bg-green">Agregar Telefono</button>-->
			<a href="InsertarTelefono.jsp" name="btn-agregar-telefono" class="btn bg-green">Agregar Telefono</a>
		</div>	
		<table class="content-table header-table-blue" id="tablaMedicos"> 
			<thead> 
				<tr> 
					<th>DNI</th>
					<th>Número Telefono</th>
					<th>Acciones</th>
				</tr>
			</thead> 
			<tbody>		
			<tr>
						<form action="serverletsTelefonos" method="post" class="">
							<td><strong>44095935</strong></td> 
							<td>1567890945</td> 

							<td class="d-flex">
								<a href="#" class="btn bg-green">Editar</a>
								<a href="#" class="btn bg-red w-100">Eliminar  </a>
							 </td>
						</form>
						

					</tr> 
					
					<tr>	
											<form action="serverletsTelefonos" method="post" class="">
							<td><strong>44095935</strong></td> 
							<td>1547678231</td> 

							<td class="d-flex">
								<a href="#" class="btn bg-green">Editar</a>
								<a href="#" class="btn bg-red w-100">Eliminar  </a>
							 </td>
						</form>
					</tr> 	
			</tbody> 
		</table>
	</div>
	
	<br/>	
			<div >	
	<div  class="filtro">
	<h3>Horarios Médico</h3> 
	<!--  <button type="submit" name="btn-agregar-telefono" class="btn bg-green">Agregar Telefono</button>-->
	<a href="InsertarTelefono.jsp" name="btn-agregar-telefono" class="btn bg-green">Agregar Nuevo Horario</a>
	</div>	
		<table class="content-table header-table-blue" id="tablaMedicos"> 
			<thead> 
				<tr> 
					<th>DNI</th>
					<th>Día</th>
					<th>Hora Entrada</th>
					<th>Hora Salida</th>
					<th>Acciones</th>
				</tr>
			</thead> 
			<tbody>		
			<tr>
						<form action="serverletsHorariosTrabajo" method="post" class="">
							<td><strong>44095935</strong></td> 
							<td>Lunes</td> 
							<td>8hs</td> 
							<td>10hs</td> 
							<td class="d-flex">
								<a href="#" class="btn bg-green">Editar</a>
								<a href="#" class="btn bg-red w-100">Eliminar  </a>
							 </td>
						</form>
						

					</tr> 
					
					<tr>	
											<form action="serverletsHorariosTrabajo" method="post" class="">
							<td><strong>44095935</strong></td> 
							<td>Miercoles</td> 
							<td>10hs</td> 
							<td>14hs</td>  

							<td class="d-flex">
								<a href="#" class="btn bg-green">Editar</a>
								<a href="#" class="btn bg-red w-100">Eliminar  </a>
							 </td>
						</form>
					</tr> 	
			</tbody> 
		</table>
	</div>
	</div>
	

	</div>
	<script src="js/validaciones.js"></script>	
</body>
</html>