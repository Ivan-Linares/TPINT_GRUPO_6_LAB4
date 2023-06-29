package serverlets;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.HorariosTrabajoDAOImpl;
import dominio.HorariosTrabajo;

@WebServlet("/serverletsHorariosMedico")
public class serverletsHorariosMedico extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		if(request.getParameter("btn-nuevo-horario") != null) {
			
			setearAtributosIdDniMedico(request);
			RequestDispatcher rd = request.getRequestDispatcher("InsertarHorario.jsp");
			rd.forward(request, response);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		if(request.getParameter("btn-agregar-horario-trabajo") != null) {
			String estadoNuevoHorario = "";
			if(agregarNuevoHorarioTrabajo(request)) 
				estadoNuevoHorario = "Nuevo horario agregado al Médico con éxito!";
			else
				estadoNuevoHorario = "Ocurrió un error al agregar el Nuevo Horario!";
			setearAtributosIdDniMedico(request);
			request.setAttribute("estadoNuevoHorario", estadoNuevoHorario);
			RequestDispatcher rd = request.getRequestDispatcher("InsertarHorario.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-editar-horario-trabajo") != null) {
			
		}
		else if(request.getParameter("btn-eliminar-horario-trabajo") != null) {
			String estadoHorario = "";
			if(eliminarHorarioTrabajo(request)) estadoHorario = "Se pudo eliminar el Horario correctamente!";
			request.setAttribute("estadoHorario", estadoHorario);
			
			String dniMedico = "";
			if(request.getParameter("dniMedico") != null) dniMedico = request.getParameter("dniMedico");
			RequestDispatcher rd = request.getRequestDispatcher("serverletsMedicos?method=post&dniMedico="+ dniMedico+"&btn-ver-medico=");
			rd.forward(request, response);
		}
		
		
	}
	
	protected boolean eliminarHorarioTrabajo(HttpServletRequest request) {
		HorariosTrabajoDAOImpl horarioDao = new HorariosTrabajoDAOImpl();
		int idMedico = 0;
		String dia = "";
		if(request.getParameter("idMedico") != null) idMedico = Integer.parseInt(request.getParameter("idMedico"));
		if(request.getParameter("diaHorarioMedico") != null) dia = request.getParameter("diaHorarioMedico") ;
		
		if(horarioDao.eliminar(idMedico, dia)) return true;
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
	
	protected boolean agregarNuevoHorarioTrabajo(HttpServletRequest request) {
		HorariosTrabajo nuevoHorario = new HorariosTrabajo();
		if(request.getParameter("idMedico") != null) {
			String idMedico = request.getParameter("idMedico");
			nuevoHorario.setIdMedico(Integer.parseInt(idMedico));
		}		
		
		nuevoHorario.setDia(request.getParameter("dia"));
		
		String horaEntrada = request.getParameter("horaEntrada").toString();
		 String[] horaMinEntrada = horaEntrada.split(":");
		 int horaEntradaInt = Integer.parseInt(horaMinEntrada[0]);
		nuevoHorario.setHoraEntrada(horaEntradaInt);
		
		String horaSalida = request.getParameter("horaSalida").toString();
		 String[] horaMinSalida = horaSalida.split(":");
		 int horaSalidaInt = Integer.parseInt(horaMinSalida[0]);
		nuevoHorario.setHoraSalida(horaSalidaInt);

		HorariosTrabajoDAOImpl horarioDao = new HorariosTrabajoDAOImpl();
		
		if(horarioDao.agregar(nuevoHorario)) {
			
			return true;
		}
		
		return false;
	}

}
