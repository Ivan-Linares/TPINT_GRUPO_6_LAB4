<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
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
				
				<a href="Pacientes.jsp">
				<span class="material-symbols-outlined">
					groups
				</span>
				Pacientes</a>
				</li>
				
				<li> 
				
				 <a href="Medicos.jsp" class="active">
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
	
	
	<div class="container fd-column m-auto">
	<div class="title-section">
		<h1>Médicos</h1>
	</div>
	
	<div>
		<table class="content-table header-table-blue"> 
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
				<tr> 
					<td><strong>27567897</strong></td> 
					<td>Mario</td> 
					<td>Gutierrez</td>
					<td>Lunes</td> 
					<td>18 a 20 hrs</td> 
					<td>Neurología, Psicología</td> 
					<td class="d-flex">
						<a href="#" class="btn bg-blue">Ver</a>
						<a href="#" class="btn bg-green">Editar</a>
						<a href="#" class="btn bg-red">Eliminar</a>
					 </td>
				</tr> 
				 <tr> 
				 	<td><strong>23123657</strong></td> 
					<td>Carla</td> 
					<td>Montenegro</td>
					<td>Miercoles</td> 
					<td>11 a 17 hrs</td> 
					<td>Oftalmología</td> 
					<td class="d-flex">
						<a href="#" class="btn bg-blue">Ver</a>
						<a href="#" class="btn bg-green">Editar</a>
						<a href="#" class="btn bg-red">Eliminar</a>
					 </td>
				 </tr> 
				 <tr> 
				 	<td><strong>33456912</strong></td> 
					<td>Mariana</td> 
					<td>Cantero</td>
					<td>Martes</td> 
					<td>8 a 12 hrs</td> 
					<td>Neurología</td> 
					<td class="d-flex">
						<a href="#" class="btn bg-blue">Ver</a>
						<a href="#" class="btn bg-green">Editar</a>
						<a href="#" class="btn bg-red">Eliminar</a>
					 </td>
				 </tr>
			</tbody> 
		</table>
	</div>
	</div>
</div>

</body>
</html>