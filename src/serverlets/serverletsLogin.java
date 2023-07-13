package serverlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.LoginDAO;
import dao.impl.MedicoDAOImpl;
import dominio.Login;
import dominio.LoginDao;
import dominio.Medico;
import dominio.Usuario;

@WebServlet("/serverletsLogin")
public class serverletsLogin extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public serverletsLogin() {
		
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getParameter("btn-cerrar-sesion") != null) {
			cerrarSesion(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		if(request.getSession().getAttribute("usuario") != null) {
			cerrarSesion(request, response);
		}
		else 
		{
		Login user = new Login();
		user.setEmail(request.getParameter("email"));
		user.setPassword(request.getParameter("password"));
		
		String requestDispacherRedirect = "";
		
		Usuario usuario = null;
		
		LoginDAO login = new LoginDAO();
		
		if(login.iniciarSesion(user)) {
			usuario = login.obtenerUsuario(user.getEmail());
			if(usuario != null) {
				if(!usuario.isEsAdministrador()) {
					MedicoDAOImpl mDao = new MedicoDAOImpl();
					Medico medicoUsuario = mDao.obtenerMedico(usuario.getDni());
					
					if(medicoUsuario != null) {
						request.getSession().setAttribute("medicoUsuario", medicoUsuario);
					}	
				}
				request.getSession().setAttribute("usuario", usuario);
				requestDispacherRedirect = "/Inicio.jsp";
			}			
			
		}
		else {
			request.getSession().setAttribute("usuario", usuario);
			requestDispacherRedirect = "/Login.jsp";
		}
		
		RequestDispatcher rd2 = request.getRequestDispatcher(requestDispacherRedirect); 
		rd2.forward(request, response);		
		}
	}
	
	protected void  cerrarSesion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().setAttribute("usuario", null);
		request.getSession().setAttribute("medicoUsuario", null);
		String requestDispacherRedirect = "/Login.jsp";
		RequestDispatcher rd2 = request.getRequestDispatcher(requestDispacherRedirect); 
		rd2.forward(request, response);		
	}

}
