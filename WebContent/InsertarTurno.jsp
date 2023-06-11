<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Agregar Turno</title>
	<style>
	<jsp:include page="css/StyleSheet.css"></jsp:include>
	</style>
	<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<body>
	<div class="container">
		<div class="navbar">
			<div class="nav-title">
				<span class="material-symbols-outlined">ecg_heart</span>
				<h3>Sistema Clínica</h3>
			</div>
	
			<div class="items">
				<ul>
					<li>					
						<a href="Pacientes.jsp">
							<span class="material-symbols-outlined">groups</span>Pacientes
						</a>
					</li>
					
					<li>					
					 	<a href="Medicos.jsp" class="active">
					 		<span class="material-symbols-outlined">clinical_notes</span>Médicos
					 	</a>
					 </li>
					
					
					<li>
						<a href="Turnos.jsp">
						<span class="material-symbols-outlined">calendar_month</span>Turnos</a>
					</li>
				</ul>
			</div>
		</div>		
		
		<div class="container fd-column m-auto" style="width:100%;
	    margin: 0px 100px;">		
			<div>		
				<form>
					<div class="d-flex fd-column style-form" style="margin: 50px 0px;">
					
						<div class="d-flex row">						
							<div class="d-flex fd-column">
								<label>Paciente</label>
								<select name="paciente" class="select">
									<option>Lucas Beltran</option>
									<option>Rodrigo Aliendro</option>									
								</select>
							</div>
							
							<div class="d-flex fd-column">
								<label>Especialidad</label>
								<select name="especialidad" class="select">
									<option>Cirugia</option>
									<option>Oftalmología</option>		
									<option>Neurología</option>							
								</select>
							</div>	
							
							<div class="d-flex fd-column">
								<label>Medico</label>
								<select name="medico" class="select">
									<option>Mario Gutierrez</option>
									<option>Carla Montenegro</option>		
									<option>Mariana Cantero</option>							
								</select>
							</div>					
						</div>					
						
						<div class="d-flex row">
							<div class="d-flex fd-column">
								<label>Fecha</label>
								<input type="date" class="campo" >
							</div>				
							
							<div class="d-flex fd-column">
								<label>Hora</label>
								<input type="number" class="campo" >
							</div>
							
							<div class="d-flex fd-column">
								<label>Observaciones</label>
								<input type="text" class="campo" >
							</div>				
						</div>
					
					</div>					
				</form>
			</div>
		</div>
	</div>
	</body>
</html>