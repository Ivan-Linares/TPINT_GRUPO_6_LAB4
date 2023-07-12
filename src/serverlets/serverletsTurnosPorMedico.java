package serverlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Usuario;
import dtos.TurnosPorMedicoDTO;
import util.Reporte;

/**
 * Servlet implementation class serverletsTurnosPorMedico
 */
@WebServlet("/serverletsTurnosPorMedico")
public class serverletsTurnosPorMedico extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public serverletsTurnosPorMedico() {
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
			if(request.getParameter("btn-buscar") != null) {
				listarTurnosPorMedico(request);
				
				RequestDispatcher rd = request.getRequestDispatcher("TurnosPorMedico.jsp");
				rd.forward(request, response);
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void listarTurnosPorMedico(HttpServletRequest request) {
		Reporte reporte = new Reporte();
		ArrayList<TurnosPorMedicoDTO> listado = (ArrayList<TurnosPorMedicoDTO>)reporte.listarCantidadTurnosPorMedico();
		request.setAttribute("listaTurnos", listado);
	}

}
