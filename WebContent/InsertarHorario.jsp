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
<div class="container fd-column m-auto" style="width:100%;
    margin: 0px 100px;">	
		<div class="title-section d-flex jc-sb">
		<h1>Nuevo Horario</h1>

		<a href="InsertarMedico.jsp" class="btn bg-green">Volver Atrás</a>
		
	</div>
	
		<form>
			<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
			
				<div class="d-flex row">
					<div class="d-flex fd-column">
						<label>Día</label>
						<input type="text" required="true" name="dia" class="campo">
					</div>	
					<div class="d-flex fd-column">
						<label>Hora Desde</label>
						<input type="text" required="true" name="dia" class="campo">
					</div>	
					<div class="d-flex fd-column">
						<label>Hora Hasta</label>
						<input type="text" required="true" name="dia" class="campo">
					</div>	
				</div>
			</div>
		</form>
	</div>
</body>
</html>