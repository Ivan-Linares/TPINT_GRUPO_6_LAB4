package serverlets;

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.impl.MedicoDAOImpl;
import dominio.Medico;
import dominio.Usuario;

@WebServlet("/serverletsMedicos")
public class serverletsMedicos extends HttpServlet   {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Usuario var = (Usuario) request.getSession().getAttribute("usuario");
		   
		if(var == null){
			RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
			rd.forward(request, response);
		}
		else {
			System.out.println("entro get");
			listarMedicos(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("Medicos.jsp");
			rd.forward(request, response);
		}
	}
	
	protected void listarMedicos(HttpServletRequest request) {
		MedicoDAOImpl medicoDao = new MedicoDAOImpl();
		ArrayList<Medico> listaMedicos  = medicoDao.listarMedicos();
		request.setAttribute("listaMedicos", listaMedicos);
	}
}
