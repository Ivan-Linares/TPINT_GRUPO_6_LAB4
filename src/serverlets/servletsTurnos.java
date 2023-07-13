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

import negocioImpl.*;
import dominio.Especialidad;
import dominio.Estado;
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
		TurnosNegocioImpl tNegocio = new TurnosNegocioImpl();
		
		int idTurno = 0;
		if(request.getParameter("idTurno") != null) idTurno = Integer.parseInt(request.getParameter("idTurno"));
		
		if(request.getParameter("btn-buscar-medicos") != null) {
			idEsp = request.getParameter("especialidadSelect").toString();

			EspecialidadNegocioImpl eNegocio = new EspecialidadNegocioImpl();
			ArrayList<Especialidad> listaEsp = (ArrayList<Especialidad>) eNegocio.listarEspecialidades();
			
			for (Especialidad esp : listaEsp) {
				if(esp.getIdEspecialidad() == Integer.parseInt(idEsp)) {
					descEsp = esp.getDescripcion();
				}
			}
			
			MedicoNegocioImpl mNegocio = new MedicoNegocioImpl();
			ArrayList<Medico> listaMedico = mNegocio.listarMedicosXespecialidad(idEsp);
			
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
				if(ModificarTurno(tNegocio, request))request.setAttribute("mensajeExito", "El turno fue modificado Correctamente!");
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
			
			EliminarTurno(tNegocio, idTurno);
			listarTurnos(request);
			request.setAttribute("mensajeExito", "El Turno fue eliminado correctamente!");
			RequestDispatcher rd = request.getRequestDispatcher("Turnos.jsp");
			rd.forward(request, response);
		}
		
	}

	private void listarTurnos(HttpServletRequest request) {
		TurnosNegocioImpl tNegocio = new TurnosNegocioImpl();
		ArrayList<Turnos> listaTurnos = new ArrayList<Turnos>();
		
		if(request.getSession().getAttribute("medicoUsuario") == null){
			listaTurnos  = (ArrayList<Turnos>) tNegocio.listarTurnos();
		}
		else {
			Medico medicoUsuario = (Medico)request.getSession().getAttribute("medicoUsuario");
			listaTurnos = (ArrayList<Turnos>) tNegocio.obtenerTurno_Medico(medicoUsuario.getIdMedico());
		}

		
		request.setAttribute("listaTurnos", listaTurnos);
	}
	
	protected void agregarDetallesVerTurno(HttpServletRequest request, int idTurno) {
		
		TurnosNegocioImpl tNegocio = new TurnosNegocioImpl();
		Turnos turno = tNegocio.listarTurnoxId(idTurno);
		request.setAttribute("turno", turno);
		listarEspecialidades(request);
	}
	
	protected void listarEspecialidades(HttpServletRequest request) {
		EspecialidadNegocioImpl espNegocio = new EspecialidadNegocioImpl();
		ArrayList<Especialidad> listaEspecialidades = (ArrayList<Especialidad>)espNegocio.listarEspecialidades();
		request.setAttribute("listaEspecialidades", listaEspecialidades);
	}
	
	protected boolean ModificarTurno(TurnosNegocioImpl tNegocio, HttpServletRequest request) throws ParseException {
		Turnos turno = setearDatosTurno(request);
		return tNegocio.modificarTurno(turno);
	}
	
	protected Turnos setearDatosTurno(HttpServletRequest request) {
		Turnos nuevoTurno = new Turnos();
		
		try {
			
			nuevoTurno.setIdTurno(Integer.parseInt(request.getParameter("idTurno").toString()));
			
			nuevoTurno.setFecha(new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha").toString()));
			nuevoTurno.setHora(Integer.parseInt(request.getParameter("hora")));
			Estado estadoTurno = new Estado();
			if(nuevoTurno.getIdTurno() > 0) estadoTurno.setIdEstado(Integer.parseInt(request.getParameter("estadoSelect").toString()));
			else estadoTurno.setIdEstado(1);
			nuevoTurno.setEstado(estadoTurno);
			nuevoTurno.setObservacion(request.getParameter("observaciones"));
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return nuevoTurno;
	}
	
	protected boolean agregarTurno(HttpServletRequest request, HttpServletResponse response) {
		
		TurnosNegocioImpl tNegocio = new TurnosNegocioImpl();
		HorariosTrabajoNegocioImpl hNegocio = new HorariosTrabajoNegocioImpl();
		Turnos nuevoTurno = new Turnos();
		String dni = "";
		boolean agregoTurno = false;
		boolean existePaciente = false;
		boolean fechaRepetidaPaciente = false;
		boolean fechayHoraRepetidaMedico = false;
		boolean coincideFechayHora = false;
		boolean fechaMenorATurno = false;
		
		try {
			
			Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("fecha").toString());

			long miliseconds = System.currentTimeMillis();
		    Date date = new Date(miliseconds);
		    
		    if(fecha.equals(date) || fecha.before(date)){
		        fechaMenorATurno = true;
		    }
		    
		    if(fechaMenorATurno) {
		    	try {
					request.setAttribute("mensajeError", "No se puede turnar con una fecha menor o igual a la actual");
					RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
					rd.forward(request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}
		    }
			
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
	        case 4: dia = "Miercoles";
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
			
			int hora = Integer.parseInt(request.getParameter("hora").toString());
			System.out.println(hora);
			nuevoTurno.setHora(hora);
			
			Medico med = new Medico();
			int idMedico = Integer.parseInt(request.getParameter("medico"));
			med.setIdMedico(idMedico);
			nuevoTurno.setMedico(med);
			
			ArrayList<Turnos> listaTurnosxMed = (ArrayList<Turnos>) tNegocio.obtenerTurno_Medico(idMedico);
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
			
			ArrayList<HorariosTrabajo> listaHT = hNegocio.listarPorMedico(idMedico);
			for (HorariosTrabajo ht : listaHT) {
				int horaEntrada = Integer.parseInt(ht.getHoraEntrada().split(":")[0]);
				int horaSalida = Integer.parseInt(ht.getHoraSalida().split(":")[0]);
				System.out.println(horaEntrada + " - "+ horaSalida + ht.getDia() + " -" + dia);
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
			PacienteNegocioImpl pNegocio = new PacienteNegocioImpl();
			ArrayList<Paciente> listaPaciente = (ArrayList<Paciente>) pNegocio.listarPacientes();
			
			dni = request.getParameter("dniPaciente");
			existePaciente = pNegocio.existe(dni);

			ArrayList<Turnos> listaTurnosxPac = (ArrayList<Turnos>) tNegocio.obtenerTurno_Paciente(dni);
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
				agregoTurno = tNegocio.agregarTurno(nuevoTurno);
			}
			
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		
		return agregoTurno;
	}
	
	protected void EliminarTurno(TurnosNegocioImpl tNegocio, int idTurno) {		
		tNegocio.eliminarTurno(idTurno);
	}
	
}
