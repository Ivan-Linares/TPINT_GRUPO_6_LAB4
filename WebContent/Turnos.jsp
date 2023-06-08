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
<title>Insert title here</title>
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
				<li>
				
				<a href="Pacientes.jsp">
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
					M�dicos</a>
				 </li>
				
				
				<li>

					<a href="Turnos.jsp" class="active">				<span class="material-symbols-outlined">
					calendar_month
				</span>	Turnos</a>
				</li>
			</ul>
		</div>
	</div>



	<div style="margin: 20px auto;">
		<table class="content-table"> 
			<thead> 
				<tr> 
					<th>ID Turno</th>
					<th>Fecha</th>
					<th>Hora</th> 
					<th>Medico</th> 
					<th>Especialidad</th> 
					<th>Paciente</th> 
					<th>Estado</th> 
					<th>Observacion</th> 
				</tr>
			</thead> 
			<tbody>
				<tr> 
					<td>1</td> 
					<td>09/06/2023</td> 
					<td>14hs</td>
					<td>Juan Perez</td> 
					<td>Cirugia</td> 
					<td>Julian Cristobal</td> 
					<td>Pendiente</td> 
					<td>Ocupado</td> 
				</tr> 
				 <tr> 
				 	<td>2</td> 
					<td>016/08/2023</td> 
					<td>9hs</td>
					<td>Trinidad Morera</td> 
					<td>Oftalmolog�a</td> 
					<td>Cristian Nadal</td> 
					<td>Pendiente</td> 
					<td>Ocupado</td> 
				 </tr> 
				 <tr> 
				 	<td>3</td> 
					<td>06/06/2023</td> 
					<td>14hs</td>
					<td>Oscar Merchan</td> 
					<td>Neurolog�a</td> 
					<td>Nadia Alcantara</td> 
					<td>Realizado</td> 
					<td>Presente</td> 
				 </tr>
			</tbody> 
		</table>
	</div>
</div>

</body>
</html>