package serverlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.EspecialidadesDAOImpl;
import dao.impl.TelefonoDAOImpl;
import dominio.Especialidad;
import dominio.Medico;
import dominio.Telefono;

@WebServlet("/serverletsEspecialidades")
public class serverletsEspecialidades extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		if(request.getParameter("btn-nueva-especialidad") != null) {
			setearAtributosIdDniMedico(request);
			agregarListaEspecialidades(request);
			RequestDispatcher rd = request.getRequestDispatcher("InsertarEspecialidad.jsp");
			rd.forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		if(request.getParameter("btn-agregar-especialidad") != null) {
			int idMedico = 0;
			int idEspecialidad = 0;
			if(request.getParameter("especialidadSelect") != null) idEspecialidad = Integer.parseInt(request.getParameter("especialidadSelect"));
			if(request.getParameter("idMedico") != null) idMedico = Integer.parseInt(request.getParameter("idMedico"));
			
			EspecialidadesDAOImpl eDao = new EspecialidadesDAOImpl();
			String estado = "";
			if(eDao.agregarEspecialidadMedico(idEspecialidad, idMedico)) {
				setearAtributosIdDniMedico(request);
				agregarListaEspecialidades(request);
				estado = "Nueva especialidad asignada al Médico con éxito";
				request.setAttribute("estado", estado);
				RequestDispatcher rd = request.getRequestDispatcher("InsertarEspecialidad.jsp");
				rd.forward(request, response);
			}
		}
		else if(request.getParameter("btn-eliminar-especialidad") != null) {
			System.out.println("entro elimnar");
			String estadoEspecialidad = "";
			if(eliminarEspecialidadMedico(request)) estadoEspecialidad = "Se pudo eliminar la Especialidad correctamente!";
			else estadoEspecialidad ="Ocurrió un error al intentar eliminar la Especialidad!";
			request.setAttribute("estadoEspecialidad", estadoEspecialidad);
			
			String dniMedico = "";
			if(request.getParameter("dniMedico") != null) dniMedico = request.getParameter("dniMedico");
			int idMedico = 0;
			if(request.getParameter("idMedico") != null) idMedico = Integer.parseInt(request.getParameter("idMedico"));
			RequestDispatcher rd = request.getRequestDispatcher("serverletsMedicos?method=post&dniMedico="+ dniMedico+"&idMedico="+idMedico+"&btn-editar-medico=");
			rd.forward(request, response);
		}

		
	}
	
	protected boolean eliminarEspecialidadMedico(HttpServletRequest request) {
		EspecialidadesDAOImpl eDao = new EspecialidadesDAOImpl();
		int idMedico = 0;
		int idEspecialidad = 0;
		if(request.getParameter("idMedico") != null) idMedico = Integer.parseInt(request.getParameter("idMedico"));
		if(request.getParameter("idEspecialidad") != null) idEspecialidad = Integer.parseInt(request.getParameter("idEspecialidad"));
		System.out.println(idMedico + idEspecialidad);
		if(eDao.eliminarEspecialidadMedico(idEspecialidad, idMedico)) return true;
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
	
	protected void agregarListaEspecialidades(HttpServletRequest request) {
		EspecialidadesDAOImpl espDao = new EspecialidadesDAOImpl();
		ArrayList<Especialidad> listaEspecialidades = (ArrayList<Especialidad>) espDao.listarEspecialidades();
		request.setAttribute("listaEspecialidades", listaEspecialidades);
	}
}
