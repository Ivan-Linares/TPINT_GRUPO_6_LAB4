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
				
				<a href="Pacientes.jsp" class="active">
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
	</div>
	
	<div class="container fd-column m-auto">
	<div class="title-section d-flex jc-sb">
		<h1>Pacientes</h1>		
		<div class="filtro">
		<h3>Filtrar por:</h3>
		<select name="filtroPacientes" id="filtroPacientes">
			<option>DNI</option>
			<option>Nombre</option>
			<option>Apellido</option>
			<option>Correo electrónico</option>
			<option>Teléfono</option>			
		</select>
		
		<input type="text">
		 <input type="submit" name="btnBuscar" class="btn bg-blue" value="Buscar"/> 
		<a href="InsertarPaciente.jsp" class="btn bg-green">Agregar Paciente</a>
		</div>
	</div>
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
					<th>Domicilio</th>
					<th>Correo electrónico</th>
					<th>Teléfono</th>
					<th>Acciones</th>
				</tr>
			</thead> 
			<tbody>
				<tr> 
					<td><strong>27567897</strong></td> 
					<td>Rodrigo</td> 
					<td>Aliendro</td>
					<td>M</td> 
					<td>Argentina</td> 
					<td>1/1/1990</td> 
					<td>Figueroa Alcorta 7597, CABA, Buenos Aires</td> 
					<td>rodri@aliendro.com</td>
					<td>123123123</td>					
					<td class="d-flex">
						<a href="#" class="btn bg-blue">Ver</a>
						<a href="#" class="btn bg-green">Editar</a>
						<a href="#" class="btn bg-red">Eliminar</a>
					 </td>
				</tr> 
				 <tr> 
				 	<td><strong>27567912</strong></td> 
					<td>Lucas</td> 
					<td>Beltran</td>
					<td>M</td> 
					<td>Argentina</td> 
					<td>1/1/1990</td> 
					<td>Figueroa Alcorta 7597, CABA, Buenos Aires</td> 
					<td>lucas@beltran.com</td>
					<td>123123123</td>					
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