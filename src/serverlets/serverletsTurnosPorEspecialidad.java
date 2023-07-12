package serverlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dominio.Usuario;
import dtos.TurnosPorEspecialidadDTO;
import util.Reporte;

/**
 * Servlet implementation class serverletsTurnosPorEspecialidad
 */
@WebServlet("/serverletsTurnosPorEspecialidad")
public class serverletsTurnosPorEspecialidad extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public serverletsTurnosPorEspecialidad() {
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
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	            Date fecha1 = null;
	            Date fecha2 = null;

	            try {
	                fecha1 = dateFormat.parse(request.getParameter("fechaInicio"));
	                fecha2 = dateFormat.parse(request.getParameter("fechaFin"));
	            } catch (Exception ex) {
	                ex.printStackTrace();
	            }
	            
	            listarTurnosPorEspecialidad(request, fecha1, fecha2);
			
				RequestDispatcher rd = request.getRequestDispatcher("TurnosPorEspecialdiad.jsp");
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
	
	private void listarTurnosPorEspecialidad(HttpServletRequest request, Date fechaInicio, Date fechaFin) {
		Reporte reporte = new Reporte();
		ArrayList<TurnosPorEspecialidadDTO> listado = (ArrayList<TurnosPorEspecialidadDTO>)reporte.listarCantidadTurnosPorEspecialdiad(fechaInicio, fechaFin);
		request.setAttribute("listaTurnos", listado);
	}

}












