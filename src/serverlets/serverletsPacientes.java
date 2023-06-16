package serverlets;

import java.io.IOException;
import dao.impl.*;
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
		   
		if(var == null){
			RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
			rd.forward(request, response);
		}
		else {
			
			listarPacientes(request);
				RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
				rd.forward(request, response);

				if(request.getParameter("btnBuscarPacientes") != null) {
					//filtrado
			}
		}
		
	}
	
	private void listarPacientes(HttpServletRequest request) {
		PacienteDAOImpl pDao = new PacienteDAOImpl();
		ArrayList<Paciente> listaPacientes  = pDao.listarPacientes();
		
		request.setAttribute("listaPacientes", listaPacientes);
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		PacienteDAOImpl pDao = new PacienteDAOImpl();
		if(request.getParameter("btn-ver-paciente") != null) 
		{
			String dniPaciente = request.getParameter("dniPaciente").toString();
			System.out.println("entrooo ver" + dniPaciente);
		}
		
		else if(request.getParameter("btn-editar-paciente") != null) {
			String dniPaciente =request.getParameter("dniPaciente").toString();
			System.out.println("entrooo editar");
		}
		else if(request.getParameter("btn-eliminar-paciente") != null) {
			System.out.println("entrooo eliminar");
			String dniPaciente = request.getParameter("dniPaciente").toString();
			pDao.eliminar(dniPaciente);
			
		}
		
		listarPacientes(request);
		RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
		rd.forward(request, response);
	}

}
