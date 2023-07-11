package serverlets;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.fabric.xmlrpc.base.Array;

import dao.impl.EspecialidadesDAOImpl;
import dao.impl.HorariosTrabajoDAOImpl;
import dao.impl.MedicoDAOImpl;
import dao.impl.PacienteDAOImpl;
import dao.impl.TurnosDAOImpl;
import dominio.Especialidad;
import dominio.HorariosTrabajo;
import dominio.Medico;
import dominio.Paciente;
import dominio.Persona;
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
			System.out.println("entro en usuario");
			rd.forward(request, response);
		}
		else {
			
			if(request.getParameter("btn-nuevo-turno") != null) {
				listarEspecialidades(request);
				RequestDispatcher rd = request.getRequestDispatcher("InsertarTurno.jsp");
				rd.forward(request, response);
			}
			else {
				listarTurnos(request);
				
				RequestDispatcher rd = request.getRequestDispatcher("Turnos.jsp");
				rd.forward(request, response);				
			}
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idEsp = "";
		String descEsp = "";
		TurnosDAOImpl tDao = new TurnosDAOImpl();
		
		int idTurno = 0;
		if(request.getParameter("idTurno") != null) idTurno = Integer.parseInt(request.getParameter("idTurno"));
		
		if(request.getParameter("btn-buscar-medicos") != null) {
			idEsp = request.getParameter("especialidadSelect").toString();

			EspecialidadesDAOImpl eDao = new EspecialidadesDAOImpl();
			ArrayList<Especialidad> listaEsp = (ArrayList<Especialidad>) eDao.listarEspecialidades();
			
			for (Especialidad esp : listaEsp) {
				if(esp.getIdEspecialidad() == Integer.parseInt(idEsp)) {
					descEsp = esp.getDescripcion();
				}
			}
			
			MedicoDAOImpl mDao = new MedicoDAOImpl();
			ArrayList<Medico> listaMedico = mDao.listarMedicosXespecialidad(idEsp);
			
			request.setAttribute("listaMed", listaMedico);
			request.setAttribute("espSelect", idEsp);
			request.setAttribute("descripcionEsp", descEsp);
			
			listarEspecialidades(request);
			
			RequestDispatcher rd = request.getRequestDispatcher("InsertarTurno.jsp");
			rd.forward(request, response);
		}
		if(request.getParameter("btn-agregar-turno") != null) {
			
			try {
				if(agregarTurno(request, response)) {
					request.setAttribute("mensajeExito", "Turno dado de alta con exito ");
					listarTurnos(request);
					
				}
				else {
					request.setAttribute("mensajeError", "No se pudo dar de alta el turno.");
					listarTurnos(request);
					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher("Turnos.jsp");
			rd.forward(request, response);
		}
		if(request.getParameter("btn-editar-turno") != null) {
			agregarDetallesVerTurno(request, idTurno);
			request.setAttribute("editar-turno", true);
			RequestDispatcher rd = request.getRequestDispatcher("EditarTurno.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-ver-turno") != null) {

			agregarDetallesVerTurno(request, idTurno);
			
			RequestDispatcher rd = request.getRequestDispatcher("EditarTurno.jsp");
			rd.forward(request, response);
		}
		else if(request.getParameter("btn-guardar-turno") != null) {
			try {
				if(ModificarTurno(tDao, request))request.setAttribute("mensajeExito", "El turno fue modificado Correctamente!");
				else request.setAttribute("mensajeError", "Ocurrió un error al intentar modificar al turno");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			agregarDetallesVerTurno(request, idTurno);
			request.setAttribute("editar-turno", true);
			listarTurnos(request);
			RequestDispatcher rd = request.getRequestDispatcher("Turnos.jsp");
			rd.forward(request, response);
		}
		if(request.getParameter("btn-eliminar-turno") != null) {
			
			EliminarTurno(tDao, idTurno);
			listarTurnos(request);
			request.setAttribute("mensajeExito", "El Turno fue eliminado correctamente!");
			RequestDispatcher rd = request.getRequestDispatcher("Turnos.jsp");
			rd.forward(request, response);
		}
		
	}

	private void listarTurnos(HttpServletRequest request) {
		TurnosDAOImpl tDao = new TurnosDAOImpl();
		ArrayList<Turnos> listaTurnos  = tDao.listarTurnos();
		
		request.setAttribute("listaTurnos", listaTurnos);
	}
	
	protected void agregarDetallesVerTurno(HttpServletRequest request, int idTurno) {
		
		TurnosDAOImpl tDao = new TurnosDAOImpl();
		Turnos turno = tDao.listarTurnoxId(idTurno);
		request.setAttribute("turno", turno);
		listarEspecialidades(request);
	}
	
	protected void listarEspecialidades(HttpServletRequest request) {
		EspecialidadesDAOImpl espDao = new EspecialidadesDAOImpl();
		ArrayList<Especialidad> listaEspecialidades = (ArrayList<Especialidad>)espDao.listarEspecialidades();
		request.setAttribute("listaEspecialidades", listaEspecialidades);
	}
	
	protected boolean ModificarTurno(TurnosDAOImpl tDao, HttpServletRequest request) throws ParseException {
		Turnos turno = setearDatosTurno(request);
		return tDao.modificarTurno(turno);
	}
	
	protected Turnos setearDatosTurno(HttpServletRequest request) {
		Turnos nuevoTurno = new Turnos();
		
		try {
			
			nuevoTurno.setIdTurno(Integer.parseInt(request.getParameter("idTurno").toString()));
			
			nuevoTurno.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha").toString()));
			nuevoTurno.setHora(Integer.parseInt(request.getParameter("hora")));
			nuevoTurno.setObservacion(request.getParameter("observaciones"));
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return nuevoTurno;
	}
	
	protected boolean agregarTurno(HttpServletRequest request, HttpServletResponse response) {
		
		TurnosDAOImpl tDao = new TurnosDAOImpl();
		HorariosTrabajoDAOImpl hDao = new HorariosTrabajoDAOImpl();
		Turnos nuevoTurno = new Turnos();
		String dni = "";
		boolean agregoTurno = false;
		boolean existePaciente = false;
		boolean fechaRepetidaPaciente = false;
		boolean fechayHoraRepetidaMedico = false;
		boolean coincideFechayHora = false;
		
		try {
			
			Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha").toString());
			
			int nD=-1;
			String dia="";
			Calendar cal = Calendar.getInstance();
			cal.setTime(fecha);
			
			nD=cal.get(Calendar.DAY_OF_WEEK); 
			switch (nD){
	        case 1: dia = "Domingo";
	            break;
	        case 2: dia = "Lunes";
	            break;
	        case 3: dia = "Martes";
	            break;
	        case 4: dia = "Miércoles";
	            break;
	        case 5: dia = "Jueves";
	            break;
	        case 6: dia = "Viernes";
	            break;
	        case 7: dia = "Sábado";
	            break;
			}
			System.out.println(dia);
			
			nuevoTurno.setFecha(fecha);
			Especialidad esp = new Especialidad();
			esp.setIdEspecialidad(Integer.parseInt(request.getParameter("espSelect")));
			esp.setDescripcion(request.getParameter("descripcionEsp"));
			nuevoTurno.setEspecialidad(esp);
			
			int hora = Integer.parseInt(request.getParameter("hora"));
			nuevoTurno.setHora(hora);
			
			Medico med = new Medico();
			int idMedico = Integer.parseInt(request.getParameter("medico"));
			med.setIdMedico(idMedico);
			nuevoTurno.setMedico(med);
			
			ArrayList<Turnos> listaTurnosxMed = tDao.obtenerTurno_Medico(idMedico);
			for (Turnos turnos : listaTurnosxMed) {
				if(turnos.getFecha().equals(fecha) && turnos.getHora() == hora) {
					fechayHoraRepetidaMedico = true;
				}
			}
			
			if(fechayHoraRepetidaMedico) {
				try {
					request.setAttribute("mensajeError", "Esta fecha y hora ya tienen un turno asignado");
					RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
					rd.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			ArrayList<HorariosTrabajo> listaHT = hDao.listarPorMedico(idMedico);
			for (HorariosTrabajo ht : listaHT) {
				int horaEntrada = Integer.parseInt(ht.getHoraEntrada());
				int horaSalida = Integer.parseInt(ht.getHoraSalida());
				
				if(ht.getDia().equals(dia) && (horaEntrada <= hora && horaSalida > hora)) {
					coincideFechayHora = true;
				}
			}
			
			if(coincideFechayHora == false) {
				try {					
					request.setAttribute("mensajeError", "La fecha o la hora del turno no coincide con el horario del medico");
					RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
					rd.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			Paciente pac = new Paciente();
			PacienteDAOImpl pDao = new PacienteDAOImpl();
			ArrayList<Paciente> listaPaciente = pDao.listarPacientes();
			
			dni = request.getParameter("dniPaciente");
			existePaciente = pDao.existe(dni);

			ArrayList<Turnos> listaTurnosxPac = tDao.obtenerTurno_Paciente(dni);
			for (Turnos turnos : listaTurnosxPac) {
				if(turnos.getFecha().equals(fecha)) {
					fechaRepetidaPaciente = true;
				}
			}
			
			if(fechaRepetidaPaciente) {
				try {					
					request.setAttribute("mensajeError", "Este paciente ya tiene turno asignado ese dia");
					RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
					rd.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(existePaciente) {
				for (Paciente paciente : listaPaciente) {
					if(paciente.getDni().equals(dni)) {
						pac.setIdPaciente(paciente.getIdPaciente());
					}
				}
				nuevoTurno.setPaciente(pac);
				nuevoTurno.setObservacion(request.getParameter("observaciones"));
			}else {
				try {
					request.setAttribute("mensajeError", "No existe paciente con ese DNI, dar de alta.");
					request.setAttribute("errorDNI", true);
					RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
					rd.forward(request, response);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(existePaciente == true && fechaRepetidaPaciente == false && fechayHoraRepetidaMedico == false && coincideFechayHora == true) {				
				agregoTurno = tDao.agregarTurno(nuevoTurno);
			}
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return agregoTurno;
	}
	
	protected void EliminarTurno(TurnosDAOImpl tDao, int idTurno) {		
		tDao.eliminarTurno(idTurno);
	}
	
}
