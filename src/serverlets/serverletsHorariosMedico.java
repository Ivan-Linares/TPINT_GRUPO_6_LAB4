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
		
		String estadoNuevoHorario = "";
		if(horarioDao.agregar(nuevoHorario)) {
			setearAtributosIdDniMedico(request);
			estadoNuevoHorario = "Nuevo horario agregado al Médico con éxito!";
			request.setAttribute("estadoNuevoHorario", estadoNuevoHorario);
			RequestDispatcher rd = request.getRequestDispatcher("InsertarHorario.jsp");
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
