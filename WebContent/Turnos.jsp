<%@ page import="java.util.ArrayList"%>
<%@ page import="dominio.Usuario"%>
<%@ page import="dominio.Medico"%>
<%@ page import="dominio.Turnos"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"
	href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.css">
	
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
<script type="text/javascript" charset="utf8"
	src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	var table = $('#tablaTurnos').DataTable({	    
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
<title>Turnos</title>
</head>
<body>

<div class="container">

	<div class="navbar">
		<div class="nav-title">
		<span class="material-symbols-outlined">
			ecg_heart
		</span>
			<h3>Sistema Cl�nica</h3>
		</div>

		<div class="items">
			<ul>
			<%
			Medico medico = null;
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
				
				 	<a href="serverletsMedicos?method=get">
				 		<span class="material-symbols-outlined">clinical_notes</span>	
						M�dicos
					</a>
				 </li>
								
				<li>
					<a href="servletsTurnos?method=get" class="active">				
						<span class="material-symbols-outlined">calendar_month</span>	
						Turnos
					</a>
				</li>
								<li>
					<a href="Reportes.jsp">				
						<span class="material-symbols-outlined">density_small</span>
						Reportes
					</a>
				</li>
				<%}
				else{
				
			if(session.getAttribute("medicoUsuario") != null) medico =(Medico)session.getAttribute("medicoUsuario");
			String dniMedico = "";
			dniMedico = medico.getDni();
			int idMedico = 0;
			idMedico = medico.getIdMedico();
			String url = "serverletsMedicos?method=post&dniMedico="+dniMedico+"&idMedico="+idMedico+ "&btn-ver-medico=";
			%>	
				
									<li>
					<a href="servletsTurnos?method=get" class="active">				
						<span class="material-symbols-outlined">calendar_month</span>	
						Turnos
					</a>
				</li>
				<li>
					<form method="post" action="serverletsMedicos">	
								<input type="hidden" name="dniMedico" value="<%=dniMedico%>">
								<input type="hidden" name="idMedico" value="<%=idMedico%>">
											<button class="btn-a" name="btn-ver-medico">				
						<span class="material-symbols-outlined">account_circle</span>	
						Mi Perfil
					</button>
					</form>
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
			<a href="serverletsLogin?method=get&btn-cerrar-sesion" class="btn bg-green">Cerrar Sesi�n</a>
		</div>
	</div>


<div class="container fd-column m-auto" style="padding: 0 35px">
	<div class="title-section d-flex jc-sb">
			<h1>Turnos</h1>
			<form action="servletsTurnos" method="get">
				<div class="filtro" >
				<h3 style="display:none">Filtrar por:</h3>
				<select name="filtroTurnos" id="filtroTurnos" style="display:none">
					<option>Especialidad</option>
					<option>Medico</option>
					<option>Paciente</option>				
				</select>			
				<input type="text" style="display:none">
				<input type="submit" name="btnBuscar" class="btn bg-blue" value="Buscar" style="display:none"/> 
				<%if(medico == null){%>
				
					<button type="submit" name="btn-nuevo-turno" class="btn bg-green w-100">Agregar Turno</button>	
				<%}%>
				
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
		<table class="content-table header-table-blue" id="tablaTurnos"> 
			<thead> 
				<tr> 
					<th>ID Turno</th>
					<th>Fecha</th>
					<th>Hora</th> 
					<th>Medico</th> 
					<th>Especialidad</th> 
					<th>Paciente</th> 
					<th>Estado</th> 
					<th>Activo</th> 
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