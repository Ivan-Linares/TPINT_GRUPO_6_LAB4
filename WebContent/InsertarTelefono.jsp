<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insertar Telefono</title>
<style>
<jsp:include page="css/StyleSheet.css"></jsp:include>
</style>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />

</head>
<body>
<div class="container fd-column m-auto" style="width:100%;
    margin: 0px 100px;">	
		<div class="title-section d-flex jc-sb">
		<h1>Nuevo Telefono</h1>

		<% 
	String idMedico = ""; 
	String dniMedico = ""; 
	if(request.getAttribute("idMedico") != null){
			 idMedico = request.getAttribute("idMedico").toString();
	}		 
			 if(request.getAttribute("dniMedico") != null){			 
				 dniMedico = request.getAttribute("dniMedico").toString();
			 %>
			 <form action="serverletsTelefono" method="post">
			<input type="hidden" name="dniMedico" value="<%=dniMedico %>">  
		<button class="btn bg-blue w-100" type="submit" name="btn-ver-medico"> Volver Atrás</button>
		</form>
			 <% }%>
		
	</div>
	

		<form action="serverletsTelefono" method="post">
				<%if(idMedico != ""){
			%>
			 <input type="hidden" name="idMedico" value="<%=idMedico %>">
		<%} %>
		<%if(dniMedico != ""){
			%>
			 <input type="hidden" name="dniMedico" value="<%=dniMedico %>">
		<%} %>
			<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
			
				<div class="d-flex row">
					<div class="d-flex fd-column">
						<label>Telefono</label>
						<input type="number" required="true" name="telefono" class="campo">
					</div>	
				</div>
			</div>
			
			<button type="submit" name="btn-agregar-telefono" class="btn bg-green">Agregar Telefono</button>
		</form>
	</div>
	
	<%if (request.getAttribute("estadoNuevoTelefono") != null) {
	String mensaje = request.getAttribute("estadoNuevoTelefono").toString();%>
	<br/>
	<span> <%= mensaje %></span>
	<%}%>
</body>
</html>