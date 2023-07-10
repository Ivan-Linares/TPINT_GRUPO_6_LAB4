<%@page import="dominio.Especialidad"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="dominio.Pais"%>
<%@ page import="dominio.Medico"%>
<%@ page import="dominio.HorariosTrabajo"%>
<%@ page import="dominio.Telefono"%>
<%@ page import="dominio.Cobertura"%>
<%@page import="java.util.ListIterator"%>
<%@ page import="java.util.ArrayList"%>
    <%@ page import="dominio.Usuario"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ver Médico</title>
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
		
				<div class="user-container">
			
			
			<%if(session.getAttribute("usuario") != null){
				Usuario user = (Usuario)session.getAttribute("usuario");
				%>	
				<strong><%= user.getCorreo() %></strong>
			<%} %>
			<a href="serverletsLogin?method=get" class="btn bg-green">Cerrar Sesión</a>
		</div>
	</div>
	<%		
		Medico medico = (Medico)request.getAttribute("medico"); 	
		%>
	
	<div class="container fd-column m-auto" style="width:100%;
    margin: 0px 100px; margin-bottom:60px" visible="<% if(medico == null) {%> false <%}%>">
	<div class="title-section d-flex jc-sb" >
		<h1> Médico</h1>
		
	</div>
	

	    <a href="serverletsMedicos?method=get" name="btn-volver" class="btn bg-blue">Volver al Listado</a>	
	    <br>
	    <% String mensajeModifico = "";
	    if(request.getAttribute("mensaje") != null) mensajeModifico = (String) request.getAttribute("mensaje");
 		 if (mensajeModifico != "") { %>
   		<h3 style="font-weight: bold; color: green; margin: 20px 0 20px 0;">
      		<%= mensajeModifico %>
  		</h3>
   		<%} %>
	    
	<div>		
	

		<form method="post" action="serverletsMedicos" class="position-relative">
		
		<%
		Integer idMedico = 0;
		if(request.getAttribute("idMedico")!= null) Integer.parseInt(request.getAttribute("idMedico").toString());
		%>
			<input type="hidden" name="IdMedico" value="<%=idMedico %>"> 
			<input type="hidden" name="dniMedico" value="<%=medico.getDni() %>"> 
			
			<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
			
				<div class="d-flex row">
					<div class="d-flex fd-column">
						<label>DNI</label>
						<input type="hidden" required="true" name="dni" class="campo" value="<%= medico.getDni()%>">
						<h3 class="campo"><%= medico.getDni()%></h3>					
					</div>
			
					<div class="d-flex fd-column">
						<label>Fecha de Nacimiento</label>
						<%if(request.getAttribute("editar-medico") != null){%>
						<input type="Date" required="true" name="fechaNacimiento" class="campo" value="<%= medico.getFechaNacimiento().toString() %>">
						<%}
						else{%>
							<p class="campo"><%= medico.getFechaNacimiento().toString() %></p>	
							<%}%>
						
						<span id="fechaNacimientoError" class="error"></span>
					</div>
					
					<div class="d-flex fd-column">
						<label>Sexo</label>
												<%if(request.getAttribute("editar-medico") != null){%>
											<select name="sexoSelect" class="select">
							<option value="F" <%if(medico.getSexo().contains("F")){%> selected="true"<%}%>>Femenino</option>
							<option  value="M" <%if(medico.getSexo().contains("M")){%> selected="true"<%}%>>Masculino</option>
							<option  value="O" <%if(medico.getSexo().contains("O")){%> selected="true"<%}%>>Otro</option>
						</select>
						<%}
						else{
						String sexo = medico.getSexo().toString();
						if(sexo.contains("F"))sexo = "Femenino";
						else if(sexo.contains("M"))sexo = "Masculino";
						else if(sexo.contains("O"))sexo = "Otro";
						else sexo = "";						
						%>
							<p class="campo"><%=sexo %></p>	
						<%}%>

					</div>
					
				</div>
				
				<div class="d-flex row">
					<div class="d-flex fd-column">
						<label>Nombre</label>
						<%if(request.getAttribute("editar-medico") != null){%><input  type="text" required="true" name="nombre" class="campo" value="<%= medico.getNombre()%>"><%}
						else{%><p class="campo"><%= medico.getNombre() %></p><%}%>
						<span id="nombreError" class="error"></span>
					</div>
			
					<div class="d-flex fd-column">
						<label>Apellido</label>
						<%if(request.getAttribute("editar-medico") != null){%><input type="text" required="true" name="apellido" class="campo" value="<%= medico.getApellido()%>"><%}
						else{%><p class="campo"><%= medico.getApellido()%></p><%}%>
						
						<span id="apellidoError" class="error"></span>
					</div>
					
					<div class="d-flex fd-column">
						<label>Nacionalidad</label>
						<%
						ArrayList<Pais> listaPaises = new ArrayList<Pais>();
						if(request.getAttribute("listaPaises") != null){
							listaPaises = (ArrayList<Pais>)request.getAttribute("listaPaises");
						}
						
						if(request.getAttribute("editar-medico") != null){%>						<select name="nacionalidadSelect" class="select">
								<% 								
								ListIterator <Pais> it = listaPaises.listIterator();
												while(it.hasNext())
				{
					Pais pais = it.next();
					String nacionalidadMedico= medico.getNacionalidad().getDescripcion();
					String nacionalidadSelect = pais.getDescripcion().toString();
				%>
				<option value="<%= pais.getIdPais()%>"  <%if(nacionalidadSelect.equals(nacionalidadMedico)){%> selected="true"<%}%> ><%= pais.getDescripcion() %></option>
				<%
				}%>
						</select><%}
						else{%><p class="campo"><%= medico.getNacionalidad().getDescripcion() %></p><%}%>

					</div>
				</div>
				
				<div class="d-flex row">
					<div class="d-flex fd-column w-50">
						<label>Correo Electrónico</label>
						<%if(request.getAttribute("editar-medico") != null){%><input type="mail" required="true" name="correo" class="campo" value="<%= medico.getCorreo()%>"><%}
						else{%><p class="campo"><%= medico.getCorreo()%></p><%}%>
						
						<span id="mailError" class="error"></span>
					</div>
			
				
								<div class="d-flex fd-column w-50">
								<label>Dirección</label>
								<%if(request.getAttribute("editar-medico") != null){%><input type="Text" required="true"  name="direccion" class="campo" value="<%= medico.getDomicilio().getDireccion()%>"><%}
						else{%><p class="campo"><%= medico.getDomicilio().getDireccion()%></p><%}%>
								
				</div>						
				</div>

			<div class="d-flex row">

				
								<div class="d-flex fd-column">
								<label>Localidad</label>
								<%if(request.getAttribute("editar-medico") != null){%><input type="Text" required="true" name="localidad"  class="campo" value="<%= medico.getDomicilio().getLocalidad()%>"><%}
						else{%><p class="campo"><%= medico.getDomicilio().getLocalidad()%></p><%}%>
				</div>
				
				<div class="d-flex fd-column">
								<label>Provincia</label>
								<%if(request.getAttribute("editar-medico") != null){%><input type="Text" required="true" name="provincia"  class="campo" value="<%= medico.getDomicilio().getProvincia()%>"><%}
						else{%><p class="campo"><%= medico.getDomicilio().getProvincia()%></p><%}%>

				</div>
			
			
			<div class="d-flex fd-column">
								<label>Pais</label>
								<%if(request.getAttribute("editar-medico") != null){%>
																<select name="paisSelect" class="select">
								<% 
								
								ListIterator <Pais> it2 = listaPaises.listIterator();
												while(it2.hasNext())
				{
					Pais pais = it2.next();
					String paisMedico= medico.getDomicilio().getPais().getDescripcion().toString();
					String paisSelect = pais.getDescripcion().toString();
				%>
				<option value="<%= pais.getIdPais()%>"  <%if(paisSelect.equals(paisMedico)){%> selected="true"<%}%>><%= pais.getDescripcion() %></option>
				<%
				}%>
								
								</select><%}
						else{%><p class="campo"><%= medico.getDomicilio().getPais().getDescripcion().toString()%></p><%}%>

							</div>	
							</div>
			<div class="d-flex row">
			
			
			</div>
			</div>
			
			<%if(request.getAttribute("editar-medico") != null){
					%>
					<button type="submit" name="btn-guardar-medico" class="btn bg-green position-absolute" style="right:0;">Guardar Cambios</button>
					<%} %>
			
		</form>
	</div>
	
	<div style="margin:60px 0px 0px 0px;border-top:1px solid blue; padding:10px 0px;">
	
	
	<div>	
			<%if(request.getAttribute("estadoEspecialidad") != null){
		String mensaje = request.getAttribute("estadoEspecialidad").toString(); %>
			<h3 style="font-weight: bold; color: green; margin: 20px 0 20px 0;"><%= mensaje %></h3>
		<%}%>
	<div  class="filtro">
	<h3>Especialidades</h3> 
	
	<form action="serverletsEspecialidades"  method="get">
	 <input type="hidden" name="idMedico" value="<%=medico.getIdMedico() %>">
	  <input type="hidden" name="dniMedico" value="<%=medico.getDni() %>">
	  
	  	<%if(request.getAttribute("editar-medico") != null){%>
			<button type="submit" name="btn-nueva-especialidad" class="btn bg-green w-100">Agregar Especialidad</button>
		<%} %>
		
	</form>
	</div>
		<%
	ArrayList<Especialidad> especialidadesMedico = new ArrayList<Especialidad>();

	if(request.getAttribute("listaEspecialidadesMedico") != null){
		Object obj = request.getAttribute("listaEspecialidadesMedico");
		if(obj instanceof ArrayList<?>){
			especialidadesMedico = (ArrayList<Especialidad>) obj;
		}
	}
	%>
	<table class="content-table header-table-blue w-100" id="tablaMedicos"> 
			<thead> 
				<tr> 
					<th>Especialidad</th>
					<th>Activo</th>
					<%if(request.getAttribute("editar-medico") != null){%>
					<th>Acciones</th>
					<%} %>
				</tr>
			</thead> 
			<tbody>	
	<% if(especialidadesMedico.size() > 0){
		for(Especialidad especialidad: especialidadesMedico){
			String nombreClase = "bg-green" ;
			String textButtonActivo ="Activo";
	
		if(!especialidad.isActivo()) {
			nombreClase = "bg-red"; 
			textButtonActivo = "Inactivo";}%>	
			<tr>
			<form action="serverletsEspecialidades" method="post" >
				<input type="hidden" name="dniMedico" value="<%=medico.getDni() %>">
			 	<input type="hidden" name="idMedico" value="<%=medico.getIdMedico() %>">
			 	<input type="hidden" name="idEspecialidad" value="<%=especialidad.getIdEspecialidad()%>">
				<td><%=especialidad.getDescripcion() %></td>
				<td><button class="btn w-100 <%=nombreClase%>"><%=textButtonActivo %></button></td> 	
				<%if(request.getAttribute("editar-medico") != null){%>
				<td class="d-flex">
					<button  type="submit"  name="btn-eliminar-especialidad" class="btn bg-red w-100">Eliminar  </button>
		 		</td>
				<%} %>
			</form>
			</tr> 
			<% }} %>
			</tbody> 
		</table>
	</div>
	
	
	</div>	
	
	
		<%if(request.getAttribute("estadoHorario") != null){
		String mensaje = request.getAttribute("estadoHorario").toString(); %>
			<h3 style="font-weight: bold; color: green; margin: 20px 0 20px 0;"><%= mensaje %></h3>
		<%}%>
	
		<%if(request.getAttribute("estadoTelefono") != null){
		String mensaje = request.getAttribute("estadoTelefono").toString(); %>
		<h3 style="font-weight: bold; color: green; margin: 20px 0 20px 0;"><%= mensaje %></h3>
		<%}%>
	
