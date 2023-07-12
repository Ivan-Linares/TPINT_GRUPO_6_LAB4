<%@page import="dominio.Especialidad"%>
<%@page import="dominio.HorariosTrabajo"%>
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
	var table = $('#tablaMedicos').DataTable({	    
	    initComplete: function() {
	      
	      $('.dataTables_filter input').css('display', 'none');
	      $('.dataTables_length').css('margin-bottom', '10px');
	      //https://datatables.net/examples/basic_init/filter_only.html
	      $('.dataTables_filter label').contents().filter(function() {
	        return this.nodeType === 3;
	      }).remove();
	    }
	  });
	    
	  
	  /*$('.dataTables_length').hide();
	  $('.dataTables_info').hide();
	  */
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
			<%
			Usuario user = null;
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
				<%}
				else{%>	
									<li>
					<a href="servletsTurnos?method=get">				
						<span class="material-symbols-outlined">calendar_month</span>	
						Turnos
					</a>
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
	
	
	<div class="container fd-column m-auto" style="padding: 0 35px">
	<div class="title-section d-flex jc-sb">
		<h1>Médicos</h1>

		<form method="get" action="serverletsMedicos"  class="filtro">	
						<div class="filtro">
				<h3>Filtrar por:</h3>
				<select name="filtro" id="filtro">
					<option value="dni">DNI</option>
					<option value="nombre">Nombre</option>
					<option value="apellido">Apellido</option>
					<option value="dia">Dia de Atención</option>	
					<option value="especialidad">Especialidad</option>				
				</select>			
				<input type="text" name="filtro-valor">
				<input type="submit" name="btn-buscar" class="btn bg-blue" value="Buscar"/> 
				</div>	
		<button type="submit" name="btn-nuevo-medico" class="btn bg-green">Agregar Médico</button>
		</form>
		
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
		
		ArrayList<HorariosTrabajo> listaHT = new ArrayList<HorariosTrabajo>();
		
		if(request.getAttribute("listaHT") != null){
			Object obj = request.getAttribute("listaHT");
			if(obj instanceof ArrayList<?>){
				listaHT = (ArrayList<HorariosTrabajo>) obj;
			}
			else{
				listaHT = null;
			}
		}
		
		ArrayList<Medico> listaEspecialidadesMedico = new ArrayList<Medico>();
		
		if(request.getAttribute("listaEspMedico") != null){
			Object obj = request.getAttribute("listaEspMedico");
			if(obj instanceof ArrayList<?>){
				listaEspecialidadesMedico = (ArrayList<Medico>) obj;
			}
			else{
				listaEspecialidadesMedico = null;
			}
		}
	%>
	
		<% String mensaje = "";
		if(request.getAttribute("mensaje") != null) mensaje = request.getAttribute("mensaje").toString();
   		if (mensaje != "") { %>
   		<h3 style="font-weight: bold; color: green; margin: 20px 0 20px 0;"><%= mensaje %></h3>
<% } %>
	
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
					<th>Activo</th>
					<th>Acciones</th> 
				</tr>
			</thead> 
			<tbody>
			<% 
				if (listaMedicos != null) { 
					for(Medico medico : listaMedicos){%>				
					<tr> 
				<% 
					ArrayList<HorariosTrabajo> horariosMedico = new ArrayList<HorariosTrabajo>();
						for(HorariosTrabajo ht : listaHT){
							if(ht.getIdMedico() == medico.getIdMedico()){
								horariosMedico.add(ht);
							}}
						
							String nombreClase ="bg-green" ; 
							String textButtonActivo ="Activo";
							if(!(medico.isActivo())){
								 nombreClase = "bg-red"; 
								 textButtonActivo ="Inactivo";
							}
							
				%>
						<form action="serverletsMedicos" method="post" class="<%=nombreClase%>">
							<td><strong><%=medico.getDni() %></strong> <input type="hidden" name="dniMedico" value="<%=medico.getDni() %>"> 
							 <input type="hidden" name="idMedico" value="<%=medico.getIdMedico() %>"> 
							  </td> 
							<td><%=medico.getNombre() %></td> 
							<td><%=medico.getApellido() %></td>
							
							<%if(horariosMedico.size() > 0){ %>
								<td style="display:flex; flex-direction:column;">
										<%for(HorariosTrabajo horarioMedico : horariosMedico){ %>									
											<div style="border-bottom:1px solid blue; padding:4px 0px 4px 0px">
												<%=horarioMedico.getDia() %>
											</div>
										<%}%>
								</td>
								 
								<td>
					           	 <%for(HorariosTrabajo horarioMedico : horariosMedico){ %>									
									<div style="border-bottom:1px solid blue;padding:4px 0px 4px 0px">
										<%=horarioMedico.getHoraEntrada().toString() %> 
											hs a
										<%=horarioMedico.getHoraSalida().toString() %> hs
									</div>
									<%}%>
								</td> 
							<%}
							else{%>								
								<td>
							Sin días asginados
							</td> 
							<td>
							Sin horarios Asignados</td> 
							<%}%>
							
							<%
							ArrayList<Especialidad> listaEsp = new ArrayList<Especialidad>();
							for(Medico med : listaEspecialidadesMedico){ 
								if(med.getIdMedico() == medico.getIdMedico()){
									listaEsp = med.getEspecialidades();
								}
							}%>
							<td> 
							<%if(listaEsp.size() > 0){
								for(Especialidad esp : listaEsp){%>
									<%=esp.getDescripcion() %>
								<%}
							}
							else{%>
								Sin Especialidades
								<%}	
							%>
							</td> 
									
							<td>
							<button class="btn w-100 <%= nombreClase%>">
							 <%= textButtonActivo%>
							</button>
							</td> 	
							<td>
							
							<div class="d-flex">
							<button type="submit" name="btn-ver-medico" class="btn bg-blue">Ver</a>
								<button type="submit" name="btn-editar-medico" class="btn bg-green">Editar</a>
								<%if(medico.isActivo()){%><button type="submit" name="btn-eliminar-medico" class="btn bg-red" onclick="return confirm('Esta seguro que desea eliminar al Medico?');">Eliminar</a><%}
								else{%><button type="submit" name="btn-reactivar-medico" class="btn bg-green" onclick="return confirm('Esta seguro que desea reactivar al Medico?');">Reactivar</a><%}%>							
							</div>
								
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