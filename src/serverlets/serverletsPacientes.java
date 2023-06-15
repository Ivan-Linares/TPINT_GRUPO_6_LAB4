package serverlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.impl.PacienteDAOImpl;
import dominio.Paciente;
import dominio.Usuario;

@WebServlet("/serverletsPacientes")
public class serverletsPacientes extends HttpServlet  {

	private static final long serialVersionUID = 1L;
	
	public serverletsPacientes() {}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario var = (Usuario) request.getSession().getAttribute("usuario");
		   
		if (var == null) {
			RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
			rd.forward(request, response);
		}
		else {
			if(request.getParameter("btnBuscarPacientes") != null) {
				PacienteDAOImpl pDao = new PacienteDAOImpl();
				ArrayList<Paciente> listaPacientes  = pDao.listarPacientes();
				
				request.setAttribute("listaPacientes", listaPacientes);
				
				RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
				rd.forward(request, response);
			}
		}
		
	}

}
