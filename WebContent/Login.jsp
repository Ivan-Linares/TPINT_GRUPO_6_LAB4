<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Ingresar</title>
<style>
<jsp:include page="css/StyleSheetLogin.css"></jsp:include>
</style>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
</head>
<body>

	<div class="container">

			
		<form action="serverletsLogin" method="get" class="container-login">
		
				<div class="title">
						<span class="material-symbols-outlined">
			ecg_heart
				</span>
		<h2>Iniciar sesión</h2>
		</div>
		
					<input type="text" placeholder="Email" name="email" required="true">
					<input type="password" placeholder="Contraseña" name="password" required="true">
			
					<button class="btn-submit" type="submit">Iniciar Sesión</button>
			
		</form>
		
	</div>

</body>
</html>