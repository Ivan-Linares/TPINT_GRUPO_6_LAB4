package serverlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.HorariosTrabajoDAOImpl;
import dao.impl.TelefonoDAOImpl;
import dominio.HorariosTrabajo;
import dominio.Telefono;

@WebServlet("/serverletsTelefono")
public class serverletsTelefono extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		if(request.getParameter("btn-nuevo-telefono") != null) {
			
			setearAtributosIdDniMedico(request);
			RequestDispatcher rd = request.getRequestDispatcher("InsertarTelefono.jsp");
			rd.forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		Telefono nuevoTelefono = new Telefono();
		if(request.getParameter("dniMedico") != null) {
			String dniMedico = request.getParameter("dniMedico");
			nuevoTelefono.setDni(dniMedico);
		}		
		
		nuevoTelefono.setTelefono(request.getParameter("telefono"));

		TelefonoDAOImpl tDao = new TelefonoDAOImpl();
		
		String estadoNuevoTelefono = "";
		if(tDao.agregar(nuevoTelefono)) {
			setearAtributosIdDniMedico(request);
			estadoNuevoTelefono = "Nuevo telefono agregado al Médico con éxito!";
			request.setAttribute("estadoNuevoTelefono", estadoNuevoTelefono);
			RequestDispatcher rd = request.getRequestDispatcher("InsertarTelefono.jsp");
			rd.forward(request, response);
		}
		
	}
	
	protected void setearAtributosIdDniMedico(HttpServletRequest request) {
		if(request.getParameter("idMedico") != null) {
			String idMedico = request.getParameter("idMedico");
			request.setAttribute("idMedico", idMedico);
		} 
		
		if(request.getParameter("dniMedico") != null) {
			String dniMedico = request.getParameter("dniMedico");
			request.setAttribute("dniMedico", dniMedico);
		} 
	}

}