<div style=" display:flex; border-top:1px solid blue; padding:10px 0px;">

	
	<br/>	
			<div style="margin-right:5px">	
	<div  class="filtro">
	<h3>Horarios</h3> 
	
	<form action="serverletsHorariosMedico"  method="get">
	 <input type="hidden" name="idMedico" value="<%=medico.getIdMedico() %>">
	 <input type="hidden" name="dniMedico" value="<%=medico.getDni() %>">
	  	<%if(request.getAttribute("editar-medico") != null){%>
			<button type="submit" name="btn-nuevo-horario" class="btn bg-green w-100">Agregar Horario</button>
		<%} %>
	</form>
	
	</div>	
	<%
	ArrayList<HorariosTrabajo> horariosMedico = new ArrayList<HorariosTrabajo>();

	if(request.getAttribute("listaHorariosMedico") != null){
		Object obj = request.getAttribute("listaHorariosMedico");
		if(obj instanceof ArrayList<?>){
			horariosMedico = (ArrayList<HorariosTrabajo>) obj;
		}
	}
	%>
	

		<table class="content-table header-table-blue w-100" id="tablaMedicos"> 
			<thead> 
				<tr> 
					<th>Día</th>
					<th>Hora Entrada</th>
					<th>Hora Salida</th>
					<th>Activo</th>
					<%if(request.getAttribute("editar-medico") != null){%>
					<th>Acciones</th>
					<%} %>
				</tr>
			</thead> 
			<tbody>		
	<% if(horariosMedico.size() > 0){
	for(HorariosTrabajo horario: horariosMedico){
	String nombreClase = "bg-green" ;
	String textButtonActivo ="Activo";
	
	if(!horario.isActivo()) {
		nombreClase = "bg-red"; 
		textButtonActivo = "Inactivo";
	}
	%>
		
		<tr>
		<form action="serverletsHorariosMedico" method="post" >
			<input type="hidden" name="dniMedico" value="<%=medico.getDni() %>">
			<input type="hidden" name="idMedico" value="<%=medico.getIdMedico() %>">
			<input type="hidden" name="diaHorarioMedico" value="<%=horario.getDia() %>">
			<td><%=horario.getDia() %></td> 
			<td><%=horario.getHoraEntrada().toString() %>hs</td> 
			<td><%=horario.getHoraSalida().toString()  %>hs</td> 
			<td><button class="btn w-100 <%= nombreClase%>"><%= textButtonActivo%></button></td> 	
			<%if(request.getAttribute("editar-medico") != null){%>
			<td class="d-flex">
				<button type="submit" name="btn-editar-horario-trabajo" class="btn bg-blue">Editar</button>
				<button  type="submit"  name="btn-eliminar-horario-trabajo" class="btn bg-red w-100"  onclick="return confirm('Esta seguro que desea eliminar el Horario seleccionado?');">Eliminar  </button>
		 	</td>
			<%} %>
	</form>


	</tr> 
	<% }} %>
			</tbody> 
		</table>
	</div>
	
	
	<div  style="border-radius:10px;height:100%;">	
	<div  class="filtro">
		<h3>Telefonos</h3> 
		<form action="serverletsTelefono"  method="get">
	 		<input type="hidden" name="idMedico" value="<%=medico.getIdMedico() %>">
	  		<input type="hidden" name="dniMedico" value="<%=medico.getDni() %>">
	  	 	<%if(request.getAttribute("editar-medico") != null){%>
				<button type="submit" name="btn-nuevo-telefono" class="btn bg-green w-100">Agregar Telefono</button>
			<%} %>
	
		</form>
	</div>	
		
		<%
	ArrayList<Telefono> telefonosMedico = new ArrayList<Telefono>();

	if(request.getAttribute("listaTelefonosMedico") != null){
		Object obj = request.getAttribute("listaTelefonosMedico");
		if(obj instanceof ArrayList<?>){
			telefonosMedico = (ArrayList<Telefono>) obj;
		}
	}
	%>
	

		<table class="content-table header-table-blue" id="tablaMedicos"> 
			<thead> 
				<tr> 
					<th>Telefono</th>
					<th>Activo</th>
					<%if(request.getAttribute("editar-medico") != null){%><th>Acciones</th><%} %>			
				</tr>
			</thead> 
			<tbody>		
			<% if(telefonosMedico.size() > 0){
			for(Telefono telefono: telefonosMedico){
			String nombreClase = "bg-green" ;
	String textButtonActivo ="Activo";
	
	if(!telefono.isActivo()) {
		nombreClase = "bg-red"; 
		textButtonActivo = "Inactivo";
	}
	%>
		
			<tr>
						<form action="serverletsTelefono" method="post" class="">
						<input type="hidden" name="dniMedico" value="<%=medico.getDni() %>">
						<input type="hidden" name="telefonoMedico" value="<%=telefono.getTelefono() %>">
							<td><%=telefono.getTelefono() %></td> 
							<td>
							<button class="btn w-100 <%= nombreClase%>">
							 <%= textButtonActivo%>
							</button>
							</td> 	
							<%if(request.getAttribute("editar-medico") != null){%>
							<td class="d-flex">
								<button type="submit" name="btn-editar-telefono" class="btn bg-blue">Editar</button>
								<button  type="submit"  name="btn-eliminar-telefono" class="btn bg-red w-100"  onclick="return confirm('Esta seguro que desea eliminar el Telefono seleccionado?');">Eliminar  </button>
							</td>
							 <%} %>	
						</form>
					</tr> 
			<% }} %>
			</tbody> 
		</table>
	</div>
	</div>
	</div>
	

	</div>
	<script src="js/validaciones.js"></script>	
</body>
</html>