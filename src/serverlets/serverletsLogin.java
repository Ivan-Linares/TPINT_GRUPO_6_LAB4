package serverlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Login;
import dominio.LoginDao;

@WebServlet("/serverletsLogin")
public class serverletsLogin extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public serverletsLogin() {
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int filas = 0;
		
		Login user = new Login();
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		
		LoginDao login = new LoginDao();
		login.iniciarSesion(user);
		String requestDispacherRedirect = "";
		
		if(filas == 1) {
			requestDispacherRedirect = "/Inicio.jsp"; 
		}
		else {
			request.setAttribute("cantidadFilas", filas);	
			requestDispacherRedirect = "/Login.jsp";
		}
		
		RequestDispatcher rd2 = request.getRequestDispatcher(requestDispacherRedirect); 
		rd2.forward(request, response);
	}

}
