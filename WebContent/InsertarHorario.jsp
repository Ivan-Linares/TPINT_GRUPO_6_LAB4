<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insertar Horario</title>
<style>
<jsp:include page="css/StyleSheet.css"></jsp:include>
</style>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

</head>
<body>
<div class="container fd-column m-auto" style="max-width:100%; padding:100px">	
		<div class="title-section d-flex jc-sb">
		<h1>Nuevo Horario</h1>

	<% 
	String idMedico = ""; 
	String dniMedico = ""; 
	if(request.getAttribute("idMedico") != null){
			 idMedico = request.getAttribute("idMedico").toString();
	}		 
			 if(request.getAttribute("dniMedico") != null){			 
				 dniMedico = request.getAttribute("dniMedico").toString();
			 %>
			 <form action="serverletsMedicos" method="post">
			<input type="hidden" name="dniMedico" value="<%=dniMedico %>">  
		<button class="btn bg-blue w-100" type="submit" name="btn-ver-medico"> Volver Atrás</button>
		</form>
			 <% }%>
		
	</div>
	
		<form method="post" action="serverletsHorariosMedico">
		
		<%if(idMedico != ""){
			%>
			 <input type="hidden" name="idMedico" value="<%=idMedico %>">
		<%} %>
		<%if(dniMedico != ""){
			%>
			 <input type="hidden" name="dniMedico" value="<%=dniMedico %>">
		<%} %>
			<div class="d-flex fd-column style-form" style="padding: 50px 0px;">
			
				<div class="d-flex row">
					<div class="d-flex fd-column">
						<label>Día</label>
						<select name="dia" class="select">
									<option value="Lunes">Lunes</option>
									<option value="Martes">Martes</option>		
									<option value="Miércoles">Miércoles</option>				
									<option value="Jueves">Jueves</option>		
									<option value="Viernes">Viernes</option>	
									<option value="Sábados">Sábado</option>		
									<option value="Domingo">Domingo</option>				
								</select>
					</div>	
					<div class="d-flex fd-column">
						<label>Hora Entrada</label>
						<input type="time"  id="horaDesde"  required="true" name="horaEntrada" class="campo">
					</div>	
					<div class="d-flex fd-column">
						<label>Hora Salida</label>
						<input type="time"  id="horaHasta" required="true" name="horaSalida" class="campo">
					</div>	
				</div>
			</div>
			
			<button type="submit" name="btn-agregar-medico" class="btn bg-green">Agregar Horario</button>
		</form>
		
	<%if (request.getAttribute("estadoNuevoHorario") != null) {
	String mensaje = request.getAttribute("estadoNuevoHorario").toString();%>
	
	<span> <%= mensaje %></span>
	<%}%>
	</div>
	
</body>
</html>