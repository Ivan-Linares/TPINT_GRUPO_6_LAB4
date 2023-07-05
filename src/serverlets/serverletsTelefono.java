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
		
		if(request.getParameter("btn-agregar-telefono") != null) {
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
		else if(request.getParameter("btn-eliminar-telefono") != null) {
			String estadoTelefono = "";
			if(eliminarTelefono(request)) estadoTelefono = "Se pudo eliminar el Telefono correctamente!";
			request.setAttribute("estadoTelefono", estadoTelefono);
			
			String dniMedico = "";
			if(request.getParameter("dniMedico") != null) dniMedico = request.getParameter("dniMedico");
			RequestDispatcher rd = request.getRequestDispatcher("serverletsMedicos?method=post&dniMedico="+ dniMedico+"&btn-ver-medico=");
			rd.forward(request, response);
		}
		
	}
	
	protected boolean eliminarTelefono(HttpServletRequest request) {
		TelefonoDAOImpl tDao = new TelefonoDAOImpl();
		String dniMedico = "";
		String telefonoMedico = "";
		if(request.getParameter("dniMedico") != null) dniMedico = request.getParameter("dniMedico");
		if(request.getParameter("telefonoMedico") != null) telefonoMedico = request.getParameter("telefonoMedico");
		
		if(tDao.eliminar(dniMedico, telefonoMedico)) return true;
		return false;
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