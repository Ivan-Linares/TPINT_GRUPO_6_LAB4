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
import dao.impl.TurnosDAOImpl;
import dominio.Paciente;
import dominio.Turnos;
import dominio.Usuario;

/**
 * Servlet implementation class servletsTurnos
 */
@WebServlet("/servletsTurnos")
public class servletsTurnos extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public servletsTurnos() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Usuario var = (Usuario) request.getSession().getAttribute("usuario");
		
		if(var == null){
			RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");
			rd.forward(request, response);
		}
		else {
			listarTurnos(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("Pacientes.jsp");
			rd.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void listarTurnos(HttpServletRequest request) {
		TurnosDAOImpl tDao = new TurnosDAOImpl();
		ArrayList<Turnos> listaTurnos  = tDao.listarTurnos();
		
		request.setAttribute("listaTurnos", listaTurnos);
	}
	
}
